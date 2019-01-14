package com.cxdmg.repository;

import org.springframework.data.repository.CrudRepository;

import com.cxdmg.model.MbUser;
import com.cxdmg.model.UserRole;
/**
 * 用户角色jpa
 * @author 60157
 *
 */
public interface UserRoleRepository extends CrudRepository<UserRole, String>{

	
}
