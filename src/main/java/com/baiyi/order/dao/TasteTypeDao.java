package com.baiyi.order.dao;

import com.baiyi.order.pojo.TasteType;

public interface TasteTypeDao extends CommonsDao<TasteType> {

	public TasteType find(String name);
}
