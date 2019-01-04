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
	
	/**
	 * 根据角色Id获取权限信息
	 * @param roleId
	 * @return
	 */
	public List<Map<String,Object>>getPermissionListByRoleId(String roleId){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT sp.perm_name,sp.perm_tag ");
		sql.append(" from  sys_role as sr ");
		sql.append(" LEFT JOIN sys_role_permission as srp on sr.id=srp.role_id ");
		sql.append(" LEFT JOIN sys_permission as sp on sp.id=srp.perm_id ");
		sql.append(" WHERE sr.id='").append(roleId).append("'");
		sql.append(" and sp.perm_name !='' ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	 
	/**
	 * 根据角色id获取所有权限对应信息
	 * @param roleId
	 * @return
	 */
	public List<Map<String,Object>>getDoYouHavePermission(String roleId){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT p.id,p.perm_name,p.perm_tag,p.url, ");
		sql.append(" (SELECT count(sp.id) ");
		sql.append("  from  sys_role as sr ");
		sql.append("  LEFT JOIN sys_role_permission as srp on sr.id=srp.role_id ");
		sql.append("  LEFT JOIN sys_permission as sp on sp.id=srp.perm_id ");
		sql.append(" WHERE sr.id='").append(roleId).append("'");
		sql.append("  and sp.id=p.id )as num ");
		sql.append("  from sys_permission as p ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 根据roleId删除角色权限关联信息
	 * @param roleId
	 */
	public void deletePermByRoleId(String roleId) {
		StringBuilder sql=new StringBuilder();
		sql.append(" DELETE from sys_role_permission WHERE role_id='").append(roleId).append("'");
		jdbcTemplate.execute(sql.toString());
	}
	/**
	 * 增加角色权限
	 * @param perId
	 * @param roleId
	 */
	public void saveRolePermission(String perId,String roleId) {
		StringBuilder sql=new StringBuilder();
		sql.append(" insert into sys_role_permission (role_id, perm_id) values ('").append(roleId).append("',").append(perId).append(")");
		jdbcTemplate.execute(sql.toString());
	}
	
}
