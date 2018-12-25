package com.cxdmg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxdmg.dao.MbUserDao;

@Service
public class MbUserService {

	@Autowired
	private MbUserDao mbUserDao;
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getUserList(){
		return mbUserDao.getUserList();
	}
	
}
