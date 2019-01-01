package com.cxdmg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxdmg.dao.RoleDao;
import com.cxdmg.model.Role;
import com.cxdmg.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleRepository roleRepository;
	
	
	/**
	 * 获取所有角色信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getRoleList(){
		return roleDao.getRoleList();
	}
	/**
	 * 查询角色英文是否存在
	 * @param roleName
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>findByRoleName(String roleName){
		return roleDao.findByRoleName(roleName);
	}
	
	/**
	 * 增加角色
	 */
	@Transactional
	public void saveRole(String roleName,String roleDesc) {
		Role role =new Role();
		role.setRole_desc(roleDesc);
		role.setRole_name(roleName);
		roleRepository.save(role);
	}
}
