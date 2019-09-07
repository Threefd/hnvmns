package com.hngd.service;

import java.util.List;

import com.hngd.common.result.Result;
import com.hngd.model.Role;
/**
 * 角色管理
 * @author tqd
 *
 */
public interface RoleService {
    /**
     * 新增角色
     * @param role 待新增的角色对象
     * @return
     * @author tqd
     */
	Result<String> addRole(Role role);
    /**
     * 删除角色
     * @param roleId 待删除角色的Id
     * @return
     * @author tqd
     */
	Integer deleteRoleById(String roleId);
    /**
     * 查询角色列表
     * @return
     * @author tqd
     */
	List<Role> getRoleList();

}
