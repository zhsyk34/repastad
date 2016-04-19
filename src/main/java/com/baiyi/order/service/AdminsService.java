package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.util.PageBean;

public interface AdminsService {

	public void save(Admins admins, int[] roleIds);

	public void del(int id);

	public void del(int[] ids);

	public void mod(Admins admins, int[] roleIds);

	public void updatePageCount(int adminId, int pageCount);

	public int updatePassword(String username, String oldPassword, String newpassword);

	// search admins
	public Admins findById(int id);

	public Admins findByNamePass(String username, String password);

	public Admins findByName(String username);

	public List<Admins> findByPageBean(PageBean pageBean);

	public List<Admins> findList();

	public List<Admins> findList(String name, int begin, int length);

	public List<Role> findRoleList(int adminId);

	public List<Integer> findAuthorityList(int adminId);

}
