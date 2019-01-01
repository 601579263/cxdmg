package com.cxdmg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxdmg.dao.PermissionDao;

@Service
public class PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	

	/**
	 * 获取所有权限信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getPermissionList(){
		return permissionDao.getPermissionList();
	}
}
