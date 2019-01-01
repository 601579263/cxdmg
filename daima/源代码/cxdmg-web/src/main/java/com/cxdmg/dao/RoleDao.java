package com.cxdmg.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 获取角色所有信息
	 * @return
	 */
	public List<Map<String,Object>>getRoleList(){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT	role_desc,role_name,id FROM sys_role  ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 *  查询角色英文是否存在
	 * @param roleName
	 * @return
	 */
	public List<Map<String,Object>>findByRoleName(String roleName){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT	role_desc,role_name,id FROM sys_role  ");
		sql.append(" where role_name='").append(roleName).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
}
