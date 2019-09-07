package com.hngd.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hngd.common.result.Result;
import com.hngd.common.result.Results;
import com.hngd.model.Role;
import com.hngd.vmns.sample.HnvmnsJavaSampleApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={HnvmnsJavaSampleApplication.class})
public class RoleServiceTest {

	
	@MockBean
	RoleService mockRoleService;
	
	@SpyBean
	RoleService spyRoleService;
	@Test
	public void testAddRole(){
		
		Role role=new Role();
		role.setInbuiltFlag((short)1);
		role.setName("test3");
		role.setPermit((short)1);
		role.setRemark("123");
		Mockito.when(mockRoleService.addRole(role)).thenReturn(Results.DB_ERROR);
		Mockito.when(mockRoleService.addRole(null)).thenReturn(null);
		Result<String> ret=mockRoleService.addRole(role);
		Assert.assertEquals(Results.DB_ERROR, ret);
		ret=mockRoleService.addRole(null);
		Assert.assertEquals(null, ret);
		
	}
	@Test
	public void testGetRole() {
		Mockito.when(spyRoleService.getRoleList()).thenReturn(null);
		List<Role> roles=spyRoleService.getRoleList();
		Assert.assertEquals(null, roles);
	}
}
