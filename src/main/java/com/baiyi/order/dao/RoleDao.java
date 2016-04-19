package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.pojo.RoleAuthority;

public interface RoleDao {

	public void save(Role role);

	public void del(int id);

	public void del(int[] ids);

	public void mod(Role role);

	public Role find(int id);

	public List<Role> findList();

	// 角色查詢:flag--->0爲自定義,1爲内置各板塊CRUD操作權限
	public List<Role> findList(int flag);
	
	public List<Role> findByName(String name);

	public List<Role> findList(String name, int begin, int length);

	public List<Role> findList(int flag, String name, int begin, int length);

	public List<AdminRole> findAdminRoleList(int roleId);

	public List<RoleAuthority> findRoleAuthorityList(int roleId);

	public List<Authority> findAuthorityList(int roleId);

}
