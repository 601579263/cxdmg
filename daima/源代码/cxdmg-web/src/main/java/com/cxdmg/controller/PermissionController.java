package com.cxdmg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxdmg.service.PermissionService;
import com.cxdmg.service.RoleService;

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
	@Autowired
	private RoleService roleService;
	
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
	
	/**
	 * 跳到设置权限界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/setUpPermission")
	public String setUpPermission(Model model) {
		List<Map<String,Object>>list=roleService.getRoleList();
		//默认获取第0个权限信息
		List<Map<String,Object>>perList=permissionService.getPermissionListByRoleId(list.get(0).get("id").toString());
		model.addAttribute("list", list);
		model.addAttribute("perList", perList);
		return "permission/setUpPermission";
	}
	
	/**
	 * 根据角色id显示权限信息
	 * @return
	 */
	@RequestMapping("/getPermissionListJson")
	@ResponseBody
	public Map<String,Object>getPermissionListJson(String roleId){
		Map<String,Object>map=new HashMap<String,Object>();
		List<Map<String,Object>>perList=permissionService.getPermissionListByRoleId(roleId);
		map.put("list", perList);
		return map;
	}
	
	/**
	 * 跳到设置角色权限界面
	 */
	@RequestMapping("/toSettingRolePermissions")
	public String toSettingRolePermissions(Model model,String roleId) {
		List<Map<String,Object>>perList=permissionService.getDoYouHavePermission(roleId);
		model.addAttribute("perList", perList);
		model.addAttribute("roleId", roleId);
		return "permission/settingRolePermissions";
	}
	/**
	 * 保存角色权限
	 * @param perId
	 * @return
	 */
	@RequestMapping("/savePerm")
	@ResponseBody
	public Map<String,Object>savePerm(String perId,String roleId){
		Map<String,Object>map=new HashMap<String,Object>();
		try {
			permissionService.savePerm(perId,roleId);
			map.put("code", 1);
		} catch (Exception e) {
			map.put("code", -1);
		}
		return map;
	}
}
