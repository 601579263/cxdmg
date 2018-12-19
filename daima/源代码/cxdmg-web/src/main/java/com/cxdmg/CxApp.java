package com.cxdmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * 
 * @Date 2018/7/4
 * <p>解决springboot打成war包，部署tomcat后访问404问题</p>
 */
@SpringBootApplication
public class CxApp extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CxApp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CxApp.class, args);
	}
}
