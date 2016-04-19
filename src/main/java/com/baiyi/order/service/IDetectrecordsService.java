package com.baiyi.order.service;

import java.util.Date;
import java.util.List;

import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.util.PageBean;

public interface IDetectrecordsService {

	void save(Detectrecords detectrecords);

	List querySomeDetectrecords(String no, String begindate, String enddate, String queryline, String querystatus, PageBean pageBean);

	void deleteRecord(Date endDate);

	Detectrecords findLastRecord(String no);
}