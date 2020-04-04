package com.ituaz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //Eurka,Zookeeper,Consul等注册中心用来发现服务
public class PaymentApplication8004ZooKeeper {
	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication8004ZooKeeper.class, args);
	}
}
