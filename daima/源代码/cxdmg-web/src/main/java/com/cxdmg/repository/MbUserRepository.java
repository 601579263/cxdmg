package com.cxdmg.repository;

import org.springframework.data.repository.CrudRepository;

import com.cxdmg.model.MbUser;
/**
 * 用户jpa
 * @author 60157
 *
 */
public interface MbUserRepository extends CrudRepository<MbUser, String>{

	
}
