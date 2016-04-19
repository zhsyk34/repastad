package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.baiyi.order.dao.AuthorityDao;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.RoleAuthority;

@Component("authorityDao")
public class AuthorityDaoImpl implements AuthorityDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void del(int id) {
		hibernateTemplate.delete(find(id));
	}

	public void del(int[] ids) {
		for (int id : ids) {
			del(id);
		}
	}

	public List<Authority> findList() {
		return (List<Authority>) hibernateTemplate.find("from Authority", null);
	}

	public Authority find(int id) {
		return (Authority) hibernateTemplate.get(Authority.class, id);
	}

	public void save(Authority authority) {
		hibernateTemplate.save(authority);
	}

	public void update(Authority authority) {
		hibernateTemplate.update(authority);
	}

	public List<RoleAuthority> findRoleAuthorityList(int authorityId) {
		String hql = "from RoleAuthority ra where ra.authorityId = ?";
		return (List<RoleAuthority>) hibernateTemplate.find(hql, authorityId);
	}
}
