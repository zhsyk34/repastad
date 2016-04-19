package com.baiyi.order.service;

import java.util.List;

public interface CommonsService<Entity> {

	public void save(Entity entity);

	public void delete(Integer id);

	public void delete(Entity entity);

	public void delete(Integer[] ids);

	public void update(Entity entity);

	public void merge(Entity entity);

	public Entity find(Integer id);

	public List<Entity> findList();

}
