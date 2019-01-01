package com.cxdmg.repository;

import org.springframework.data.repository.CrudRepository;

import com.cxdmg.model.Role;
/**
 * 角色jpa
 * @author 60157
 *
 */
public interface RoleRepository extends CrudRepository<Role, String>{

}
