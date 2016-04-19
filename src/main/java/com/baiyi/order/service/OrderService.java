package com.baiyi.order.service;

import java.util.Date;
import java.util.List;

import com.baiyi.order.pojo.OrderInfo;
import com.baiyi.order.util.PageBean;

public interface OrderService {

	List<OrderInfo> findByPageBean(String shopId, String cookId, String orderId,String begindate, String enddate, PageBean pageBean);

	OrderInfo findById(int id);

	void saveOrder(int id, String orderId, String shopId, String cookId, String content, float amount, float income, float pay, Date d);

	void deleteById(int id);

	public OrderInfo findByOrderId(String orderId);

	List<OrderInfo> findByCal(String shopId, String cookId, String begindate, String enddate);
}
