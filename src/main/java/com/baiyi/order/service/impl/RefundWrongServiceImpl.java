package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baiyi.order.dao.RefundWrongDao;
import com.baiyi.order.pojo.RefundWrong;
import com.baiyi.order.service.RefundWrongService;

@Component("refundWrongService")
public class RefundWrongServiceImpl implements RefundWrongService {

	private RefundWrongDao refundWrongDao;

	@Resource
	public void setRefundWrongDao(RefundWrongDao refundWrongDao) {
		this.refundWrongDao = refundWrongDao;
	}

	public void delete(Integer id) {
		refundWrongDao.delete(id);
	}

	public void delete(Integer[] ids) {
		refundWrongDao.delete(ids);
	}

	public RefundWrong find(Integer id) {
		return refundWrongDao.find(id);
	}

	public List<RefundWrong> findList(String terminalId, String orderId, String sno, String beginTime, String endTime, int isGet, int adminId, int pageNo, int pageSize) {
		return refundWrongDao.findList(terminalId, orderId, sno, beginTime, endTime, isGet, adminId, pageNo, pageSize);
	}

	public void save(RefundWrong refundWrong) {
		refundWrongDao.save(refundWrong);
	}

	public void update(RefundWrong refundWrong) {
		refundWrongDao.update(refundWrong);
	}

	public RefundWrong find(String sno) {
		return refundWrongDao.find(sno);
	}

}
