package com.cxdmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
/**
 * 资源服务器
 * @author 60157
 *
 */
@SpringBootApplication
@EnableOAuth2Sso //开启这个注解会帮我们完成跳转到授权服务器
public class AppOauthResouce {

	public static void main(String[] args) {
		SpringApplication.run(AppOauthResouce.class, args);
	}
}
