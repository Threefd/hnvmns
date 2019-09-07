package com.hngd.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hngd.common.error.ErrorCode;
import com.hngd.common.result.Result;
import com.hngd.common.web.RestResponses;
import com.hngd.common.web.result.RestResponse;
import com.hngd.dto.RoleForm;
import com.hngd.model.Role;
import com.hngd.operation.log.annotation.LogAnnotation;
import com.hngd.operation.log.handler.internal.DefaultQueryLogHandler;
import com.hngd.service.RoleService;

/**
 * 角色管理
 * 
 * @author tqd
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@InitBinder
	public void onBinderInit(WebDataBinder binder) {
	}

	/**
	 * 添加系统角色
	 * @param role 待添加的角色信息
	 * @return
	 * @author tqd
	 * @since 1.0.0
	 * @time 2018年7月13日 下午3:25:48
	 */
	@PostMapping("/add")
	public RestResponse<String> addRole(@RequestBody RoleForm role) {
		Role r = new Role();
		r.setName(role.getName());
		r.setRemark(role.getRemark());
		Result<String> result = roleService.addRole(r);
		if (result.isSuccess()) {
			return RestResponses.newSuccessResponse("",result.getData());
		} else {
			return RestResponses.newFailResponse(result.getErrorCode(), result.getDescription());
		}
	}

	/**
	 * 删除角色
	 * @param roleId 待删除角色的Id
	 * @return
	 * @author tqd
	 * @since 1.0.0
	 * @time 2018年7月13日 下午3:26:24
	 */
	@DeleteMapping("/delete")
	public RestResponse<Void> deleteRole(@RequestParam("roleId") String roleId) {
		Integer result = roleService.deleteRoleById(roleId);
		if (ErrorCode.NO_ERROR.equals(result)) {
			return RestResponses.newSuccessResponse("");
		} else {
			return RestResponses.newFailResponse(result, "删除角色失败");
		}
	}

	/**
	 * 加载角色列表
	 * @return
	 * @author tqd
	 * @since 1.0.0
	 * @time 2018年7月13日 下午3:26:45
	 */
	@LogAnnotation(logHandler = DefaultQueryLogHandler.class)
	@GetMapping("/list")
	public RestResponse<List<Role>> getRoleList() {
		List<Role> roles = roleService.getRoleList();
		if (roles != null) {
			return RestResponses.newSuccessResponse("",roles);
		} else {
			return RestResponses.newFailResponse(ErrorCode.SERVER_INTERNAL_ERROR, "删除角色失败");
		}
	}
}
