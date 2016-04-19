package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.RefundWrong;

public interface RefundWrongService {

	public void save(RefundWrong refundWrong);

	public void update(RefundWrong refundWrong);

	public void delete(Integer id);

	public void delete(Integer[] ids);

	public RefundWrong find(Integer id);
	
	public RefundWrong find(String sno);

	public List<RefundWrong> findList(String terminalId, String orderId, String sno, String beginTime, String endTime, int isGet, int adminId, int pageNo, int pageSize);
}
