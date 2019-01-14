package com.cxdmg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

import com.cxdmg.dao.MbUserDao;
import com.cxdmg.dao.PermissionDao;
import com.cxdmg.model.MbUser;
import com.cxdmg.model.UserRole;
import com.cxdmg.repository.MbUserRepository;
import com.cxdmg.repository.UserRoleRepository;
import com.cxdmg.util.MD5Util;
import com.cxdmg.util.StringUtil;
import com.cxdmg.vo.MbUserVo;


@Service
public class MbUserService {
	
	@Value("${msg.subject}")
	private String subject;//邮件名称
	@Value("${msg.text}")
	private String text;//内容
	@Value("${spring.mail.username}")
	private String username;//服务器邮箱账号
	
	
	@Autowired
	private MbUserDao mbUserDao;
	@Autowired
	private MbUserRepository mbUserRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private PermissionDao permissionDao; 
	
	   
	
	
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
		pwd=MD5Util.encode(pwd);
		MbUser mb=new MbUser();
		mb.setCreateTime(new Date());
		mb.setEmail(mbUserVo.getEmail());
		mb.setUsername(mbUserVo.getUsername());
		mb.setName(mbUserVo.getName());
		mb.setPassword(pwd);
		mb.setPhone(mbUserVo.getPhone());
		//以下4个都设置为true
		mb.setAccountNonExpired(true);
		mb.setAccountNonLocked(true);
		mb.setEnabled(true);
		mb.setCredentialsNonExpired(true);
		mbUserRepository.save(mb);
		//注册成功后发送邮件
		if(!StringUtils.isEmpty(mb.getId())) {
			sendMsg(mbUserVo.getEmail(),mb.getUsername());
		}
	}
	/**
	 * 修改密码
	 * @param id
	 * @param pwd
	 */
	@Transactional
	public void updateNewPwd(String id,String pwd) {
		pwd=MD5Util.encode(pwd);
		MbUser mb=mbUserRepository.findById(id).get();
		mb.setPassword(pwd);
		mbUserRepository.save(mb);
	}
	
	
	/**
	 * 发送邮件
	 * @param emailId
	 */
	public void sendMsg(String emailId,String empId) {
		//处理发送邮件
		System.out.println("消息服务平台发送邮件{}开始:"+emailId);
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		//发件人
		simpleMailMessage.setFrom(username);
		//接收邮件账号
		simpleMailMessage.setTo(emailId);
		//邮件标题
		simpleMailMessage.setSubject(subject);
		//邮件内容
		simpleMailMessage.setText(text.replace("{}", empId));
		//发送邮件
		javaMailSender.send(simpleMailMessage);
		System.out.println("消息服务平台发送邮件{}完成:"+emailId);
	}
	
	
	/**
	 * 获取openId是否存在
	 * @param empId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>>findByUserOpenId(String openId){
		return mbUserDao.findByUserOpenId(openId);
	}
	
	/**
	 * 随机添加一条用户信息
	 * @param openId
	 * @throws Exception 
	 */
	@Transactional
	public void saveUserOpenId(String openId,String name,String empId) throws Exception {
		//密码默认123456
		String pwd="123456";
		pwd=MD5Util.encode(pwd);
		MbUser mb=new MbUser();
		mb.setCreateTime(new Date());
		mb.setUsername(empId);
		mb.setName(name);
		mb.setPassword(pwd);
		mb.setOpen_id(openId);
		//以下4个都设置为true
		mb.setAccountNonExpired(true);
		mb.setAccountNonLocked(true);
		mb.setEnabled(true);
		mb.setCredentialsNonExpired(true);
		mbUserRepository.save(mb);
		/*//给用户设置普通角色
		UserRole userRole=new UserRole();
		userRole.setRoleId("8a9f01d5683abd2e01683ac1d8c00000");
		userRole.setUserId(mb.getId());
		userRoleRepository.save(userRole);*/
	}

	
	
	
	
}
