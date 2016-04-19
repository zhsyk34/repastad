package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.OrderInfo;
import com.baiyi.order.util.PageBean;

public interface OrderDao {

	List<OrderInfo> findByPage(String shopId, String cookId, String orderId, String begindate, String enddate, PageBean pageBean);

	OrderInfo findById(int id);

	List<OrderInfo> findByCal(String shopId, String cookId, String begindate, String enddate);

	public OrderInfo findByOrderId(String orderId);
}
