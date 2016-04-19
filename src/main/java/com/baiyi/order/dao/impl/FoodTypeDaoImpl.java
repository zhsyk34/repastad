package com.baiyi.order.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.FoodTypeDao;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.util.ValidateUtil;

@Repository("foodTypeDao")
public class FoodTypeDaoImpl extends CommonsDaoImpl<FoodType> implements FoodTypeDao {

	public int count(final String name) {
		return (Integer) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("select count(*) from FoodType as f where 1 = 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and f.typeName like :name");
				}
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setParameter("name", "%" + name + "%");
				}
				return Integer.parseInt(query.uniqueResult().toString());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<FoodType> findList(final String name, final int index, final int length) {
		return (List<FoodType>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("from FoodType as f where 1 = 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and f.typeName like :name");
				}
				hql.append(" order by f.id desc");
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setParameter("name", "%" + name + "%");
				}
				if (index >= 0 && length > 0) {
					query.setFirstResult(index).setMaxResults(length);
				}
				return query.list();
			}
		});
	}

}
