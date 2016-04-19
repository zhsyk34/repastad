package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.DinnerTableDao;
import com.baiyi.order.pojo.DinnerTable;
import com.baiyi.order.util.ValidateUtil;

@Repository("dinnerTableDao")
public class DinnerTableDaoImpl extends CommonsDaoImpl<DinnerTable> implements DinnerTableDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	public List<DinnerTable> findList(final String name, final String style, final int index, final int length) {
		return (List<DinnerTable>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("from DinnerTable as d where 1= 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and d.name like :name");
				}
				if (ValidateUtil.isStrictNotEmpty(style)) {
					hql.append(" and d.style like :style");
				}
				hql.append(" order by d.id desc");
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setString("name", "%" + name + "%");
				}
				if (ValidateUtil.isStrictNotEmpty(style)) {
					query.setString("style", "%" + style + "%");
				}
				if (index >= 0 && length > 0) {
					query.setFirstResult(index).setMaxResults(length);
				}
				return query.list();
			}
		});
	}

	public int count(final String name, final String style) {
		return (Integer) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("select count(*) from DinnerTable as d where 1 = 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and d.name like :name");
				}
				if (ValidateUtil.isStrictNotEmpty(style)) {
					hql.append(" and d.style like :style");
				}
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setString("name", "%" + name + "%");
				}
				if (ValidateUtil.isStrictNotEmpty(style)) {
					query.setString("style", "%" + style + "%");
				}
				return Integer.parseInt(query.uniqueResult().toString());
			}
		});
	}

}
