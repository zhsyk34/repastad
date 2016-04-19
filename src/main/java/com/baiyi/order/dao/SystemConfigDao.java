package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.SystemConfig;

public interface SystemConfigDao {
	public List<SystemConfig> findList();

	// 根據配置名稱查詢配置信息
	public SystemConfig findByName(String name);

	// 修改系統默認配置信息
	public void mod(SystemConfig systemConfig);

	public void save(SystemConfig systemConfig);

	public void merge(SystemConfig systemConfig);
}
