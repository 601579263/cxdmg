package com.cxdmg.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxdmg.config.MD5Util;
import com.cxdmg.dao.MbUserDao;
import com.cxdmg.model.MbUser;
import com.cxdmg.repository.MbUserRepository;
import com.cxdmg.vo.MbUserVo;

@Service
public class MbUserService {

	@Autowired
	private MbUserDao mbUserDao;
	@Autowired
	private MbUserRepository mbUserRepository;
	/**
	 * 获取所有用户信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>getUserList(){
		return mbUserDao.getUserList();
	}
	
	/**
	 * 根据账号密码获取是否存在
	 * @param empId
	 * @param pwd
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>findByUser(String empId,String pwd){
		return mbUserDao.findByUser(empId, pwd);
	}
	
	/**
	 * 获取手机号是否存在
	 * @param phone
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>findByUserPhone(String phone){
		return mbUserDao.findByUserPhone(phone);
	}
	/**
	 * 获取账号是否存在
	 * @param empId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>findByUserEmpId(String empId){
		return mbUserDao.findByUserEmpId(empId);
	}
	
	/**
	 * 注册账号
	 * @param mbUserVo
	 */
	@Transactional
	public void saveUser(MbUserVo mbUserVo) {
		String pwd=mbUserVo.getPassword();
		pwd=MD5Util.MD5(pwd);
		MbUser mb=new MbUser();
		mb.setCreateTime(new Date());
		mb.setEmail(mbUserVo.getEmail());
		mb.setEmpId(mbUserVo.getEmpId());
		mb.setName(mbUserVo.getName());
		mb.setPassword(pwd);
		mb.setPhone(mbUserVo.getPhone());
		mbUserRepository.save(mb);
	}
}
