package com.cxdmg.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * 角色权限
 * @author 60157
 *
 */
@Entity
@Table(name="sys_role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="perm_id")
	private String perm_id;

	public String getPerm_id() {
		return perm_id;
	}

	public void setPerm_id(String perm_id) {
		this.perm_id = perm_id;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	@Column(name="role_id")
	private String role_id;

	public RolePermission() {
	}

	

}