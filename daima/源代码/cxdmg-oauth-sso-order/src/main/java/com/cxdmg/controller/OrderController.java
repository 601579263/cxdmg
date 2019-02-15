package com.cxdmg.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@RequestMapping("/list")
	public String list(OAuth2Authentication auth,Model model) {
		Map map=(Map)auth.getUserAuthentication().getDetails();
		String name=map.get("name").toString();
		model.addAttribute("name", name);
		return "order/list";
	}
	
	@PreAuthorize("hasAuthority('orderDetail')")
	@RequestMapping("/detail")
	@ResponseBody
	public String detail() {
		return "这是订单详情,有角色权限才能访问";
	}
}
