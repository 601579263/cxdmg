package com.cxdmg.repository;

import org.springframework.data.repository.CrudRepository;

import com.cxdmg.model.Role;
import com.cxdmg.model.RolePermission;
/**
 * 角色权限jpa
 * @author 60157
 *
 */
public interface RolePermissionRepository extends CrudRepository<RolePermission, String>{

}
