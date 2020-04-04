package com.ituaz.springcloud.common;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudLoggingFilter extends AbstractRequestLoggingFilter {
	private static final String TIME_KEY = MethodHandles.lookup().lookupClass().getName();

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
}
