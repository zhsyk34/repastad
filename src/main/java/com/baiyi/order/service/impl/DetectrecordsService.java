package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.IDetectrecordsDAO;
import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.service.IDetectrecordsService;
import com.baiyi.order.util.PageBean;

@Service("iDetectrecordsService")
public class DetectrecordsService implements IDetectrecordsService {
	@Resource
	private IDetectrecordsDAO iDetectrecordsDAO;
	@Resource
	private GenericDao genericDao;
	
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public IDetectrecordsDAO getiDetectrecordsDAO() {
		return iDetectrecordsDAO;
	}
	
	public void setiDetectrecordsDAO(IDetectrecordsDAO iDetectrecordsDAO) {
		this.iDetectrecordsDAO = iDetectrecordsDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.baiyi.tvset.service.IDetectrecordsService#save(com.baiyi.tvset.dao.Detectrecords)
	 */
	public void save(Detectrecords detectrecords) {
		iDetectrecordsDAO.save(detectrecords);
	}
	
	public List querySomeDetectrecords(String no,String begindate,String enddate,String queryline,String querystatus,PageBean pageBean){
		return this.iDetectrecordsDAO.findByPage(no, begindate, enddate, queryline, querystatus, pageBean);
	}

	public void deleteRecord(Date endDate) {
		this.iDetectrecordsDAO.deleteRecord(endDate);
	}
//	List list = iDetectrecordsDAO.findAll();
//	Collections.sort(list,new Comparator<Detectrecords>() {
//		
//		public int compare(Detectrecords o1, Detectrecords o2) {
//			return o2.getAddtime().compareTo(o1.getAddtime());
//		}
//	});	
//	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//	List tempList = new ArrayList();
//	for (Object object : list) {
//		Detectrecords detectrecords = (Detectrecords) object;
//		if ((queryline==null||queryline.equals("")||detectrecords.getLine().indexOf(queryline)!=-1)
//				&&(querystatus==null||querystatus.equals("")||detectrecords.getStatus().indexOf(querystatus)!=-1)
//				&&(detectrecords.getNo().equals(no))) {
//			if (begindate==null||enddate==null||begindate.equals("")||enddate.equals("")) {
//				tempList.add(detectrecords);
//			}else {
//				try {
//					Date beginDate = simpleDateFormat.parse(begindate);
//					beginDate.setHours(0);
//					beginDate.setMinutes(0);
//					beginDate.setSeconds(0);
//					Date endDate = simpleDateFormat.parse(enddate);
//					Calendar calendar =Calendar.getInstance();
//					calendar.setTime(endDate);
//					calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
//					endDate=calendar.getTime();
//					endDate.setHours(0);
//					endDate.setMinutes(0);
//					endDate.setSeconds(0);
//					if (!detectrecords.getAddtime().before(beginDate)&&detectrecords.getAddtime().before(endDate)) {
//						tempList.add(detectrecords);
//					}
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	pageBean.setPageSize(15);
//	pageBean.setTotalCount(tempList.size());
//	if (pageBean.getTotalPage()>=pageBean.getCurrentPage()) {
//		pageBean.setCurrentPage(pageBean.getCurrentPage());
//	}else {
//		pageBean.setCurrentPage(1);
//	}
//	pageBean.setResult(tempList.subList(pageBean.getFirstRecord(), pageBean.getLastRecord()));

	public Detectrecords findLastRecord(String no) {
		List list = this.genericDao.findBySqlParam("select max(id) from Detectrecords where no = ?",new Object[]{no});
		if(list!=null && !list.isEmpty()){
			Object obj = list.get(0);
			if(obj!=null){
				return (Detectrecords) this.genericDao.findById(Detectrecords.class, (Integer)obj);
			}else {
				return null;
			}
		}
		return null;
	}
}
