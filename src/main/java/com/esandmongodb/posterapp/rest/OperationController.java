package com.esandmongodb.posterapp.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.esandmongodb.posterapp.entity.Operation;
import com.esandmongodb.posterapp.entity.Role;
import com.esandmongodb.posterapp.entity.RoleOperation;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.request.OperationRequest;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.OperationService;
import com.esandmongodb.posterapp.service.RoleOperationService;
import com.esandmongodb.posterapp.service.RoleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/operations")
public class OperationController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private RoleOperationService roleOperaitonService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private DbSequenceService dbSequenceService;
	@ApiOperation("endpoint listesi")
	@RequestMapping("/create/operation")
	public void showEndpointsAction(HttpServletRequest req) {
         Role role=this.roleService.findById(1L);
		for (RequestMappingInfo a : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
			String path = a.getPatternsCondition().getPatterns().toArray()[0].toString();
			String code = (a.getMethodsCondition().isEmpty() ? "GET"
					: a.getMethodsCondition().getMethods().toArray()[0].toString());
			Operation operation = new Operation();

			operation.setId(this.dbSequenceService.getSeq(Operation.seqName));
			operation.setCode(path + "_" + code);
			operation.setMethod(code);
			operation.setPath(path);
			operation.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(req));
			operation.setDescription(path + "_" + code);
			operation.setCreatedDate(new Date());
			this.operationService.save(operation);
			//**********************
			role.addOperation(operation);
			RoleOperation roleOperation=new RoleOperation();
			roleOperation.setId(this.dbSequenceService.getSeq(RoleOperation.seqName));
			roleOperation.setOperationId(operation.getId());
			roleOperation.setRoleId(role.getId());
			this.roleOperaitonService.save(roleOperation);
		}
		this.roleService.save(role);

	}
	@PostMapping("/save")
	public ResponseEntity<?> addOperation(@RequestBody OperationRequest operationRequest,
			HttpServletRequest httpServletRequest) {

		Operation operation = null;
		try {
			if (!this.operationService.existByCode(operationRequest.getCode())) {
				operation = new Operation();
				operation.setId(this.dbSequenceService.getSeq(Operation.seqName));
				operation.setPath(operationRequest.getPath());
				operation.setCode(operationRequest.getCode());
				operation.setMethod(operationRequest.getMethod());
				operation.setDescription(operationRequest.getDescription());
				this.operationService.save(operation);
			}

		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(operation);
	}
	@DeleteMapping("/delete/{uuid}")
	public ResponseEntity<?> deleteOperation(@PathVariable String uuid, HttpServletRequest httpServletRequest) {

		try {
			this.operationService.delete(uuid, this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(true);
	}
	@PutMapping("/update/{uuid}")
	public ResponseEntity<?> updateOperation(@PathVariable String uuid, @RequestBody OperationRequest operationRequest,
			HttpServletRequest httpServletRequest) {
		Operation operation = this.operationService.findByUuid(uuid);
		if (operation != null) {

			try {
				operation.setMethod(operationRequest.getMethod());
				operation.setPath(operationRequest.getPath());
				operation.setCode(operationRequest.getCode());
				operation.setDescription(operationRequest.getDescription());
				this.operationService.save(operation);
			} catch (Exception e) {
				return operationFail(e, logger);
			}
		}
		return operationSuccess(operation);
	}
	@GetMapping
	public ResponseEntity<?> getAllActiveOperations(
			@RequestParam(value = "nopaging", required = false) String nopaging) {
		Page<Operation> page = null;

		try {
			Pageable pageable = PageRequest.of(0, 3, Sort.by("createdDate"));
			if ("1".equals(nopaging)) {
				pageable = Pageable.unpaged();
			}
			page = this.operationService.findAllActiceOperations(pageable);
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(page);
	}
	@GetMapping("/{uuid}")
	public ResponseEntity<?> getOperation(@PathVariable String uuid) {
		Operation operation = null;
		try {
			if (this.operationService.existsByUuid(uuid)) {

				operation = this.operationService.findByUuid(uuid);
			}
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(operation);
	}
}
