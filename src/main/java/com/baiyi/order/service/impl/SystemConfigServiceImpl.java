package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baiyi.order.dao.SystemConfigDao;
import com.baiyi.order.pojo.SystemConfig;
import com.baiyi.order.service.SystemConfigService;

@Component("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService {

	private SystemConfigDao systemConfigDao;

	@Resource
	public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
		this.systemConfigDao = systemConfigDao;
	}

	public void mod(SystemConfig systemConfig) {
		systemConfigDao.mod(systemConfig);
	}

	public SystemConfig findByName(String name) {
		return systemConfigDao.findByName(name);
	}

	public void merge(SystemConfig systemConfig) {
		systemConfigDao.merge(systemConfig);
	}

	public void save(SystemConfig systemConfig) {
		systemConfigDao.save(systemConfig);
	}

	public List<SystemConfig> findList() {
		return systemConfigDao.findList();
	}
}
