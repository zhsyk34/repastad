package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DownRecordDao;
import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.service.DownRecordService;

@Service("downRecordService")
public class DownRecordServiceImpl implements DownRecordService {

	@Resource
	private DownRecordDao downRecordDao;
	@Resource
	private GenericDao genericDao;

	public void saveDownRecord(int id, int templateId, String terminalId) {
		DownRecord d = downRecordDao.findByIsDelete(templateId, terminalId, null);
		if (d == null) {
			d = new DownRecord();
			d.setTerminalId(terminalId);
			d.setTemplateId(templateId);
		}
		d.setAddtime(new Date());
		d.setIsDelete(DownRecord.UNDELETE);
		d.setIsDown(DownRecord.UNDOWN);
		genericDao.saveOrUpdate(d);
	}

	public void updateDownRecord(DownRecord d) {
		genericDao.update(d);
	}

	public DownRecord findById(int id) {
		return this.downRecordDao.findById(id);
	}

	public List<DownRecord> findBySearch(Integer templateId, String terminalId, Integer isDelete, Integer isDown) {
		return this.downRecordDao.findBySearch(templateId, terminalId, isDelete, isDown);
	}

	public void deleteById(int id) {
		this.genericDao.deleteByParam("delete from DownRecord as a where a.id=" + id);
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public DownRecord findByIsDelete(int templateId, String terminalId, Integer isDelete) {
		return this.downRecordDao.findByIsDelete(templateId, terminalId, isDelete);
	}

	public void setDownRecordDao(DownRecordDao downRecordDao) {
		this.downRecordDao = downRecordDao;
	}

}
