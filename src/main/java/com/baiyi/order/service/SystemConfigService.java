package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.SystemConfig;

public interface SystemConfigService {
	
	public List<SystemConfig> findList();
	
	public SystemConfig findByName(String name);

	public void save(SystemConfig systemConfig);
	
	public void mod(SystemConfig systemConfig);
	
	public void merge(SystemConfig systemConfig);
	
}
