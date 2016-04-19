package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.TasteDao;
import com.baiyi.order.pojo.Taste;
import com.baiyi.order.util.ValidateUtil;
import com.baiyi.order.vo.TasteVO;

@Repository("tasteDao")
public class TasteDaoImpl extends CommonsDaoImpl<Taste> implements TasteDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public int count(final String name, final Integer type) {
		return (Integer) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("select count(*) from Taste as t where 1 = 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and t.name like :name");
				}
				if (type != null && type > 0) {
					hql.append(" and t.type = :type");
				}

				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setParameter("name", "%" + name + "%");
				}
				if (type != null && type > 0) {
					query.setParameter("type", type);
				}
				return Integer.parseInt(query.uniqueResult().toString());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Taste> findList(final String name, final Integer type, final int index, final int length) {
		return (List<Taste>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("from Taste as t where 1 = 1");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and t.name like :name");
				}
				if (type != null && type > 0) {
					hql.append(" and t.type = :type");
				}
				hql.append(" order by t.type desc");
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setParameter("name", "%" + name + "%");
				}
				if (type != null && type > 0) {
					query.setParameter("type", type);
				}
				if (index >= 0 && length > 0) {
					query.setFirstResult(index).setMaxResults(length);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TasteVO> findVOList(final String name, final Integer type, final int index, final int length) {
		return (List<TasteVO>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("select new com.baiyi.order.vo.TasteVO(t.id, t.name, tt.id, tt.name, t.price)");
				hql.append(" from Taste as t, TasteType as tt where  t.type = tt.id");
				if (ValidateUtil.isStrictNotEmpty(name)) {
					hql.append(" and t.name like :name");
				}
				if (type != null && type > 0) {
					hql.append(" and t.type = :type");
				}
				hql.append(" order by t.id desc");
				Query query = session.createQuery(hql.toString());
				if (ValidateUtil.isStrictNotEmpty(name)) {
					query.setParameter("name", "%" + name + "%");
				}
				if (type != null && type > 0) {
					query.setParameter("type", type);
				}
				if (index >= 0 && length > 0) {
					query.setFirstResult(index).setMaxResults(length);
				}
				return query.list();
			}
		});
	}
}
