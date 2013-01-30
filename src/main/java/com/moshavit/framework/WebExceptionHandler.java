/*
 User: Ophir
 Date: 22/01/12
 Time: 16:39
 */
package com.moshavit.framework;

import com.moshavit.ApplicationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.resteasy.spi.UnhandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class WebExceptionHandler implements Filter {

	private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

	private static final String CLIENT_ABORT_EXCEPTION = "org.apache.catalina.connector.ClientAbortException";
	private final String errorJsonResponseString = "{\"result\": \"error\"}";
	private final String errorWithMessageJsonResponseString = "{\n\t\"result\": \"error\",\n\t\"message\": \"%s\"\n}";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (ApplicationException e) {
			log.info(String.format("Application message: '%s' for request %s (via %s)", e.getMessage(), prettyPrintRequest((HttpServletRequest) request), e.getClass().getSimpleName()));
			handleResponse((HttpServletResponse) response, String.format(errorWithMessageJsonResponseString, e.getMessage()));
		} catch (UnhandledException e) {
			handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
		} catch (Exception e) {
			handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
		}
	}

	private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof IllegalArgumentException) {
			log.info(String.format("IllegalArgument message: '%s' for request %s (via %s)", rootCause.getMessage(), prettyPrintRequest(request), rootCause.getClass().getSimpleName()));
			handleResponse(response, String.format(errorWithMessageJsonResponseString, rootCause.getMessage()));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else if (ex instanceof org.jboss.resteasy.spi.ApplicationException) {
			log.warn(ex.getMessage(), ex);
			handleResponse(response, errorJsonResponseString);
		} else if (CLIENT_ABORT_EXCEPTION.equals(ex.getClass().getName())) {
			handleResponse(response, errorJsonResponseString);
			// ignore client aborting requests - not interesting
		} else {
			handleResponse(response, errorJsonResponseString);
			log.error("Error processing request: " + prettyPrintRequest(request), ex);
		}
	}

	private void handleResponse(HttpServletResponse response, String responseBody) throws IOException {
		if (!response.isCommitted()) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType(MediaType.APPLICATION_JSON);
			response.getWriter().write(responseBody);
		}
	}

	private String prettyPrintRequest(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURI());
		if (isNotBlank(request.getQueryString())) {
			sb.append('?').append(request.getQueryString());
		}
		return sb.toString();
	}

	@Override public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override public void destroy() {
		log.debug("Destroyed.");
	}
}
