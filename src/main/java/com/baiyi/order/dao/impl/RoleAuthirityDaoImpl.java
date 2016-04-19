package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.RoleAuthorityDao;
import com.baiyi.order.pojo.RoleAuthority;

@Repository
public class RoleAuthirityDaoImpl implements RoleAuthorityDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public void del(int id) {
		hibernateTemplate.delete(find(id));
	}

	public void del(int[] ids) {
		for (int id : ids) {
			del(id);
		}
	}

	public RoleAuthority find(int id) {
		return (RoleAuthority) hibernateTemplate.get(RoleAuthority.class, id);
	}

	public List<RoleAuthority> findList() {
		return (List<RoleAuthority>) hibernateTemplate.find("from RoleAuthority", null);
	}

	public void mod(RoleAuthority roleAuthority) {
		hibernateTemplate.update(roleAuthority);
	}

	public void save(RoleAuthority roleAuthority) {
		hibernateTemplate.save(roleAuthority);
	}

}
