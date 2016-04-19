package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.MarqueeDao;
import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class MarqueeDaoImpl implements MarqueeDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public void deleteById(int id) {
		try {
			Marquee marquee = this.findById(id);
			this.hibernateTemplate.delete(marquee);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl deleteById ERROR");
			throw re;
		}
	}

	public Marquee findById(int id) {
		try {
			return (Marquee) this.hibernateTemplate.get(Marquee.class, id);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl findById ERROR");
			throw re;
		}
	}

	public void saveMarquee(Marquee marquee) {
		try {
			this.hibernateTemplate.saveOrUpdate(marquee);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl saveMarquee ERROR");
			throw re;
		}
	}

	public void updateMarquee(Marquee marquee) {
		try {
			this.hibernateTemplate.update(marquee);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl updateMarquee ERROR");
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Marquee> searchMarquee(final String title, final String content) {
		try {
			return (List<Marquee>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuilder searchStr = new StringBuilder("from Marquee as m where 1=1");
					if (title != null) {
						searchStr.append(" and m.title like:title");
					}
					if (content != null) {
						searchStr.append(" and m.m_content like:content");
					}
					Query searchQuery = session.createQuery(searchStr.toString());
					if (title != null) {
						searchQuery.setString("title", title);
					}
					if (content != null) {
						searchQuery.setString("content", content);
					}
					return searchQuery.list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl searchMarquee ERROR");
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Marquee> searchMarqueeByPage(final String title, final String content, final int adminId, final PageBean pageBean, final String sortType, final List<Integer> idList) {
		try {
			return (List<Marquee>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(m.id) from Marquee as m where 1=1");
					StringBuffer search_hql = new StringBuffer("from Marquee as m where 1=1");

					if (title != null) {
						count_hql.append(" and m.title like:title");
						search_hql.append(" and m.title like:title");
					}
					if (content != null) {
						count_hql.append(" and m.m_content like:content");
						search_hql.append(" and m.m_content like:content");
					}
					if (adminId != 0) {
						count_hql.append(" and m.adminid=:adminid");
						search_hql.append(" and m.adminid=:adminid");
					}
					if (idList != null && idList.size() > 0) {
						count_hql.append(" and m.id in (:idList)");
						search_hql.append(" and m.id in (:idList)");
					}

					search_hql.append(" order by m.id " + sortType);

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());
					if (title != null) {
						count_query.setString("title", "%" + title + "%");
						search_query.setString("title", "%" + title + "%");
					}
					if (content != null) {
						count_query.setString("content", "%" + content + "%");
						search_query.setString("content", "%" + content + "%");
					}
					if (adminId != 0) {
						count_query.setInteger("adminid", adminId);
						search_query.setInteger("adminid", adminId);
					}
					if (idList != null && idList.size() > 0) {
						count_query.setParameterList("idList", idList);
						search_query.setParameterList("idList", idList);
					}
					Integer rowCount = new Integer(((Long) count_query.uniqueResult()).toString());
					Integer startPage = pageBean.getStartPage();
					int pageSize = pageBean.getPageSize();
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
					// if(startPage*pageSize > rowCount){
					// startPage = 1;
					// }
					pageBean.setStartPage(startPage);
					pageBean.setMaxPage(maxPage);
					pageBean.setRowCount(rowCount);

					return search_query.setFirstResult((startPage - 1) * pageBean.getPageSize()).setMaxResults(pageBean.getPageSize()).list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MarqueeDaoImpl searchMarqueeByPage ERROR");
			throw re;
		}

	}

}
