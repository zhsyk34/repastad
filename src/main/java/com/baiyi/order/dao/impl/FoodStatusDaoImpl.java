package com.baiyi.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.FoodStatusDao;
import com.baiyi.order.pojo.FoodStatus;

@Repository
public class FoodStatusDaoImpl extends CommonsDaoImpl<FoodStatus> implements FoodStatusDao {

	@SuppressWarnings("unchecked")
	public FoodStatus find(Integer foodId, Integer terminalId) {
		List<FoodStatus> list = (List<FoodStatus>) hibernateTemplate.find("from FoodStatus as f where f.foodId = ? and f.terminalId = ?", new Integer[] { foodId, terminalId });
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<FoodStatus> findList(String foodName, String terminalNo, Integer pattern) {

		Session session = hibernateTemplate.getSessionFactory().openSession();

		StringBuffer hql = new StringBuffer("select fs");
		hql.append(" from FoodStatus as fs, Food as f, Terminal as t where 1 = 1");
		hql.append(" and fs.foodId = f.id and fs.terminalId = t.id");

		Map<String, Object> map = new HashMap<String, Object>();

		if (foodName != null && foodName.trim().length() > 0) {
			hql.append(" and f.name like :foodName");
			map.put("foodName", "%" + foodName + "%");
		}
		if (terminalNo != null && terminalNo.trim().length() > 0) {
			hql.append(" and t.terminalId like :terminalNo");
			map.put("terminalNo", "%" + terminalNo + "%");
		}
		if (pattern != null && pattern > 0) {
			hql.append(" and fs.pattern = :pattern");
			map.put("pattern", pattern);
		}

		Query query = session.createQuery(hql.toString());
		if (map != null) {
			query.setProperties(map);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<FoodStatus> findList(String terminalNo) {
		Session session = hibernateTemplate.getSessionFactory().openSession();

		StringBuffer hql = new StringBuffer("select fs");
		hql.append(" from FoodStatus as fs,Terminal as t");
		hql.append(" where fs.terminalId = t.id");

		Map<String, Object> map = new HashMap<String, Object>();

		if (terminalNo != null && terminalNo.trim().length() > 0) {
			hql.append(" and t.terminalId = :terminalNo");
			map.put("terminalNo", terminalNo);
		}

		Query query = session.createQuery(hql.toString());
		if (map != null) {
			query.setProperties(map);
		}
		return query.list();
	}
}
