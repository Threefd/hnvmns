package com.hngd.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 角色表单参数
 * @author hnoe-dev-tqd
 *
 */
public @Data class RoleForm {
    /**
     * 角色名称
     */
	@NotNull
	@Size(max=40)
	//@Pattern
	private String name;
	/**
	 * 角色描述
	 */
	@Size(max=200)
	private String remark;
}
