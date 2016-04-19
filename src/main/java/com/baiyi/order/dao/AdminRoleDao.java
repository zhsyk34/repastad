package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.AdminRole;

public interface AdminRoleDao {

	public void save(AdminRole adminRole);

	public void del(int id);

	public void del(int[] ids);

	public void mod(AdminRole adminRole);

	public AdminRole find(int id);

	public List<AdminRole> findList();

}
