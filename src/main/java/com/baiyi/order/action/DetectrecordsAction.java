package com.baiyi.order.action;

import java.util.List;

import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.IDetectrecordsService;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;

public class DetectrecordsAction extends BasicAction {
	
	/**
	 * 查詢監控記錄
	 * @return
	 */
	public String querySomeDetectrecords() {
		if(this.pageSize == 0){
			this.pageSize = admins.getPageCount();
		}else if(this.pageSize !=0 && this.pageSize!=admins.getPageCount()){
			admins.setPageCount(pageSize);
			this.adminsService.updatePageCount(admins.getId(), pageSize);
			session.put("user", admins);
		}
		PageBean pageBean = new PageBean(this.pageSize);
		if (startPage > 0) {
			pageBean.setStartPage(startPage);
		} else {
			pageBean.setStartPage(1);
		}
		this.begindate = (String) Utility.checkParmeter(this.begindate);
		this.enddate = (String) Utility.checkParmeter(this.enddate);
		this.queryline = (String) Utility.checkParmeter(this.queryline);
		this.querystatus = (String) Utility.checkParmeter(this.querystatus);
		this.no = (String) Utility.checkParmeter(this.no);
		List<Detectrecords> resultList = iDetectrecordsService.querySomeDetectrecords(no, begindate, enddate, queryline, querystatus, pageBean);
		request.setAttribute("resultList", resultList);
		request.setAttribute("pageBean", pageBean);
		return SUCCESS;
	}
	
	public String detectPicturePage(){
		return SUCCESS;
	}
	
	private IDetectrecordsService iDetectrecordsService;
	private AdminsService adminsService;
	private String begindate;
	private String enddate;
	private String queryline;
	private String querystatus;
	private String no;
	private int startPage;
	private int pageSize;
	private int totalCount;
	private int maxPage;

	
	private Detectrecords detectrecords;
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public Detectrecords getDetectrecords() {
		return detectrecords;
	}
	public void setDetectrecords(Detectrecords detectrecords) {
		this.detectrecords = detectrecords;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public IDetectrecordsService getiDetectrecordsService() {
		return iDetectrecordsService;
	}
	public void setiDetectrecordsService(IDetectrecordsService detectrecordsService) {
		iDetectrecordsService = detectrecordsService;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getQueryline() {
		return queryline;
	}
	public void setQueryline(String queryline) {
		this.queryline = queryline;
	}
	public String getQuerystatus() {
		return querystatus;
	}
	public void setQuerystatus(String querystatus) {
		this.querystatus = querystatus;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}
	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
