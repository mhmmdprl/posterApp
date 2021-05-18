package com.esandmongodb.posterapp.filter;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.esandmongodb.posterapp.entity.AuthorLog;
import com.esandmongodb.posterapp.entity.Operation;
import com.esandmongodb.posterapp.enums.JwtConfig;
import com.esandmongodb.posterapp.service.AuthorLogService;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.OperationService;

@Component
public class OperationInterceptor implements HandlerInterceptor {
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private AuthorLogService authorLogService;
	@Autowired
	private DbSequenceService dbSequenceService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = StringUtils.removeEndIgnoreCase(
				(String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE), "/");
		String header = request.getHeader(JwtConfig.HEADER_STRING.getValue());

		if (header != null && header.startsWith(JwtConfig.TOKEN_PREFIX.getValue()) && !"/error".equals(url)) {

			String token = header.replace(JwtConfig.TOKEN_PREFIX.getValue() + " ", "");

			List<String> authorOperation = jwtTokenUtil.getAuthoritiesFromToken(token);

			String operationCode = url + '_' + request.getMethod();
			Operation operation = this.operationService.findByCode(operationCode);
			boolean isExists = authorOperation.contains(operationCode);
			AuthorLog authorLog = new AuthorLog();
			if (operation != null) {
				authorLog.setOperationId(operation.getId());
			}
			authorLog.setId(this.dbSequenceService.getSeq(AuthorLog.seqName));
			authorLog.setAuthor(authorService.findById(this.jwtTokenUtil.getUserIdFromRequest(request)));
			authorLog.setRequestPath(url);
			authorLog.setRequestMethod(request.getMethod());
			authorLog.setOperationCode(operationCode);
			authorLog.setOperationControlResult(isExists?'1':'0');
			authorLog.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(request));
			authorLog.setCreatedDate(new Date());
			this.authorLogService.save(authorLog);

		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
