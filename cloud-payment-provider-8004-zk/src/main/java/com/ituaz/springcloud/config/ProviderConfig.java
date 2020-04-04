package com.ituaz.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ituaz.springcloud.common.CloudLoggingFilter;

@Configuration
public class ProviderConfig {

	@Bean
	public CloudLoggingFilter requestLoggingFilter() {
		CloudLoggingFilter filter = new CloudLoggingFilter();
		filter.setIncludeClientInfo(true);
		filter.setIncludeQueryString(true);
		filter.setIncludeHeaders(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(1024);
		return filter;
	}
}
