package com.ituaz.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ituaz.springcloud.bean.CommonResult;
import com.ituaz.springcloud.entity.PaymentBean;
import com.ituaz.springcloud.util.CommonConst;

@RestController
public class ConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/consumer/{id}")
	public CommonResult<PaymentBean> getPayment(@PathVariable("id") Long id) {
		String url = CommonConst.PROVIDER_SERVICE_URL + "/payment/" + id;
		@SuppressWarnings("unchecked")
		CommonResult<PaymentBean> res = restTemplate.getForObject(url, CommonResult.class);
		return res;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/consumer")
	public CommonResult createPayment(PaymentBean bean) {
		String url = CommonConst.PROVIDER_SERVICE_URL + "/payment";
		return restTemplate.postForObject(url, bean, CommonResult.class);
	}
}
