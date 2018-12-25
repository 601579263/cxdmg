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
		sql.append(" SELECT `name`,empId,phone,email from mb_user  ");
		sql.append(" ORDER BY create_time DESC ");
		list=jdbcTemplate.queryForList(sql.toString());
		return list;
	}
}
