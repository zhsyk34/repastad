package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.OrderRuleDao;
import com.baiyi.order.pojo.OrderRule;

@Repository("orderRuleDao")
public class OrderRuleDaoImpl extends CommonsDaoImpl<OrderRule> implements OrderRuleDao {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public int count() {
		return (Integer) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("select count(*) from OrderRule");
				return Integer.parseInt(query.uniqueResult().toString());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<OrderRule> findList(final int index, final int length) {
		return (List<OrderRule>) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from OrderRule as o order by o.id desc");
				if (index >= 0 && length > 0) {
					query.setFirstResult(index).setMaxResults(length);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public OrderRule findByUsed(int used) {
		List<OrderRule> list = (List<OrderRule>) this.hibernateTemplate.find("from OrderRule as o where o.used = ?", used);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public OrderRule findById(int id) {
		List<OrderRule> list = (List<OrderRule>) this.hibernateTemplate.find("from OrderRule as o where o.id = ?", id);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
