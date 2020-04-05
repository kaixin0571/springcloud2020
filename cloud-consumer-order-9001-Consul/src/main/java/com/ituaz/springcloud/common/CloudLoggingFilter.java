package com.ituaz.springcloud.common;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudLoggingFilter extends AbstractRequestLoggingFilter {
	private static final String TIME_KEY = MethodHandles.lookup().lookupClass().getName();
	private static final String[] noLogRequstSuffixs = { ".css", ".js", ".jpeg", ".jpg", "gif", "png","/health" };

	@Override
	protected boolean shouldLog(HttpServletRequest request) {

		if (!log.isInfoEnabled())
			return false;

		return !StringUtils.endsWithAny(request.getRequestURI(), noLogRequstSuffixs);
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		log.info("|------>> {}", message);
		request.setAttribute(TIME_KEY, System.nanoTime());
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		Object beforeNanoSec = request.getAttribute(TIME_KEY);
		if (beforeNanoSec instanceof Long) {
			long elapsedNanoSec = System.nanoTime() - (long) beforeNanoSec;
			long elapsedMilliSec = TimeUnit.NANOSECONDS.toMillis(elapsedNanoSec);
			log.info("<<------| ({} ms) {}", elapsedMilliSec, message);
		} else {
			log.info(message);
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		boolean isFirstRequest = !isAsyncDispatch(request);
		HttpServletRequest requestToUse = request;

		if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
			requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
		}

		boolean shouldLog = shouldLog(requestToUse);
		if (shouldLog && isFirstRequest) {
			beforeRequest(requestToUse, getBeforeMessage(requestToUse));
		}
		try {
			filterChain.doFilter(requestToUse, response);
		} finally {
			if (shouldLog && !isAsyncStarted(requestToUse)) {
				afterRequest(requestToUse, getAfterMessage(requestToUse, response));
			}
		}
	}

	private String getBeforeMessage(HttpServletRequest request) {
		return createMessage(request, DEFAULT_BEFORE_MESSAGE_PREFIX, DEFAULT_BEFORE_MESSAGE_SUFFIX);
	}

	private String getAfterMessage(HttpServletRequest request, HttpServletResponse response) {
		return "(status " + response.getStatus() + ") "
				+ createMessage(request, DEFAULT_AFTER_MESSAGE_PREFIX, DEFAULT_AFTER_MESSAGE_SUFFIX);
	}
}
