package com.baiyi.order.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.OrderDao;
import com.baiyi.order.pojo.OrderInfo;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<OrderInfo> findByPage(final String shopId, final String cookId, final String orderId, final String begindate, final String enddate, final PageBean pageBean) {
		try {
			return (List<OrderInfo>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(a.id) from OrderInfo as a where 1=1");
					StringBuffer search_hql = new StringBuffer("from OrderInfo as a where 1=1");
					if (shopId != null) {
						count_hql.append(" and a.shopId like:shopId");
						search_hql.append(" and a.shopId like:shopId");
					}
					if (cookId != null) {
						count_hql.append(" and a.cookId like:cookId");
						search_hql.append(" and a.cookId like:cookId");
					}
					if (orderId != null) {
						count_hql.append(" and a.orderId like:orderId");
						search_hql.append(" and a.orderId like:orderId");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begin = null;
					Date end = null;
					if (begindate != null) {
						try {
							begin = sdf.parse(begindate + " 00:00:00");
							count_hql.append(" and a.addtime >=:beginDate");
							search_hql.append(" and a.addtime >=:beginDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (enddate != null) {
						try {
							end = sdf.parse(enddate + " 23:59:59");
							count_hql.append(" and a.addtime <=:endDate");
							search_hql.append(" and a.addtime <=:endDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					search_hql.append(" order by a.id desc");

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());
					if (shopId != null) {
						count_query.setString("shopId", "%" + shopId + "%");
						search_query.setString("shopId", "%" + shopId + "%");
					}
					if (cookId != null) {
						count_query.setString("cookId", "%" + cookId + "%");
						search_query.setString("cookId", "%" + cookId + "%");
					}
					if (orderId != null) {
						count_query.setString("orderId", "%" + orderId + "%");
						search_query.setString("orderId", "%" + orderId + "%");
					}
					if (begin != null) {
						// countQuery.setDate("beginDate", begin);
						// searchQuery.setDate("beginDate", begin);
						count_query.setTimestamp("beginDate", begin);
						search_query.setTimestamp("beginDate", begin);
					}
					if (end != null) {
						count_query.setTimestamp("endDate", end);
						search_query.setTimestamp("endDate", end);
						// searchQuery.setDate("endDate", end);
					}
					Integer rowCount = new Integer(((Long) count_query.uniqueResult()).toString());
					Integer startPage = pageBean.getStartPage();
					Integer maxPage = null;

					if (rowCount <= 0) {
						startPage = 1;
						maxPage = 1;
					} else {
						if (rowCount % pageBean.getPageSize() == 0) {
							maxPage = rowCount / pageBean.getPageSize();
						} else {
							maxPage = rowCount / pageBean.getPageSize() + 1;
						}
					}
					if (startPage > maxPage) {
						startPage = maxPage;
					}

					if (startPage <= 1) {
						startPage = 1;
					}

					pageBean.setStartPage(startPage);
					pageBean.setMaxPage(maxPage);
					pageBean.setRowCount(rowCount);

					return search_query.setFirstResult((startPage - 1) * pageBean.getPageSize()).setMaxResults(pageBean.getPageSize()).list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " OrderDaoImpl findByPage ERROR");
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfo> findByCal(final String shopId, final String cookId, final String begindate, final String enddate) {
		try {
			return (List<OrderInfo>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer search_hql = new StringBuffer("from OrderInfo as a where 1=1");
					if (shopId != null) {
						search_hql.append(" and a.shopId like:shopId");
					}
					if (cookId != null) {
						search_hql.append(" and a.cookId like:cookId");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begin = null;
					Date end = null;
					if (begindate != null) {
						try {
							begin = sdf.parse(begindate + " 00:00:00");
							search_hql.append(" and a.addtime >=:beginDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (enddate != null) {
						try {
							end = sdf.parse(enddate + " 23:59:59");
							search_hql.append(" and a.addtime <=:endDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					search_hql.append(" order by a.shopId asc");
					Query search_query = session.createQuery(search_hql.toString());
					if (shopId != null) {
						search_query.setString("shopId", "%" + shopId + "%");
					}
					if (cookId != null) {
						search_query.setString("cookId", "%" + cookId + "%");
					}
					if (begin != null) {
						search_query.setTimestamp("beginDate", begin);
					}
					if (end != null) {
						search_query.setTimestamp("endDate", end);
					}
					return search_query.list();
				}
			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " OrderDaoImpl findByCal ERROR");
			throw re;
		}
	}

	public OrderInfo findById(int id) {
		try {
			List list = this.hibernateTemplate.find("from OrderInfo as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (OrderInfo) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " OrderDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public OrderInfo findByOrderId(String orderId) {
		try {
			List list = this.hibernateTemplate.find("from OrderInfo as a where a.orderId=?", orderId);
			if (list != null && !list.isEmpty()) {
				return (OrderInfo) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " OrderDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

}
