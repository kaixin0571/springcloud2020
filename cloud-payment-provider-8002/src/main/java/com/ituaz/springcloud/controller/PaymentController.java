package com.ituaz.springcloud.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ituaz.springcloud.bean.CommonResult;
import com.ituaz.springcloud.entity.PaymentBean;
import com.ituaz.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Value("${server.port}")
	private String port;

	@GetMapping("/payment/{id}")
	public CommonResult<PaymentBean> search(@PathVariable("id")Long id) {

		PaymentBean data = paymentService.getPaymentByKey(id);
		if (Objects.isNull(data)) {
			return new CommonResult<PaymentBean>(404, "没找到相应数据,id=" + id);
		}
		return new CommonResult<PaymentBean>().success(data, "Success:port="+port);
	}

	@PostMapping(value = "/payment")
	public CommonResult<Integer> create(@RequestBody PaymentBean bean) {
		Integer data = paymentService.create(bean);
		if (data.intValue() == 0) {
			return new CommonResult<Integer>(500, "数据库插入失败!", data);
		}
		return new CommonResult<Integer>().success(data, "Success:port"+port);
	}
	
	@GetMapping(value = "/discoveryclient")
	public Object getDiscoveryClient() {

		List<String> services = discoveryClient.getServices();
		for (String s : services) {
			log.info("service:{}", s);
		}
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-PROVIDER");
		for (ServiceInstance s : instances) {
			log.info("{},{},{},{},{},{}", s.getHost(), s.getPort(), s.getUri(), s.getInstanceId(), s.getServiceId(),
					s.getScheme());
		}
		return discoveryClient;
	}

}
