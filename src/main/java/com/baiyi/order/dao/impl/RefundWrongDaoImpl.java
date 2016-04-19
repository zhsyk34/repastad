package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.baiyi.order.dao.RefundWrongDao;
import com.baiyi.order.pojo.RefundWrong;

@Component("refundWrongDao")
public class RefundWrongDaoImpl implements RefundWrongDao {

	private HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void delete(Integer id) {
		hibernateTemplate.delete(find(id));
	}

	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			delete(id);
		}
	}

	public RefundWrong find(Integer id) {
		return (RefundWrong) hibernateTemplate.get(RefundWrong.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RefundWrong> findList(final String terminalId, final String orderId, final String sno, final String beginTime, final String endTime, final int isGet, final int adminId, final int pageNo, final int pageSize) {
		return (List<RefundWrong>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hql = new StringBuffer("from RefundWrong as r where 1= 1");
				//
				if (terminalId != null && terminalId.trim().length() > 0) {
					hql.append(" and r.terminalId like :terminalId");
				}
				if (orderId != null && orderId.trim().length() > 0) {
					hql.append(" and r.orderId like :orderId");
				}
				if (sno != null && sno.trim().length() > 0) {
					hql.append(" and r.sno like :sno");
				}
				if (beginTime != null && beginTime.trim().length() > 0) {
					hql.append(" and r.happenTime >= :beginTime");
				}
				if (endTime != null && endTime.trim().length() > 0) {
					hql.append(" and r.happenTime <= :endTime");
				}
				if (isGet > 0) {
					hql.append(" and r.isGet = :isGet");
				}
				if (adminId > 0) {
					hql.append(" and r.adminId = :adminId");
				}
				hql.append(" order by r.id desc");

				//
				Query query = session.createQuery(hql.toString());
				if (terminalId != null && terminalId.trim().length() > 0) {
					query.setString("terminalId", "%" + terminalId + "%");
				}
				if (orderId != null && orderId.trim().length() > 0) {
					query.setString("orderId", "%" + orderId + "%");
				}
				if (sno != null && sno.trim().length() > 0) {
					query.setString("sno", "%" + sno + "%");
				}
				if (beginTime != null && beginTime.trim().length() > 0) {
					query.setString("beginTime", beginTime + " 00:00:00");
				}
				if (endTime != null && endTime.trim().length() > 0) {
					query.setString("endTime", endTime + " 23:59:59");
				}
				if (isGet > 0) {
					query.setInteger("isGet", isGet);
				}
				if (adminId > 0) {
					query.setInteger("adminId", adminId);
				}
				if (pageNo > 0 && pageSize > 0) {
					query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
				}
				return query.list();
			}

		});
	}

	public void save(RefundWrong refundWrong) {
		hibernateTemplate.save(refundWrong);
	}

	public void update(RefundWrong refundWrong) {
		hibernateTemplate.update(refundWrong);
	}

	public RefundWrong find(String sno) {
		List<RefundWrong> list = (List<RefundWrong>) hibernateTemplate.find("from RefundWrong as r where r.sno = ?", sno);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

}
