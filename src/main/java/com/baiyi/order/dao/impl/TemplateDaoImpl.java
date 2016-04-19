package com.baiyi.order.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.TemplateDao;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class TemplateDaoImpl implements TemplateDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<Template> findByPage(final String name, final PageBean pageBean) {
		try {
			return (List<Template>) this.hibernateTemplate.execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(a.id) from Template as a where 1=1");
					StringBuffer search_hql = new StringBuffer("from Template as a where 1=1");
					if (name != null) {
						count_hql.append(" and a.name like:name");
						search_hql.append(" and a.name like:name");
					}

					search_hql.append(" order by a.id desc");

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());
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
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TemplateDaoImpl findByPage ERROR");
			throw re;
		}
	}

	public Template findById(int id) {
		try {
			List list = this.hibernateTemplate.find("from Template as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (Template) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TemplateDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public List<Template> findByFoodId(String column, int id) {
		try {
			List<Template> list = (List<Template>) this.hibernateTemplate.find("from Template as a where a." + column + " like ? ", new Object[] { "%" + id + "%" });
			if (list != null) {
				return list;
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TemplateDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public List<Template> findByName(String name) {
		return (List<Template>) this.hibernateTemplate.find("from Template as a where a.name = ?", name);
	}

	public void save(Template template) {
		this.hibernateTemplate.save(template);
	}

}
