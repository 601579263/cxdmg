package com.cxdmg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxdmg.service.MbUserService;
/**
 * 用户控制层
 * @author 60157
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MbUserService mbUserService;
	
	/**
	 * 获取所有用户信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUserList")
	public String getUserList(Model model,String name,String figureurl) {
		List<Map<String,Object>>list=mbUserService.getUserList();
		model.addAttribute("list", list);
		model.addAttribute("name", name);
		model.addAttribute("figureurl", figureurl);
		return "user/userList";
	}
}
