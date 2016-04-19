package com.baiyi.order.dao;

import java.util.List;
import java.util.Map;

import com.baiyi.order.util.PageBean;

public interface GenericDao<T> {

	int deleteByIdList(String hql,List<Integer> idList);
	
	List findByIdList(final String hql, final List idList);

	void save(T entity);

	void delete(T entity);

	void saveOrUpdate(T entity);
	
	void update(T entity);

	int deleteByParam(String param);
	
	T findById(Class<T> entityClass, Integer id);
	
	void deleteBySql(String sql,Object[] param);
	
	List findByParamPageBean(final String poName,final Map<Object,Object> param,final PageBean pageBean);
	
	List findBySql(String sql);
	
	List findBySqlParam(String sql,Object[] param);
	
	void updateBySql(String sql,Object[] param);
	
	List findBySQL(String sql);
	
}
