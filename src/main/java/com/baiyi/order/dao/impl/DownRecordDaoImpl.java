package com.baiyi.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.DownRecordDao;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;

@Repository
public class DownRecordDaoImpl implements DownRecordDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	public DownRecord findById(int id) {
		try {
			List list = hibernateTemplate.find("from DownRecord as a where a.id=?", id);
			if (list != null && !list.isEmpty()) {
				return (DownRecord) list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findById ERROR");
			throw re;
		}
		return null;
	}

	// 查找下載記錄，如果isdelete為null則查找所有記錄，如果不是，則查找未刪除記錄
	public DownRecord findByIsDelete(int templateId, String terminalId, Integer isDelete) {
		try {
			List<DownRecord> list = null;
			if (isDelete == null) {
				list = (List<DownRecord>) hibernateTemplate.find("from DownRecord as a where a.templateId=? and a.terminalId=?", new Object[] { templateId, terminalId });
			} else {
				list = (List<DownRecord>) hibernateTemplate.find("from DownRecord as a where a.templateId=? and a.terminalId=? and a.isDelete=?", new Object[] { templateId, terminalId, isDelete });
			}
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " TerminalDaoImpl findByTerminalId ERROR");
			throw re;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DownRecord> findBySearch(final Integer templateId, final String terminalId, final Integer isDelete, final Integer isDown) {
		try {
			return (List<DownRecord>) hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					StringBuilder searchStr = new StringBuilder("from DownRecord as d where 1=1");
					if (templateId != null) {
						searchStr.append(" and d.templateId=:templateId");
					}
					if (terminalId != null) {
						searchStr.append(" and d.terminalId=:terminalId");
					}
					if (isDelete != null) {
						searchStr.append(" and d.isDelete=:isDelete");
					}
					if (isDown != null) {
						searchStr.append(" and d.isDown=:isDown");
					}
					searchStr.append(" order by d.id desc");
					Query searchQuery = session.createQuery(searchStr.toString());
					if (templateId != null) {
						searchQuery.setInteger("templateId", templateId);
					}
					if (terminalId != null) {
						searchQuery.setString("terminalId", terminalId);
					}
					if (isDelete != null) {
						searchQuery.setInteger("isDelete", isDelete);
					}
					if (isDown != null) {
						searchQuery.setInteger("isDown", isDown);
					}
					return searchQuery.list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " DetectrecordsDAO findByPage ERROR", re);
			throw re;
		}
	}

}
