package com.ituaz.springcloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ituaz.springcloud.entity.PaymentBean;

@Mapper
public interface PaymentMapper {
	@Select({
		"select * from payment where id =#{id}"
	})
	public PaymentBean getPaymentById(@Param("id")Long id);
	
	@Insert({
		"INSERT INTO payment(serial) VALUES (#{serial})"
	})
	public Integer create(PaymentBean bean);

}
