package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.AdminRoleDao;
import com.baiyi.order.dao.AdminsDao;
import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.util.PageBean;

@Service("adminsService")
public class AdminsServiceImpl implements AdminsService {
	@Resource
	private AdminsDao adminsDao;
	@Resource
	private AdminRoleDao adminRoleDao;

	public void del(int id) {
		adminsDao.del(id);
		List<AdminRole> adminRoleList = adminsDao.findAdminRoleList(id);
		for (AdminRole adminRole : adminRoleList) {
			adminRoleDao.del(adminRole.getId());
		}
	}

	public void del(int[] ids) {
		for (int id : ids) {
			del(id);
		}
	}

	public List<Integer> findAuthorityList(int adminId) {
		return adminsDao.findAuthorityList(adminId);
	}

	public Admins findById(int id) {
		return adminsDao.findById(id);
	}

	public Admins findByNamePass(String username, String password) {
		return adminsDao.findByNamePass(username, password);
	}

	public List<Admins> findByPageBean(PageBean pageBean) {
		return adminsDao.findByPage(pageBean);
	}

	public Admins findByName(String username) {
		return adminsDao.findByName(username);
	}

	public List<Admins> findList() {
		return adminsDao.findList();
	}

	public List<Role> findRoleList(int adminId) {
		return adminsDao.findRoleList(adminId);
	}

	public void save(Admins admins, int[] roleIds) {
		adminsDao.save(admins);
		if (roleIds != null && roleIds.length > 0) {
			int adminId = admins.getId();
			for (int roleId : roleIds) {
				AdminRole adminRole = new AdminRole();
				adminRole.setAdminId(adminId);
				adminRole.setRoleId(roleId);
				adminRoleDao.save(adminRole);
			}
		}
	}

	public void mod(Admins admins, int[] roleIds) {
		adminsDao.mod(admins);
		int adminId = admins.getId();
		List<AdminRole> adminRoleList = adminsDao.findAdminRoleList(adminId);
		if (adminRoleList != null && adminRoleList.size() > 0) {
			for (AdminRole adminRole : adminRoleList) {
				adminRoleDao.del(adminRole.getId());
			}
		}
		if (roleIds != null && roleIds.length > 0) {
			for (int roleId : roleIds) {
				AdminRole adminRole = new AdminRole();
				adminRole.setAdminId(adminId);
				adminRole.setRoleId(roleId);
				adminRoleDao.save(adminRole);
			}
		}
	}

	public void updatePageCount(int adminId, int pageCount) {
		Admins admin = this.adminsDao.findById(adminId);
		if (admin != null) {
			admin.setPageCount(pageCount);
		}
		adminsDao.mod(admin);
	}

	public int updatePassword(String username, String oldPassword, String newpassword) {
		Admins admins = this.adminsDao.findByNamePass(username, oldPassword);
		if (admins != null) {
			admins.setUsername(username);
			admins.setPassword(newpassword);
			adminsDao.mod(admins);
			return 1;
		} else {
			return -1;
		}
	}

	public List<Admins> findList(String name, int begin, int length) {
		return adminsDao.findList(name, begin, length);
	}
}
