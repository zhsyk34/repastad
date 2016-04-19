package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.AuthorityDao;
import com.baiyi.order.dao.RoleAuthorityDao;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.RoleAuthority;
import com.baiyi.order.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	@Resource
	private AuthorityDao authorityDao;
	@Resource
	private RoleAuthorityDao roleAuthorityDao;

	public void del(int id) {
		authorityDao.del(id);
		List<RoleAuthority> roleAuthorityList = authorityDao.findRoleAuthorityList(id);
		for (RoleAuthority roleAuthority : roleAuthorityList) {
			roleAuthorityDao.del(roleAuthority.getId());
		}
	}

	public void del(int[] ids) {
		if (ids != null && ids.length > 0) {
			for (int id : ids) {
				del(id);
			}
		}
	}

	public Authority find(int id) {
		return authorityDao.find(id);
	}

	public List<Authority> findList() {
		return authorityDao.findList();
	}

	public void save(Authority authority) {
		authorityDao.save(authority);
	}

	public void mod(Authority authority) {
		authorityDao.update(authority);
	}
}
