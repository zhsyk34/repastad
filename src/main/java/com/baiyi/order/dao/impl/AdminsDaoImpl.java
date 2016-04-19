package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.AdminsDao;
import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class AdminsDaoImpl implements AdminsDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public void del(int id) {
		hibernateTemplate.delete(findById(id));
	}

	public void del(int[] ids) {
		for (int id : ids) {
			del(id);
		}
	}

	public List<Admins> findList() {
		try {
			return (List<Admins>) hibernateTemplate.find("from Admins as a where a.username != 'admin'");
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("AdminsDaoImpl findAdmins ERROR", re);
			throw re;
		}
	}

	public List<Admins> findByPage(final PageBean pageBean) {
		try {
			return (List<Admins>) hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					StringBuffer count_hql = new StringBuffer("select count(a.id) from Admins as a where 1=1");
					StringBuffer search_hql = new StringBuffer("from Admins as a where 1=1 order by a.id desc");

					Query count_query = session.createQuery(count_hql.toString());
					Query search_query = session.createQuery(search_hql.toString());

					Integer rowCount = new Integer(((Long) count_query.uniqueResult()).toString());
					Integer startPage = pageBean.getStartPage();
					Integer maxPage = null;

					if (rowCount <= 0) {
						startPage = 1;
						maxPage = 1;
					} else {
						maxPage = (rowCount + pageBean.getPageSize() - 1) / pageBean.getPageSize();
					}
					if (startPage > maxPage) {
						startPage = maxPage;
					}

					if (startPage < 1) {
						startPage = 1;
					}

					pageBean.setStartPage(startPage);
					pageBean.setMaxPage(maxPage);
					pageBean.setRowCount(rowCount);

					return search_query.setFirstResult((startPage - 1) * pageBean.getPageSize()).setMaxResults(pageBean.getPageSize()).list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " AdminsDaoImpl findAdminsByPage ERROR");
			throw re;
		}
	}

	public Admins findById(int id) {
		try {
			return (Admins) hibernateTemplate.get(Admins.class, id);
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " AdminsDaoImpl findById ERROR");
			throw re;
		}
	}

	public Admins findByName(String username) {
		String hql = "from Admins as a where a.username=?";
		try {
			List list = hibernateTemplate.find(hql, username);
			if (!list.isEmpty()) {
				return (Admins) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("AdminsDaoImpl findByNamePass ERROR", re);
			throw re;
		}
		return null;
	}

	public Admins findByNamePass(String username, String password) {
		try {
			String hql = "from Admins as a where a.username=? and a.password=?";
			List list = hibernateTemplate.find(hql, new Object[] { username, password });
			if (!list.isEmpty()) {
				return (Admins) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error("AdminsDaoImpl findByNamePass ERROR", re);
			throw re;
		}
		return null;
	}

	public void mod(Admins admins) {
		hibernateTemplate.update(admins);

	}

	public void save(Admins admins) {
		hibernateTemplate.save(admins);
	}

	public List<AdminRole> findAdminRoleList(int adminId) {
		String hql = "from AdminRole as ar where ar.adminId = ?";
		return (List<AdminRole>) hibernateTemplate.find(hql, adminId);
	}

	public List<Integer> findAuthorityList(int adminId) {
		String hql = "select distinct a.id from Authority as a, RoleAuthority as ra, AdminRole as ar ";
		hql += "where ra.roleId = ar.roleId and ra.authorityId = a.id and ar.adminId = ?";
		return (List<Integer>) hibernateTemplate.find(hql, adminId);
	}

	public List<Role> findRoleList(int adminId) {
		String hql = "select r from Role as r, AdminRole as ar ";
		hql += "where r.id = ar.roleId and ar.adminId = ?";
		return (List<Role>) hibernateTemplate.find(hql, adminId);
	}

	public List<Admins> findList(final String name, final int begin, final int length) {
		return (List<Admins>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select a from Admins as a where a.username != 'admin' ";
				if (name != null && name.trim().length() > 0) {
					hql += "and a.username like :name ";
				}
				hql += "order by a.id";
				Query q = session.createQuery(hql);
				if (name != null && name.trim().length() > 0) {
					q.setString("name", "%" + name + "%");
				}
				if (begin >= 0 && length > 0) {
					q.setFirstResult(begin).setMaxResults(length);
				}
				return q.list();
			}
		});
	}
}
