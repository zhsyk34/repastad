package com.baiyi.order.service;

import com.baiyi.order.pojo.TasteType;

public interface TasteTypeService extends CommonsService<TasteType> {

	public TasteType find(String name);
}
