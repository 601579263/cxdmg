package com.cxdmg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cxdmg.util.MD5Util;
/**
 * 自定义统一密码加密类
 * @author 60157
 *
 */
@Configuration
public class Md5PasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("加密密码");
		return MD5Util.encode((String)rawPassword); 
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		System.out.println("匹配密码");
		return MD5Util.encode((String) rawPassword).equals(encodedPassword);
	}

}
