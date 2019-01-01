package com.cxdmg.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色
 * @author 60157
 *
 */
@Entity
@Table(name="sys_role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	private String id;

	private String role_desc;

	private String role_name;

	public Role() {
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getRole_desc() {
		return role_desc;
	}


	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}


	public String getRole_name() {
		return role_name;
	}


	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}