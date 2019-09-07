package com.hngd.vmns.sample;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.health.DiscoveryClientHealthIndicator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hngd.operation.log.record.LogRecordService;

@SpringBootApplication(scanBasePackages={"com.hngd.web","com.hngd.service"})
@MapperScan(basePackages={"com.hngd.dao","com.hngd.common.dao"})
@EnableWebMvc
public class HnvmnsJavaSampleApplication {

	public static void main(String[] args) {
		SpringApplication springApplication=new SpringApplication(HnvmnsJavaSampleApplication.class);
		ConfigurableApplicationContext app=springApplication.run(args);
		LogRecordService r=app.getBean(LogRecordService.class);
		System.out.println(r.getClass());
	}
	
	 
	 
}
