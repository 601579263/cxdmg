package com.cxdmg.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDao {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 获取所有权限信息
	 * @return
	 */
	public List<Map<String,Object>>getPermissionList(){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT id,perm_name,perm_tag,url from sys_permission ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
}
