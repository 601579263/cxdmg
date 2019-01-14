package com.cxdmg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cxdmg.dao.MbUserDao;
import com.cxdmg.vo.MbUserVo;
//设置动态用户信息
@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MbUserDao mbUserDao;

	/**
	 * security 自己的方法,匹配数据库的信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据用户名称查询数据库用户信息
		List<Map<String,Object>>list=mbUserDao.findByUserEmpId(username);
		MbUserVo vo=new MbUserVo();
		vo.setPassword(list.get(0).get("password").toString());
		vo.setId(list.get(0).get("id").toString());
		vo.setUsername(list.get(0).get("username").toString());
		vo.setName(list.get(0).get("name").toString());
		String accountNonExpired=list.get(0).get("accountNonExpired").toString();
		if(accountNonExpired.equals("1")) {
			vo.setAccountNonExpired(true);
		}else {
			vo.setAccountNonExpired(false);
		}
	    String accountNonLocked=list.get(0).get("accountNonLocked").toString();
	    if(accountNonLocked.equals("1")) {
	    	vo.setAccountNonLocked(true);
	    }else {
	    	vo.setAccountNonLocked(false);
	    }
		String enabled=list.get(0).get("enabled").toString();	
		if(enabled.equals("1")) {
			vo.setEnabled(true);
		}else {
			vo.setEnabled(false);
		}
		String credentialsNonExpired=list.get(0).get("credentialsNonExpired").toString();
		if(credentialsNonExpired.equals("1")) {
			vo.setCredentialsNonExpired(true);
		}else {
			vo.setCredentialsNonExpired(false);
		}
		//底层会根据数据库查询用户信息,判断密码是否正确
		//给用户设置权限
		List<Map<String,Object>>permissionList=mbUserDao.findPermissionByUsername(username);
		System.out.println("username:"+username+",对应权限:"+permissionList.toString());
		if(permissionList.size()>0) {
			//定义用户权限, 授权当局
			List<GrantedAuthority>authorities=new ArrayList<GrantedAuthority>();
			for (int i = 0; i < permissionList.size(); i++) {
				String perm_tag=permissionList.get(i).get("perm_tag").toString();
				//放入权限类型
				authorities.add(new SimpleGrantedAuthority(perm_tag));
			}
			vo.setAuthorities(authorities);
		}
		return vo;
	}

}
