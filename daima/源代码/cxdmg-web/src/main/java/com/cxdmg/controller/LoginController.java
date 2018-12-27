package com.cxdmg.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.alibaba.fastjson.JSONObject;
import com.cxdmg.config.MD5Util;
import com.cxdmg.config.SmsUtils;
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
	/**
	 * 修改新密码
	 * @param pwd
	 * @param phone
	 * @return
	 */
	@RequestMapping("/updateNewPwd")
	@ResponseBody
	public Map<String,Object>updateNewPwd(String pwd,String phone){
		Map<String,Object>map=new HashMap<String,Object>();
		//判断手机号是否存在
		List<Map<String,Object>>phoneList=mbUserService.findByUserPhone(phone);
		if(phoneList.size()==0) {
			map.put("code","-1");
			map.put("msg","手机号不存在");
			return map;
		}
		//根据id修改密码
		String id=phoneList.get(0).get("id").toString();
		try {
			mbUserService.updateNewPwd(id,pwd);
			map.put("code","1");
			map.put("msg","修改成功");
		} catch (Exception e) {
			map.put("code","-1");
			map.put("msg","修改异常");
		}
		return map;
	}
	
	/**
	 * 发送验证码
	 * 短信正文id 255433 找回密码
	 * 短信正文id 247134 注册验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/sendOut")
	@ResponseBody
	public Map<String,Object>sendOut(String phone,String templateId){
		Map<String,Object>map=new HashMap<String,Object>();
		String []phoneNumbers=new String[]{phone};
		String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
   	 	System.out.println("验证码是:"+verifyCode+",手机号是:"+phone);
		String[] params= {verifyCode};
		try {
			String msg=SmsUtils.sendSms(params, phoneNumbers,Integer.parseInt(templateId));
			JSONObject jsonObject=JSONObject.parseObject(msg);
			String result=jsonObject.getString("result");
			 if(result.equals("0")) {
		    	map.put("yzm", verifyCode);
				map.put("code", "1");
		    }else if(result.equals("1021")){
		    	map.put("msg", "请求发起时间不正常，通常是由于您的服务器时间与腾讯云服务器时间差异超过 10 分钟导致的");
				map.put("code", "-1");
		    }else if(result.equals("1022")){
		    	map.put("msg", "业务短信日下发条数超过设定的上限");
				map.put("code", "-1");
		    }else if(result.equals("1023")){
		    	map.put("msg", "单个手机号 30 秒内下发短信条数超过设定的上限");
				map.put("code", "-1");
		    }else if(result.equals("1024")){
				map.put("code", "-1");
				map.put("msg", "单个手机号 1 小时内下发短信条数超过设定的上限");
		    }else if(result.equals("1025")){
		    	map.put("msg", "单个手机号日下发短信条数超过设定的上限");
				map.put("code", "-1");
		    }else if(result.equals("1026")){
		    	map.put("msg", "单个手机号下发相同内容超过设定的上限");
				map.put("code", "-1");
		    }else if(result.equals("1029")){
		    	map.put("msg", "营销短信发送时间限制");
				map.put("code", "-1");
		    }else if(result.equals("1030")){
		    	map.put("msg", "不支持该请求");
				map.put("code", "-1");
		    }else if(result.equals("1031")){
		    	map.put("msg", "套餐包余量不足");
				map.put("code", "-1");
		    }else if(result.equals("1032")){
		    	map.put("msg", "个人用户没有发营销短信的权限");
				map.put("code", "-1");
		    }else if(result.equals("1033")){
		    	map.put("msg", "欠费被停止服务");
				map.put("code", "-1");
		    }else if(result.equals("1034")){
		    	map.put("msg", "群发请求里既有了国内也有国际手机号");
				map.put("code", "-1");
		    }else {
		    	map.put("msg", "获取验证码失败,请换一个手机号试试,code值为:"+result);
				map.put("code", "-1");
		    }
		} catch (Exception e) {
			map.put("msg", "发送失败,请30秒后重新发送");
			map.put("code", "-1");
		}
		return map;
	}
	
	
	
	/**
	 * 获取qq登陆后的回调信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/qqLoginCallback")
	@ResponseBody
	public Map<String,Object> qqLoginCallback(String openId,String name,String figureurl) {
		Map<String,Object>map=new HashMap<String,Object>();
		//判断openId是否存在
		List<Map<String,Object>>openList=mbUserService.findByUserOpenId(openId);
		if(openList.size()==0) {
			//添加一条记录
			try {
				//随机添加一条用户信息
				mbUserService.saveUserOpenId(openId,name);
			} catch (Exception e) {
				map.put("code","-1");
				map.put("msg","添加用户异常");
				return map;
			}
		}
		map.put("code",1);
		return map;
	}
	
	
	/**
	 * 用户注销
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();//获取当前session
		if(session!=null){
			session.invalidate();//关闭session
		}
		return "login1";
	}
}
