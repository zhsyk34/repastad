package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.baiyi.order.dao.SystemConfigDao;
import com.baiyi.order.pojo.SystemConfig;

@Component("systemConfigDao")
public class SystemConfigDaoImpl implements SystemConfigDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void mod(SystemConfig systemConfig) {
		hibernateTemplate.update(systemConfig);
	}

	@SuppressWarnings("unchecked")
	public SystemConfig findByName(String name) {
		List<SystemConfig> list = (List<SystemConfig>) hibernateTemplate.find("from SystemConfig as s where s.name = ?", name);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void merge(SystemConfig systemConfig) {
		hibernateTemplate.save(systemConfig);
	}

	public void save(SystemConfig systemConfig) {
		hibernateTemplate.saveOrUpdate(systemConfig);
	}

	@SuppressWarnings("unchecked")
	public List<SystemConfig> findList() {
		return (List<SystemConfig>) hibernateTemplate.find("from SystemConfig");
	}
}
