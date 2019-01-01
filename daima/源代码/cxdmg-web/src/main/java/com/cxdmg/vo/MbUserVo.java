package com.cxdmg.vo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 用户信息扩展类
 * @author 60157
 *
 */
public class MbUserVo implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//创建日期字符串
	private String createTimeStr;
	//邮箱
	private String email;
	//名称
	private String name;
	//密码
	private String password;
	//手机号
	private String phone;
	//qq登陆关联id
	private String open_id;
	//用户账号
	private String username;
	//该帐号是否启用,false则验证不通过 
	private boolean enabled;
	//如果帐户没有过期设置为true,false则验证不通过 
	private boolean accountNonExpired;
	//如果帐户不锁定设置为true,false则验证不通过 
	private boolean accountNonLocked;
	//如果证书没有过期设置为true,false则验证不通过 
	private boolean credentialsNonExpired;
	

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public MbUserVo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	// 用户所有权限
	List<GrantedAuthority>authorities=new ArrayList<GrantedAuthority>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}