package com.cxdmg.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxdmg.config.MD5Util;
import com.cxdmg.model.MbUser;
import com.cxdmg.service.MbUserService;
import com.cxdmg.vo.MbUserVo;



/**
 * 登陆
 * @author 60157
 *
 */
@Controller
public class LoginController {

	@Autowired
	private MbUserService mbUserService;
	
	/**
	 * 跳到登陆界面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet() {
		return "login1";
	}
	
	/**
	 * 点击登陆按钮
	 * @return
	 */
	@RequestMapping(value="/toLogin")
	@ResponseBody
	public Map<String,Object> loginPost(String empId,String pwd) {
		Map<String,Object>map=new HashMap<String,Object>();
		//先加密在查询
		pwd=MD5Util.MD5(pwd);
		List<Map<String,Object>>list=mbUserService.findByUser(empId,pwd);
		if(list.size()>0) {
			map.put("msg", "验证成功");
			map.put("code", "1");
		}else {
			map.put("msg", "账号或者密码错误");
			map.put("code", "-1");
		}
		return map;
	}
	
	/**
	 * 注册
	 * @param mbUserVo
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Map<String,Object>register(MbUserVo mbUserVo){
		Map<String,Object>map=new HashMap<String,Object>();
		//判断手机号是否存在
		List<Map<String,Object>>phoneList=mbUserService.findByUserPhone(mbUserVo.getPhone());
		if(phoneList.size()>0) {
			map.put("code","-1");
			map.put("msg","手机号已存在");
			return map;
		}
		//判断账号是否存在
		List<Map<String,Object>>userList=mbUserService.findByUserEmpId(mbUserVo.getEmpId());
		if(userList.size()>0) {
			map.put("code","-1");
			map.put("msg","账号号已存在");
			return map;
		}
		try {
			//注册
			mbUserService.saveUser(mbUserVo);
			map.put("code","1");
			map.put("msg","注册成功");
		} catch (Exception e) {
			map.put("code","-1");
			map.put("msg","注册异常");
		}
		return map;
	}

	/**
	 * 跳到忘记密码界面
	 * @return
	 */
	@RequestMapping("/getForgetPwd")
	public String getForgetPwd() {
		return "forgetPwd";
	}
}
