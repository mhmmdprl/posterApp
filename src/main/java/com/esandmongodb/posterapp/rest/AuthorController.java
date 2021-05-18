package com.esandmongodb.posterapp.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.AuthorRole;
import com.esandmongodb.posterapp.entity.Role;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.request.ChangePasswordRequest;
import com.esandmongodb.posterapp.model.request.CreateAuthorRequest;
import com.esandmongodb.posterapp.model.request.LoginRequest;
import com.esandmongodb.posterapp.model.request.UpdateAuthorRequest;
import com.esandmongodb.posterapp.model.request.util.ListRequest;
import com.esandmongodb.posterapp.service.AuthorRoleService;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.RoleService;
import com.esandmongodb.posterapp.util.specification.Specification;

@RestController
public class AuthorController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	DbSequenceService dbSequenceService;
	@Autowired
	BCryptPasswordEncoder bcrptPasswordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorRoleService authorRoleService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private AuthorService authorService;

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		Author author = null;
		String token = null;
		try {
			final Authentication authentication = this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())

			);
			author = this.authorService.findByUsername(loginRequest.getUsername());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtTokenUtil.generateToken(authentication, author);
		} catch (Exception e) {
			return operationFail(e, logger, "invalied username or password");
		}

		return operationSuccess(token);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/save")
	public ResponseEntity<?> save(HttpServletRequest httpServletRequest,
			@Valid @RequestBody CreateAuthorRequest authorRequest) {

		if (this.authorService.existsByUsername(authorRequest.getUsername())) {

			return operationFail(null, logger, "Already used this : " + authorRequest.getUsername());
		}
		Author author = new Author();
		try {
			author.setName(authorRequest.getName());
			author.setAboutMe(authorRequest.getAboutMe());
			author.setUsername(authorRequest.getUsername());
			author.setId(this.dbSequenceService.getSeq(Author.seqName));
			author.setPassword(this.bcrptPasswordEncoder.encode(authorRequest.getPassword()));
			author.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			author.setBirtOfDate(authorRequest.getBirtOfDate());
			author.setEmail(authorRequest.getEmail());
			author.setCreatedDate(new Date());
			if (!authorRequest.getRoleIds().isEmpty()) {
				Role role = null;
				for (Long roleId : authorRequest.getRoleIds()) {
					AuthorRole authorRole = new AuthorRole();
					authorRole.setId(this.dbSequenceService.getSeq(AuthorRole.seqName));
					authorRole.setRoleId(roleId);
					authorRole.setAuthorId(author.getId());
					this.authorRoleService.save(authorRole);
					role = roleService.findById(roleId);
					author.getRoles().add(role);
				}

			} else {
				// TODO ilk kayıtta role belirtilmemişse default olarak user role ekelenecek...
			}
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(this.authorService.save(author));

	}
	@PreAuthorize("hasAuthority('/updateAuthor_PUT')")
	@RequestMapping(method = RequestMethod.PUT, path = "/updateAuthor")
	public ResponseEntity<?> updateAuthor(@Valid @RequestBody UpdateAuthorRequest updateAuthor,
			HttpServletRequest httpServletRequest) {
		Author author = this.authorService.findById(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));

		try {
			if (author != null) {

				author.setAboutMe(updateAuthor.getAboutMe());
				author.setBirtOfDate(updateAuthor.getBirtOfDate());
				author.setName(updateAuthor.getName());
				author.setUpdatedDate(new Date());
				author.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
				this.authorService.save(author);

			} else {
				return operationFail(null, logger, "NO FOUND AUTHOR");
			}

		} catch (Exception e) {
			return operationFail(e, logger);
		}
		return operationSuccess(updateAuthor);

	}
	@PreAuthorize("hasAuthority('/changePassword_PUT')")
	@RequestMapping(method = RequestMethod.PUT, path = "/changePassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
			HttpServletRequest httpServletRequest) {
		Author author = this.authorService.findById(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
				
		boolean operationSucces = false;
		String oldPassword = changePasswordRequest.getOldPassword();
		try {
			if (this.bcrptPasswordEncoder.matches(oldPassword, author.getPassword())) {
				operationSucces = true;
				author.setPassword(this.bcrptPasswordEncoder.encode(oldPassword));
				this.authorService.save(author);
			} else {
				operationFail(null, logger, "Invalid oldpasword");
			}

		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(operationSucces);
	}

	@PreAuthorize("hasAuthority('/authors_GET')")
	@RequestMapping(method = RequestMethod.GET, path = "/authors")
	public ResponseEntity<?> getAll() {
		List<Author> authors = new ArrayList<Author>();
		try {
			authors = this.authorService.findAllActive();
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(authors);
	}

	@PreAuthorize("hasAuthority('/author/searcing_GET')")
	@RequestMapping(method = RequestMethod.GET, path = "/authors/searcing")
	public ResponseEntity<?> getSpecificAuthorList(@RequestBody ListRequest list) {
		Specification specification = new Specification();
		List<Author> userList;
		try {
			userList = this.authorService.findSpecific(specification.toQuery(list.getSearch()),
					list.getSortedByCreatedDate());

		} catch (Exception e) {
			return operationFail(e, logger);
		}
		return operationSuccess(userList);
	}

}
