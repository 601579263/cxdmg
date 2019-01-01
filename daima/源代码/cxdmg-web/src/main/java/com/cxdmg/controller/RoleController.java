package com.cxdmg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxdmg.service.MbUserService;
import com.cxdmg.service.RoleService;
/**
 * 角色控制层
 * @author 60157
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	/**
	 * 获取所有角色信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/getRoleList")
	public String getRoleList(Model model) {
		List<Map<String,Object>>list=roleService.getRoleList();
		model.addAttribute("list", list);
		return "role/roleList";
	}
	
	
	/**
	 * 跳到增加角色界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddRole")
	public String toAddRole(Model model) {
		return "role/addRole";
	}
	
	/**
	 * 添加角色
	 * @return
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	public Map<String,Object> addRole(String roleName,String roleDesc) {
		 Map<String,Object> map=new HashMap<String,Object>();
		//查询角色英文是否存在
		List<Map<String,Object>>roleNameList=roleService.findByRoleName(roleName);
		if(roleNameList.size()>0) {
			map.put("code","-1");
			map.put("msg","角色英文已存在");
			return map;
		}
		try {
			roleService.saveRole(roleName,roleDesc);
			map.put("code","1");
		} catch (Exception e) {
			map.put("code","-1");
			map.put("msg","增加角色异常");
			return map;
		}
		return map;
	}
}
