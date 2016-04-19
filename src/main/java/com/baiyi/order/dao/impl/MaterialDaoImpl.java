package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.MaterialDao;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class MaterialDaoImpl implements MaterialDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<Material> findByPage(final int type, final String name, final PageBean pageBean, final String sortType, final List<Integer> idList) {
		try {
			return (List<Material>) hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(m.id) from Material as m where 1=1");
					StringBuffer search_hql = new StringBuffer("from Material as m where 1=1");
					if (type > 0) {
						count_hql.append(" and m.type=:type");
						search_hql.append(" and m.type=:type");
					}
					if (name != null) {
						count_hql.append(" and m.name like:name");
						search_hql.append(" and m.name like:name");
					}
					if (idList != null && idList.size() > 0) {
						count_hql.append(" and m.id in (:idList)");
						search_hql.append(" and m.id in (:idList)");
					}

					search_hql.append(" order by m.id " + sortType);
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
					if (idList != null && idList.size() > 0) {
						count_query.setParameterList("idList", idList);
						search_query.setParameterList("idList", idList);
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
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MaterialDaoImpl findAdminsByPage ERROR");
			throw re;
		}
	}

	public Material findById(int id) {
		try {
			List list = hibernateTemplate.find("from Material as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (Material) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MaterialDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	public void saveMaterial(Material m) {
		try {
			hibernateTemplate.saveOrUpdate(m);
		} catch (Exception re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " MaterialDaoImpl saveMaterial ERROR");
			re.printStackTrace();
		}
	}

	public List<Material> search(final String name, final int start, final int length) {
		return (List<Material>) hibernateTemplate.execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				String hql = "from Material m where 1 = 1";
				if (name != null && name.trim().length() > 0) {
					hql += " and m.name like :name";
				}
				hql += " order by m.id desc";
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

	public Material findByLog(int isLogo) {
		List<Material> list = (List<Material>) hibernateTemplate.find("from Material m where m.isLogo = ?", isLogo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void updateMaterial(int id, int isLogo) {
		Material m = findById(id);
		if (m != null) {
			m.setIsLogo(isLogo);
			hibernateTemplate.update(m);
		}
	}
}
