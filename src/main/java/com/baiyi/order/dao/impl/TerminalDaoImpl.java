package com.baiyi.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.TerminalDao;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class TerminalDaoImpl implements TerminalDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<Terminal> findByPage(final int type, final String terminalId, final PageBean pageBean) {
		try {
			return (List<Terminal>) hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(a.id) from Terminal as a where 1=1");
					StringBuffer search_hql = new StringBuffer("from Terminal as a where 1=1");
					if (type > 0) {
						count_hql.append(" and a.type=:type");
						search_hql.append(" and a.type=:type");
					}
					if (terminalId != null) {
						count_hql.append(" and a.terminalId like:terminalId");
						search_hql.append(" and a.terminalId like:terminalId");
					}

					search_hql.append(" order by a.terminalId asc");

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());
					if (type > 0) {
						count_query.setInteger("type", type);
						search_query.setInteger("type", type);
					}
					if (terminalId != null) {
						count_query.setString("terminalId", "%" + terminalId + "%");
						search_query.setString("terminalId", "%" + terminalId + "%");
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
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findByPage ERROR");
			throw re;
		}
	}

	public Terminal findById(int id) {
		try {
			List list = hibernateTemplate.find("from Terminal as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (Terminal) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public List<Terminal> findAll() {
		try {
			List list = hibernateTemplate.find("from Terminal as ti order by ti.terminalId");
			return list;
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findById ERROR");
			throw re;
		}
	}

	public Terminal findByTerminalId(String tid) {
		try {
			List list = hibernateTemplate.find("from Terminal as a where a.terminalId=?", tid);
			if (list != null && !list.isEmpty()) {
				return (Terminal) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findByTerminalId ERROR");
			throw re;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Terminal> find(final int type, final String terminalId) {
		return (List<Terminal>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("from Terminal as t where 1 = 1");
				Map<String, Object> map = new HashMap<String, Object>();
				if (type > 0) {
					hql.append(" and t.type = :type");
					map.put("type", type);
				}
				if (StringUtils.isNotBlank(terminalId)) {
					hql.append(" and t.terminalId like :terminalId");
					map.put("terminalId", "%" + terminalId + "%");
				}
				Query query = session.createQuery(hql.toString());
				query.setProperties(map);

				return query.list();
			}
		});
	}

	public List<Terminal> findByType(int type) {
		return (List<Terminal>) hibernateTemplate.find("from Terminal as t where t.type = ?", type);
	}

}
