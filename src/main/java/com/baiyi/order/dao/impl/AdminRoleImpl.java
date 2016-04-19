package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.AdminRoleDao;
import com.baiyi.order.pojo.AdminRole;

@Repository
public class AdminRoleImpl implements AdminRoleDao {

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

	public AdminRole find(int id) {
		return (AdminRole) hibernateTemplate.get(AdminRole.class, id);
	}

	public List<AdminRole> findList() {
		return (List<AdminRole>) hibernateTemplate.find("from AdminRole", null);
	}

	public void mod(AdminRole adminRole) {
		hibernateTemplate.update(adminRole);
	}

	public void save(AdminRole adminRole) {
		hibernateTemplate.save(adminRole);
	}

}
