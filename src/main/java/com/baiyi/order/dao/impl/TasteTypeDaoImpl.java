package com.baiyi.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.TasteTypeDao;
import com.baiyi.order.pojo.TasteType;

@Repository("tasteTypeDao")
public class TasteTypeDaoImpl extends CommonsDaoImpl<TasteType> implements TasteTypeDao {

	@SuppressWarnings("unchecked")
	public TasteType find(String name) {
		List<TasteType> list = (List<TasteType>) hibernateTemplate.find("from TasteType as t where t.name = ?", name);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
