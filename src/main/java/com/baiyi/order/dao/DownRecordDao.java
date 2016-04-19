package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.DownRecord;


public interface DownRecordDao {
	
	DownRecord findById(int id);
	
	DownRecord findByIsDelete(int templateId,String terminalId,Integer isDelete);
	
	List<DownRecord> findBySearch(Integer templateId,String terminalId,Integer isDelete, Integer isDown);

}
