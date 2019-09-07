package com.hngd.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hngd.common.error.ErrorCode;
import com.hngd.common.exception.DBErrorException;
import com.hngd.common.result.Result;
import com.hngd.common.result.Results;
import com.hngd.common.util.UuidUtils;
import com.hngd.dao.RoleMapper;
import com.hngd.model.Role;
import com.hngd.model.RoleExample;
import com.hngd.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleDao;

	@Transactional
	@Override
	public Result<String> addRole(Role role) {
		String roleId=UuidUtils.uuid();
		role.setId(roleId);
		if(roleDao.insertSelective(role)>0){
			throw new DBErrorException();
			//return Results.newSuccessResult(roleId);
		}else{
		    return Results.newFailResult(ErrorCode.SERVER_INTERNAL_ERROR, "添加角色失败,服务器内部错误");
		}
	}

	@Override
	public Integer deleteRoleById(String roleId) {
		if(StringUtils.isEmpty(roleId)){
			return ErrorCode.INVALID_PARAMETER;
		}
		if(roleDao.deleteByPrimaryKey(roleId)>0){
			return ErrorCode.NO_ERROR;
		}else{
		    return ErrorCode.TARGET_NOT_FOUND;
		}
	}

	@Override
	public List<Role> getRoleList() {
		RoleExample example=new RoleExample();
		return roleDao.selectByExample(example);
	}
 
}
