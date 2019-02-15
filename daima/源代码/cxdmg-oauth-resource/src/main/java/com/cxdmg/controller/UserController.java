package com.cxdmg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/getUserInfo")
	public Map getUserInfo(OAuth2Authentication auth) {
		//获取当前用户信息
		Map user=(Map)auth.getPrincipal();
		Map map=new HashMap<>();
		map.put("name", user.get("name"));
		map.put("username", user.get("username"));
		map.put("authorities", user.get("authorities"));
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
}
