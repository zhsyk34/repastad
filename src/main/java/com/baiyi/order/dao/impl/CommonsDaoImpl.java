package com.baiyi.order.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.baiyi.order.dao.CommonsDao;

public class CommonsDaoImpl<Entity> implements CommonsDao<Entity> {

	private Class<Entity> entityClass;

	protected HibernateTemplate hibernateTemplate;

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	public CommonsDaoImpl() {
		entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(Entity entity) {
		hibernateTemplate.save(entity);
	}

	public void delete(Integer id) {
		hibernateTemplate.delete(find(id));
	}

	public void delete(Entity entity) {
		hibernateTemplate.delete(entity);
	}

	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			delete(id);
		}
	}

	public void update(Entity entity) {
		hibernateTemplate.update(entity);
	}

	public void merge(Entity entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public Entity find(Integer id) {
		return (Entity) hibernateTemplate.get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<Entity> findList() {
		String queryString = "from " + entityClass.getSimpleName() + " as entry order by entry.id desc";
		return (List<Entity>) hibernateTemplate.find(queryString);
	}

}
