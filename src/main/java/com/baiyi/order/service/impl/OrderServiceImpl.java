package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.OrderDao;
import com.baiyi.order.pojo.OrderInfo;
import com.baiyi.order.service.OrderService;
import com.baiyi.order.util.PageBean;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderDao orderDao;
	@Resource
	private GenericDao genericDao;

	public List<OrderInfo> findByPageBean(String shopId, String cookId, String orderId, String begindate, String enddate, PageBean pageBean) {
		return this.orderDao.findByPage(shopId, cookId, orderId, begindate, enddate, pageBean);
	}

	public List<OrderInfo> findByCal(String shopId, String cookId, String begindate, String enddate) {
		return this.orderDao.findByCal(shopId, cookId, begindate, enddate);
	}

	public OrderInfo findById(int id) {
		return this.orderDao.findById(id);
	}

	public void saveOrder(int id, String orderId, String shopId, String cookId, String content, float amount, float income, float pay, Date d) {
		OrderInfo o = new OrderInfo();
		if (id > 0) {
			o = orderDao.findById(id);
		}
		o.setAddtime(d);

		o.setOrderId(orderId);
		o.setShopId(shopId);
		o.setCookId(cookId);
		o.setContent(content);
		o.setAmount(amount);
		o.setIncome(income);
		o.setPay(pay);
		genericDao.saveOrUpdate(o);
	}

	public void deleteById(int id) {
		this.genericDao.deleteByParam("delete from OrderInfo as a where a.id=" + id);
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderInfo findByOrderId(String orderId) {
		return orderDao.findByOrderId(orderId);
	}
}
