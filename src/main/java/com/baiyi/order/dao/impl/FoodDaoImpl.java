package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.FoodDao;
import com.baiyi.order.pojo.Food;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class FoodDaoImpl implements FoodDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<Food> findByPage(final int type, final String name, final PageBean pageBean) {
		try {
			return (List<Food>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(a.id) from Food as a where 1=1");
					StringBuffer search_hql = new StringBuffer("from Food as a where 1=1");
					if (type > 0) {
						count_hql.append(" and a.type=:type");
						search_hql.append(" and a.type=:type");
					}
					if (name != null) {
						count_hql.append(" and a.name like:name");
						search_hql.append(" and a.name like:name");
					}

					search_hql.append(" order by a.id desc");

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());
					if (type > 0) {
						count_query.setInteger("type", type);
						search_query.setInteger("type", type);
					}
					if (name != null) {
						count_query.setString("name", "%" + name + "%");
						search_query.setString("name", "%" + name + "%");
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
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " FoodDaoImpl findByPage ERROR");
			throw re;
		}
	}

	public Food findById(int id) {
		try {
			List list = this.hibernateTemplate.find("from Food as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (Food) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " FoodDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public List<Food> getAllFood() {
		try {
			List list = this.hibernateTemplate.find("from Food order by id desc");
			if (list != null && !list.isEmpty()) {
				return list;
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " FoodDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public List<Food> search(final String name, final int start, final int length) {
		return (List<Food>) hibernateTemplate.execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				String hql = "from Food f ";
				if (name != null && name.trim().length() > 0) {
					hql += " where f.name like :name";
				}
				hql += " order by f.id desc";
				Query q = session.createQuery(hql);
				if (name != null && name.trim().length() > 0) {
					q.setString("name", "%" + name + "%");
				}
				if (start >= 0 && length > 0) {
					q.setFirstResult(start).setMaxResults(length);
				}
				return q.list();
			}
		});
	}

}
