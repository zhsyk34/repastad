package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.RoleAuthority;

public interface AuthorityDao {

	public void save(Authority authority);

	public void del(int id);

	public void del(int[] ids);

	public void update(Authority authority);

	public Authority find(int id);

	public List<Authority> findList();
	
	public List<RoleAuthority> findRoleAuthorityList(int authorityId);

}
