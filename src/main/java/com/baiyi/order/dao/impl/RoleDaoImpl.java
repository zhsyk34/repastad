package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.baiyi.order.dao.RoleDao;
import com.baiyi.order.pojo.AdminRole;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.pojo.RoleAuthority;

@Component("roleDao")
public class RoleDaoImpl implements RoleDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void del(int id) {
		hibernateTemplate.delete(find(id));
	}

	public void del(int[] ids) {
		for (int id : ids) {
			del(id);
		}
	}

	public Role find(int id) {
		return (Role) hibernateTemplate.get(Role.class, id);
	};

	public List<Authority> findAuthorityList(int roleId) {
		String hql = "select a from Authority as a, RoleAuthority as ra ";
		hql += "where a.id = ra.authorityId and ra.roleId = ?";
		return (List<Authority>) hibernateTemplate.find(hql, roleId);
	}

	public List<Role> findList() {
		return (List<Role>) hibernateTemplate.find("from Role", null);
	}

	public List<RoleAuthority> findRoleAuthorityList(int roleId) {
		String hql = "select ra from RoleAuthority as ra where ra.roleId = ?";
		return (List<RoleAuthority>) hibernateTemplate.find(hql, roleId);
	}

	public void mod(Role role) {
		hibernateTemplate.update(role);

	}

	public void save(Role role) {
		hibernateTemplate.save(role);
	}

	public List<AdminRole> findAdminRoleList(int roleId) {
		String hql = "from AdminRole as ar where ar.roleId = ?";
		return (List<AdminRole>) hibernateTemplate.find(hql, roleId);
	}

	public List<Role> findList(int flag) {
		return (List<Role>) hibernateTemplate.find("from Role as r where r.permanent = ?", flag);
	}

	public List<Role> findByName(String name) {
		return (List<Role>) hibernateTemplate.find("from Role as r where r.name = ?", name);
	}

	public List<Role> findList(final String name, final int begin, final int length) {
		return (List<Role>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select r from Role as r where 1 = 1 ";
				if (name != null && name.trim().length() > 0) {
					hql += "and r.name like :name ";
				}
				hql += "order by r.id";
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

	public List<Role> findList(final int flag, final String name, final int begin, final int length) {
		return (List<Role>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select r from Role as r where r.permanent = :flag ";
				if (name != null && name.trim().length() > 0) {
					hql += "and r.name like :name ";
				}
				hql += "order by r.id";
				Query q = session.createQuery(hql);
				q.setInteger("flag", flag);
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
