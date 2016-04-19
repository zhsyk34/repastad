package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.util.PageBean;

public interface AdminsDao {

	public Admins findById(int id);

	public Admins findByName(String username);

	public Admins findByNamePass(String username, String password);

	public List<Admins> findByPage(PageBean pageBean);

	public List<Admins> findList();

	public List<Admins> findList(String name, int begin, int length);

	public void save(Admins admins);

	public void del(int id);

	public void del(int[] ids);

	public void mod(Admins admins);

	public List<AdminRole> findAdminRoleList(int adminId);

	public List<Role> findRoleList(int adminId);

	public List<Integer> findAuthorityList(int adminId);

}
