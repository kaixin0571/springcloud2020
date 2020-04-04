package com.ituaz.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ituaz.springcloud.entity.PaymentBean;
import com.ituaz.springcloud.mapper.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper mapper;
	
	public PaymentBean getPaymentByKey(Long id) {
		return mapper.getPaymentById(id);
	}
	
	public Integer create(PaymentBean bean) {
		return mapper.create(bean);
	}
}
