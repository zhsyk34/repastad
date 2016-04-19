package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.pojo.RoleAuthority;

public interface RoleService {

	public void save(Role role, int[] authorityIds);

	public void del(int id);

	public void del(int[] ids);

	public void mod(Role role, int[] authorityIds);

	public Role find(int id);

	public List<Role> findList();

	public List<Role> findList(int flag);
	
	public List<Role> findByName(String name);

	public List<Role> findList(String name, int begin, int length);

	public List<Role> findList(int flag, String name, int begin, int length);

	public List<Authority> findAuthorityList(int roleId);

	public void initRole();

}
