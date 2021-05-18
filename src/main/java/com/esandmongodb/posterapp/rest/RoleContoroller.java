package com.esandmongodb.posterapp.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Operation;
import com.esandmongodb.posterapp.entity.Role;
import com.esandmongodb.posterapp.entity.RoleOperation;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.request.AssingOperationRequest;
import com.esandmongodb.posterapp.model.request.RoleRequest;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.OperationService;
import com.esandmongodb.posterapp.service.RoleOperationService;
import com.esandmongodb.posterapp.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleContoroller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleContoroller.class);
	@Autowired
	private DbSequenceService dbSequenceService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private RoleOperationService roleOperationService;
	@PreAuthorize("hasAuthority('/roles/save_POST')")
	@PostMapping("/save")
	public ResponseEntity<?> addRole(@RequestBody RoleRequest roleRequest, HttpServletRequest httpServletRequest) {
		Role role = null;
		try {
			role = new Role();
			role.setId(this.dbSequenceService.getSeq(Role.seqName));
			role.setName(roleRequest.getName());
			role.setCode(roleRequest.getCode());
			role.setDescription(roleRequest.getDescription());
			role.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			role.setCreatedDate(new Date());
			this.roleService.save(role);
			if (roleRequest.getOperationIds() != null && roleRequest.getOperationIds().size() > 0) {

				List<Long> operationIds = roleRequest.getOperationIds();

				for (Long operationId : operationIds) {
					RoleOperation roleOperation = new RoleOperation();
					roleOperation.setId(this.dbSequenceService.getSeq(RoleOperation.seqName));
					roleOperation.setOperationId(operationId);
					roleOperation.setRoleId(role.getId());
					roleOperation.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
					roleOperation.setCreatedDate(new Date());

					this.roleOperationService.save(roleOperation);
				}

			}

		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(role);
	}
	@PreAuthorize("hasAuthority('/roles/delete/{uuid}_DELETE')")
	@DeleteMapping("/delete/{uuid}")
	public ResponseEntity<?> deleteRole(@PathVariable String uuid, HttpServletRequest httpServletRequest) {

		try {
			if (this.roleService.existsByUuid(uuid)) {

				this.roleService.delete(uuid, this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			} else
				return operationFail(null, logger);
		} catch (Exception e) {

			return operationFail(e, logger);
		}

		return operationSuccess(true);
	}
	@PreAuthorize("hasAuthority('/roles/update/{uuid}_PUT')")
	@PutMapping("/update/{uuid}")
	public ResponseEntity<?> updateRole(@RequestBody RoleRequest roleRequest, @PathVariable String uuid,
			HttpServletRequest httpServletRequest) {
		Role role = null;
		try {
			role = new Role();
			role.setName(roleRequest.getName());
			role.setDescription(roleRequest.getDescription());
			role.setCode(roleRequest.getCode());
			role.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			role.setUpdatedDate(new Date());
			this.roleService.save(role);
		} catch (Exception e) {
			return operationFail(e, logger);
		}
		return operationSuccess(role);

	}
	@PreAuthorize("hasAuthority('/roles/update/{uuid}_PUT')")
	@PostMapping("/privilege/{uuid}")
	public ResponseEntity<?> assinngPrivilege(@RequestBody AssingOperationRequest operationRequest,
			@PathVariable String uuid, HttpServletRequest httpServletRequest) {

		Role role = null;
		try {
			if (operationRequest.getPrivilegeId() != null && operationRequest.getPrivilegeId().size() > 0) {
				role = this.roleService.findByUuid(uuid);
				Operation operation = null;
				if (role != null) {

					for (Long operationId : operationRequest.getPrivilegeId()) {
						operation = this.operationService.findById(operationId);

						if (operation != null && this.roleOperationService.existsByRoleIdAndOperationId(role.getId(),
								operation.getId())) {
							RoleOperation roleOperation = new RoleOperation();
							roleOperation.setRoleId(role.getId());
							roleOperation.setOperationId(operationId);
							roleOperation.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
							roleOperation.setCreatedDate(new Date());
							roleOperation.setId(this.dbSequenceService.getSeq(RoleOperation.seqName));
							role.addOperation(operation);

							this.roleOperationService.save(roleOperation);
						}
					}
					role.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
					role.setUpdatedDate(new Date());
					this.roleService.save(role);
				}

			}
		} catch (Exception e) {
			return operationFail(e, logger);

		}

		return operationSuccess(role);
	}
}
