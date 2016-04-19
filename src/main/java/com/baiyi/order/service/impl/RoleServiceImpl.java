package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baiyi.order.dao.AdminRoleDao;
import com.baiyi.order.dao.RoleAuthorityDao;
import com.baiyi.order.dao.RoleDao;
import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.pojo.RoleAuthority;
import com.baiyi.order.service.AuthorityService;
import com.baiyi.order.service.RoleService;

@Component("roleService")
public class RoleServiceImpl implements RoleService {

	private AdminRoleDao AdminRoleDao;

	private RoleDao roleDao;

	private RoleAuthorityDao roleAuthorityDao;

	private AuthorityService authorityService;

	@Resource
	public void setAdminRoleDao(AdminRoleDao adminRoleDao) {
		AdminRoleDao = adminRoleDao;
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setRoleAuthorityDao(RoleAuthorityDao roleAuthorityDao) {
		this.roleAuthorityDao = roleAuthorityDao;
	}

	@Resource
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void del(int id) {
		if (id > 0) {
			return;
		}
		roleDao.del(id);
		List<AdminRole> adminRoleList = roleDao.findAdminRoleList(id);
		if (adminRoleList != null && adminRoleList.size() > 0) {
			for (AdminRole rdminRole : adminRoleList) {
				roleAuthorityDao.del(rdminRole.getId());
			}
		}

		List<RoleAuthority> roleAuthorityList = roleDao.findRoleAuthorityList(id);
		if (roleAuthorityList != null && roleAuthorityList.size() > 0) {
			for (RoleAuthority roleAuthority : roleAuthorityList) {
				roleAuthorityDao.del(roleAuthority.getId());
			}
		}
	}

	public void del(int[] ids) {
		if (ids != null && ids.length > 0) {
			for (int id : ids) {
				del(id);
			}
		}
	}

	public Role find(int id) {
		return roleDao.find(id);
	}

	public List<Authority> findAuthorityList(int roleId) {
		return roleDao.findAuthorityList(roleId);
	}

	public List<Role> findList() {
		return roleDao.findList();
	}

	public List<Role> findByName(String name) {
		return roleDao.findByName(name);
	}

	public void mod(Role role, int[] authorityIds) {
		roleDao.mod(role);
		int roleId = role.getId();
		List<RoleAuthority> roleAuthorityList = roleDao.findRoleAuthorityList(roleId);
		if (roleAuthorityList != null && roleAuthorityList.size() > 0) {
			for (RoleAuthority roleAuthority : roleAuthorityList) {
				roleAuthorityDao.del(roleAuthority.getId());
			}
		}
		if (authorityIds != null && authorityIds.length > 0) {
			for (int authorityId : authorityIds) {
				RoleAuthority roleAuthority = new RoleAuthority();
				roleAuthority.setRoleId(roleId);
				roleAuthority.setAuthorityId(authorityId);
				roleAuthorityDao.save(roleAuthority);
			}
		}
	}

	public void save(Role role, int[] authorityIds) {
		roleDao.save(role);
		int roleId = role.getId();
		if (authorityIds != null && authorityIds.length > 0) {
			for (int authorityId : authorityIds) {
				RoleAuthority roleAuthority = new RoleAuthority();
				roleAuthority.setRoleId(roleId);
				roleAuthority.setAuthorityId(authorityId);
				roleAuthorityDao.save(roleAuthority);
			}
		}
	}

	public List<Role> findList(int flag) {
		return roleDao.findList(flag);
	}

	public void initRole() {
		List<Role> roleList = roleDao.findList();
		List<Authority> authorityList = authorityService.findList();
		if (roleList != null && roleList.size() > 0) {
			for (Role role : roleList) {
				del(role.getId());
			}
		}
		if (authorityList != null && authorityList.size() > 0) {
			for (Authority authority : authorityList) {
				authorityService.del(authority.getId());
			}
		}
	}

	public List<Role> findList(String name, int begin, int length) {
		return roleDao.findList(name, begin, length);
	}

	public List<Role> findList(int flag, String name, int begin, int length) {
		return roleDao.findList(flag, name, begin, length);
	}
}
