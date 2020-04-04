package com.ituaz.springcloud.mapper;



import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ituaz.springcloud.entity.PaymentBean;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentMapperTest {
	
	@Autowired
	private PaymentMapper mapper;

	@Test
	public void test() throws SQLException {
		
		
		PaymentBean paymentById = mapper.getPaymentById(1L);
		System.out.println(paymentById);
		paymentById.setSerial("889");
		Integer create = mapper.create(paymentById);
		System.out.println(create);
	 
	}

}
