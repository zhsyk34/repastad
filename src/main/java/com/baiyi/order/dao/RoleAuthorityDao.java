package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.RoleAuthority;

public interface RoleAuthorityDao {

	public void save(RoleAuthority roleAuthority);

	public void del(int id);

	public void del(int[] ids);

	public void mod(RoleAuthority roleAuthority);

	public RoleAuthority find(int id);

	public List<RoleAuthority> findList();

}
