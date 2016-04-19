package com.baiyi.order.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class GenericDaoImpl<T> implements GenericDao<T> {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public void delete(T entity) {
		try {
			hibernateTemplate.delete(entity);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl delete ERROR", re);
			throw re;
		}
	}

	public int deleteByIdList(final String hql, final List idList) {
		try {
			return (Integer) hibernateTemplate.execute(new HibernateCallback() {
				public Integer doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					query.setParameterList("ids", idList);
					return query.executeUpdate();
				}
			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl deleteByIdList ERROR", re);
			throw re;
		}
	}

	public List findByIdList(final String hql, final List idList) {
		try {
			return (List) hibernateTemplate.execute(new HibernateCallback() {
				public List doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					query.setParameterList("ids", idList);
					return query.list();
				}
			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl findByIdList ERROR", re);
			throw re;
		}
	}

	public T findById(Class<T> entityClass, Integer id) {
		try {
			return (T) hibernateTemplate.get(entityClass, id);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl findById ERROR", re);
			throw re;
		}
	}

	public void save(T entity) {
		try {
			hibernateTemplate.save(entity);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl save ERROR", re);
			throw re;
		}
	}

	public void saveOrUpdate(T entity) {
		try {
			hibernateTemplate.saveOrUpdate(entity);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl saveOrUpdate ERROR", re);
			throw re;
		}
	}

	public void update(T entity) {
		try {
			hibernateTemplate.update(entity);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl update ERROR", re);
			throw re;
		}
	}

	public int deleteByParam(final String param) {
		try {
			return (Integer) hibernateTemplate.execute(new HibernateCallback() {
				public Integer doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(param);
					return query.executeUpdate();
				}
			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl deleteByParam ERROR", re);
			throw re;
		}
	}

	public void deleteBySql(String sql, Object[] param) {
		try {
			hibernateTemplate.bulkUpdate(sql, param);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl deleteBySql ERROR", re);
			throw re;
		}
	}

	public List findByParamPageBean(final String poName, final Map<Object, Object> param, final PageBean pageBean) {
		try {
			return (List) hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					StringBuilder countStr = new StringBuilder("select count(t_table.id) from " + poName + " as t_table where 1=1");
					StringBuilder searchStr = new StringBuilder("from " + poName + " as t_table where 1=1");
					param.entrySet().iterator();

					for (Map.Entry<Object, Object> entry : param.entrySet()) {
						String key = (String) entry.getKey();
						countStr.append(" and t_table." + key + "=:" + key);
						searchStr.append(" and t_table." + key + "=:" + key);

					}
					searchStr.append(" order by t_table.id desc");

					Query countQuery = session.createQuery(countStr.toString());
					Query searchQuery = session.createQuery(searchStr.toString());

					for (Map.Entry<Object, Object> entry : param.entrySet()) {
						String key = (String) entry.getKey();
						Object value = (Object) entry.getValue();
						if (value instanceof String) {
							countQuery.setString(key, (String) value);
							searchQuery.setString(key, (String) value);
						} else if (value instanceof Integer) {
							countQuery.setInteger(key, (Integer) value);
							searchQuery.setInteger(key, (Integer) value);
						}
					}

					Integer maxCount = new Integer(((Long) countQuery.uniqueResult()).toString());
					Integer startPage = pageBean.getStartPage();
					Integer maxPage = null;
					Integer pageSize = pageBean.getPageSize();
					if (maxCount <= 0) {
						startPage = 1;
						maxPage = 1;
					} else {
						if (maxCount % pageSize == 0) {
							maxPage = maxCount / pageSize;
						} else {
							maxPage = maxCount / pageSize + 1;
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
					pageBean.setRowCount(maxCount);
					return searchQuery.setFirstResult((startPage - 1) * pageSize).setMaxResults(pageSize).list();
				}

			});

		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " GenericDaoImpl findByParamPageBean ERROR");
			throw re;
		}
	}

	public List findBySql(String sql) {
		try {
			return hibernateTemplate.find(sql);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " GenericDaoImpl findBySql ERROR");
			throw re;
		}
	}

	public List findBySqlParam(String sql, Object[] param) {
		try {
			return hibernateTemplate.find(sql, param);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " GenericDaoImpl findBySqlParam ERROR");
			throw re;
		}
	}

	public void updateBySql(String sql, Object[] param) {
		try {
			hibernateTemplate.bulkUpdate(sql, param);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " GenericDaoImpl updateBySql ERROR");
			throw re;
		}
	}

	public List findBySQL(String sql) {
		try {
			return hibernateTemplate.find(sql);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("GenericDaoImpl deleteByParam ERROR", re);
			throw re;
		}
	}
}
