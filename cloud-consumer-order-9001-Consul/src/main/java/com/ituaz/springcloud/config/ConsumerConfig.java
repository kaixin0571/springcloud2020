package com.ituaz.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ituaz.springcloud.common.CloudLoggingFilter;

@Configuration
public class ConsumerConfig {
	
	@Autowired
	private RestTemplateBuilder builder;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return builder.build();
	}
	
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
