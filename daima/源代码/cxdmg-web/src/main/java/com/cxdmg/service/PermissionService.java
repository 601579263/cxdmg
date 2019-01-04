package com.cxdmg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxdmg.dao.PermissionDao;
import com.cxdmg.model.RolePermission;
import com.cxdmg.repository.RolePermissionRepository;

@Service
public class PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	

	/**
	 * 获取所有权限信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getPermissionList(){
		return permissionDao.getPermissionList();
	}
	
	/**
	 * 根据角色Id获取权限信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getPermissionListByRoleId(String roleId){
		return permissionDao.getPermissionListByRoleId(roleId);
	}
	
	/**
	 *  根据角色id获取所有权限对应信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getDoYouHavePermission(String roleId){
		return permissionDao.getDoYouHavePermission(roleId);
	}
	/**
	 * 保存角色权限
	 * @param perId
	 */
	@Transactional
	public void savePerm(String perId,String roleId) {
		String []id=perId.split(",");
		//删除角色权限信息
		permissionDao.deletePermByRoleId(roleId);
		for (int i = 0; i < id.length; i++) {
			//增加角色权限
			permissionDao.saveRolePermission(id[i], roleId);
		}
	}
}
