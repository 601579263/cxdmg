package com.cxdmg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MbUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取用户所有信息
	 * @return
	 */
	public List<Map<String,Object>>getUserList(){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT `name`,username,phone,email from mb_user  ");
		sql.append(" ORDER BY create_time DESC ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	/**
	 * 根据账号密码获取是否存在
	 * @param empId
	 * @param pwd
	 * @return
	 */
	public List<Map<String,Object>>findByUser(String username,String pwd){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT * from mb_user  ");
		sql.append(" WHERE username='").append(username).append("'");
		sql.append(" and `password`='").append(pwd).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 查询手机号是否存在
	 * @param phone
	 * @return
	 */
	public List<Map<String,Object>>findByUserPhone(String phone){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT * from mb_user  ");
		sql.append(" WHERE phone='").append(phone).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 查询账号是否存在
	 * @param empId
	 * @return
	 */
	public List<Map<String,Object>>findByUserEmpId(String username){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT * from mb_user  ");
		sql.append(" WHERE username='").append(username).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 查询openId是否存在
	 * @param phone
	 * @return
	 */
	public List<Map<String,Object>>findByUserOpenId(String openId){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT * from mb_user  ");
		sql.append(" WHERE open_id='").append(openId).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	
	/**
	 * 获取用户所有权限
	 * @param empId
	 * @return
	 */
	public List<Map<String,Object>>findPermissionByUsername(String username){
		StringBuilder sql=new StringBuilder();
		List<Map<String,Object>> list=null;
		sql.append(" SELECT permission.id,permission.perm_name,permission.perm_tag,permission.url ");
		sql.append(" FROM mb_user USER ");
		sql.append(" INNER JOIN sys_user_role user_role ON USER .id = user_role.user_id ");
		sql.append(" INNER JOIN sys_role_permission role_permission ON user_role.role_id = role_permission.role_id ");
		sql.append(" INNER JOIN sys_permission permission ON role_permission.perm_id = permission.id ");
		sql.append(" WHERE USER .username = '").append(username).append("'");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
}
