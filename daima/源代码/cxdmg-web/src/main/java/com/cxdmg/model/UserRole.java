package com.cxdmg.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * 用户角色
 * @author 60157
 *
 */
@Entity
@Table(name="sys_user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private String roleId;

	@Column(name="user_id")
	private String userId;

	public UserRole() {
	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}