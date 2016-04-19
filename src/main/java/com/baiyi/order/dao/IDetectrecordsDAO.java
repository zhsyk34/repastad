package com.baiyi.order.dao;

import java.util.Date;
import java.util.List;

import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.util.PageBean;

public interface IDetectrecordsDAO {

	// property constants
	public static final String NO = "no";
	public static final String STATION = "station";
	public static final String STATUS = "status";
	public static final String LINE = "line";

	public abstract void save(Detectrecords transientInstance);

	public abstract void delete(Detectrecords persistentInstance);

	public abstract Detectrecords findById(java.lang.Integer id);

	public abstract List findByExample(Detectrecords instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByNo(Object no);

	public abstract List findByStation(Object station);

	public abstract List findByStatus(Object status);

	public abstract List findByLine(Object line);

	public abstract List findAll();

	public abstract Detectrecords merge(Detectrecords detachedInstance);

	public abstract void attachDirty(Detectrecords instance);

	public abstract void attachClean(Detectrecords instance);
	
	void deleteRecord(Date endDate);
	
	List findByPage(String no,String begindate,String enddate,String queryline,String querystatus,PageBean pageBean);

}