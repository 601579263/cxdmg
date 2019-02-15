package com.cxdmg.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxdmg.vo.MbUserVo;
/**
 * 认证成功 监听
 * @author 60157
 *
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		System.out.println("用户认证成功");
		//该过程中首先需要将原来的客户请求缓存下来，然后登录成功后将缓存的请求从缓存中提取出来。
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(req, res);
        String targetUrl ="";
        if(savedRequest!=null){
          	 targetUrl = savedRequest.getRedirectUrl();
        }
        System.out.println("登陆进来的路径:"+targetUrl);
        res.sendRedirect(targetUrl);
	}

}
