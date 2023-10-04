package com.capstone.fidelite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
//tell Spring Boot where to scan for annotated components
@ComponentScan(basePackages={"com.capstone.fidelite.integration", "com.capstone.fidelite.restcontroller", "com.capstone.fidelite.services"})
//tell MyBatis where to scan for mapping interface files
@MapperScan(basePackages="com.capstone.fidelite.integration.mapper")  
public class FideliteServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FideliteServiceApplication.class, args);
	}
	
	@Bean
	@Scope("prototype")
	Logger createLogger(InjectionPoint ip) {
	    Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
	    return LoggerFactory.getLogger(classThatWantsALogger);
	}
}

