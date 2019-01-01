package com.cxdmg.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * 权限
 * @author 60157
 *
 */
@Entity
@Table(name="sys_permission")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
	private String id;

	private String perm_name;

	private String perm_tag;

	private String url;

	public Permission() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPerm_name() {
		return perm_name;
	}

	public void setPerm_name(String perm_name) {
		this.perm_name = perm_name;
	}

	public String getPerm_tag() {
		return perm_tag;
	}

	public void setPerm_tag(String perm_tag) {
		this.perm_tag = perm_tag;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}