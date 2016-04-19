package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.DownRecord;


public interface DownRecordService {
	
	DownRecord findById(int id);
	
	void saveDownRecord(int id,int templateId,String terminalId);
	
	DownRecord findByIsDelete(int templateId,String terminalId,Integer isDelete);
	
	void updateDownRecord(DownRecord d);
	
	void deleteById(int id);
	
	List<DownRecord> findBySearch(Integer templateId,String terminalId,Integer isDelete, Integer isDown);
}
