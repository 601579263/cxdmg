package com.cxdmg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxdmg.service.PermissionService;

/**
 * 权限控制层
 * @author 60157
 *
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 获取所有权限信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPermissionList")
	public String getPermissionList(Model model) {
		List<Map<String,Object>>list=permissionService.getPermissionList();
		model.addAttribute("list", list);
		return "permission/permissionList";
	}

}
