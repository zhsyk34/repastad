package com.baiyi.order.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.MarqueeService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;
import com.opensymphony.xwork2.util.ResolverUtil.IsA;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class MarqueeAction extends BasicAction {
	/**
	 * 新增前準備
	 * @return
	 */
	public String saveMarqueeByPrepare() {
		return SUCCESS;
	}
	
	/**
	 * 保存更新跑馬燈
	 * @return
	 */
	public String saveMarquee() {
		this.admins = (Admins) session.get("user");
		try {
			this.color = (String) Utility.checkParmeter(this.color);
			this.size = (String) Utility.checkParmeter(this.size);
			this.title = (String) Utility.checkParmeter(this.title);
			this.direction = (String) Utility.checkParmeter(this.direction);
			this.content = (String) Utility.checkParmeter(this.content);
			this.fontfamily = (String) Utility.checkParmeter(this.fontfamily);
			this.bgcolor = (String) Utility.checkParmeter(this.bgcolor);
			if(this.speed!=null && this.speed.length()>3){
				this.speed = speed.substring(0,4);
			}
			this.size = size+"px";
			boolean isCheckSuccess = true;
			if(this.title == null){
				request.setAttribute("titleError", this.getText("marquee.add.titleRequired"));
				isCheckSuccess = false;
			}
			if (this.content == null) {
				request.setAttribute("contentError", this.getText("marquee.add.contentRequired"));
				isCheckSuccess = false;
			}
			content = content.replace("\r\n", " ").replace("\n", " ");
			if(isCheckSuccess){
				if(direction == null){
					direction = "left";
				}
				int adminId = this.admins.getId();
				StringBuilder destPath = new StringBuilder(servletContext.getRealPath("/"));
				this.marqueeService.saveOrUpdate(id, color,size,title,direction, content,speed,fontfamily,colorOrImg,bgcolor,isRss,adminId);
				request.setAttribute("message", this.getText("write.success"));
				this.message = URLEncoder.encode(this.getText("write.success"),"UTF-8");
				return SUCCESS;
			}else{
				request.setAttribute("message", this.getText("write.error"));
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("write.error"));
			Log4JFactory.instance().error("MarqueeAction saveOrUpdateMarquee ERROR",e);
		}
		return INPUT; 
	}
	/**
	 * 刪除跑馬燈
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteMarquee(){
		JSONObject resultObj = new JSONObject();
		try{
			if(this.id>0){
				Marquee marquee = this.marqueeService.findById(id);
				if(marquee!=null){
					this.marqueeService.deleteMarqueeById(id);
					resultObj.put("success", true);
				}else {
					resultObj.put("success", false);
				}
			}else{
				resultObj.put("errorMsg", this.getText("param.error"));
				resultObj.put("success", false);
			}
		}catch(Exception e){
			resultObj.put("errorMsg", this.getText("delete.error"));
			resultObj.put("success", false);
			Log4JFactory.instance().error("MarqueeAction deleteMarquee ERROR",e);
		}
		try{
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(resultObj);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}catch(Exception e){
			Log4JFactory.instance().error(
					"NewsAction deleteMarquee Stream ERROR", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根據id查詢跑馬燈
	 * @return
	 */
	public String findMarquee(){
		try{
			if(this.id>0){
				Marquee marquee = this.marqueeService.findById(id);
				StringBuilder ImgStr = new StringBuilder();
				if(marquee.getColorOrImg()!=1){
					getMaterialPath(marquee.getBackground(),ImgStr);
				}
				request.setAttribute("ImgStr", ImgStr.toString());
				request.setAttribute("marquee", marquee);
				if(marquee.getDirection().equals("left") || marquee.getDirection().equals("right")){
					return SUCCESS;
				}else {
					return "successver";
				}
			}
		}catch(Exception e){
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("MarqueeAction findMarquee ERROR",e);
		}
		return INPUT;
	}
	

	public void getMaterialPath(String item,StringBuilder resultStr){
		if(item != null){
			String[] itemArray = item.split(";");
			for(int i = 0;i<itemArray.length;i++){
				int mid = new Integer(itemArray[i]);
				Material  material = this.materialService.findById(mid);
				if(material!=null){
					String path = material.getPath().replace(File.separator, "/");
					resultStr.append(path+"<"+mid+">;");
				}
			}
		}
	}


	/**
	 * 跑马灯管理查询
	 */
	public String searchMarquee(){
		try{
			this.title = (String) Utility.checkParmeter(title);
			this.content = (String) Utility.checkParmeter(this.content);
			if(this.pageSize == 0){
				this.pageSize = admins.getPageCount();
			}else if(this.pageSize !=0 && this.pageSize!=admins.getPageCount()){
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
			}
			PageBean pageBean = new PageBean(this.pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			int adminId = 0;
			if(sortType == null){
				sortType = "desc";
			}
			List<Marquee> marqueeList = this.marqueeService.findMarqueeByPage(title, content,adminId,pageBean,sortType,null);
			for(Marquee marquee:marqueeList){
				if(marquee.getIsRss()==1){
					String content = marquee.getM_content();
					content = content.split("@rss@")[0];
					marquee.setM_content(content);
				}
			}
			if(this.message!=null){
				request.setAttribute("message", URLDecoder.decode(this.message,"UTF-8"));
			}
			request.setAttribute("marqueeList", marqueeList);
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("forwardPage", "marquee");
			if(select){
				return "select";
			}
			return SUCCESS;
		}catch(Exception e){
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("MarqueeAction searchMarquee ERROR",e);
		}
		return INPUT;
	}
	/**
	 * 搜尋跑馬燈(節目選擇)
	 * @return
	 */
	public String searchMarqueePage(){
		try{
			this.title = (String) Utility.checkParmeter(title);
			this.content = (String) Utility.checkParmeter(this.content);
			sortType = (String) Utility.checkParmeter(sortType);
			idList = (String) Utility.checkParmeter(idList);
			if(sortType == null){
				sortType = "desc";
			}
			List<Integer> idList =  new ArrayList<Integer>();
			if(this.idList!=null && this.idList.length()>0){
				String[] idArray = this.idList.split(";");
				for(int i = 0;i<idArray.length;i++){
					try{
						Integer id= new Integer(idArray[i]);
						idList.add(id);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(this.pageSize == 0){
				this.pageSize = admins.getPageCount();
			}else if(this.pageSize !=0 && this.pageSize!=admins.getPageCount()){
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			int adminId = 0;
			List<Marquee> marqueeList = this.marqueeService.findMarqueeByPage(title,content,adminId,pageBean,sortType,idList);
			for(Marquee marque:marqueeList){
				String content = marque.getM_content();
				if(content.indexOf("'")!=-1 || content.indexOf("\"")!=-1){
					marque.setM_content(content.replace("'", "‘").replace("\"", "“"));
				}
				if(marque.getIsRss()==1){
					content = content.split("@rss@")[0];
					marque.setM_content(content);
				}
			}
			request.setAttribute("marqueeList", marqueeList);
			request.setAttribute("pageBean", pageBean);
			if(title!=null || content!=null || this.idList!=null){
				request.setAttribute("searchType", "marquee");
			}
			if(isForDynamicMarquee==1){
				//公車跑馬燈查詢下載
				return "success2";
			}else {
				return SUCCESS;
			}
		}catch(Exception e){
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("MarqueeAction searchMarqueePage ERROR", e);
		}
		return INPUT;
	}

	/**
	 * 批量删除多个跑马灯
	 * @return
	 */
	public String deleteByIdList(){
		JSONObject resultObj = new JSONObject();
		try{
			this.idList = (String) Utility.checkParmeter(this.idList);
			if(idList!=null){
				List<Integer> idList =  new ArrayList<Integer>();
				String[] idArray = this.idList.split(",");
				int adminId = this.admins.getId();
				for(int i = 0;i<idArray.length;i++){
					Integer  id= new Integer(idArray[i]);
					Marquee marquee = this.marqueeService.findById(id);
//					if(marquee != null){
//						this.operRecordService.saveOperRecord(0, adminId, TypeBean.MARQUEE, this.getText("marquee.delete")+marquee.getTitle());
//					}
					idList.add(id);
				}
				int result = this.marqueeService.deleteByIdList(idList);
				if(result>0){
					resultObj.put("success", true);
				}else{
					resultObj.put("errorMsg", this.getText("delete.error"));
					resultObj.put("success", false);
				}
			}
		}catch(Exception e){
			resultObj.put("errorMsg", this.getText("delete.error"));
			resultObj.put("success", false);
			Log4JFactory.instance().error("MarqueeAction deleteMarquee ERROR",e);
		}
		try{
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(resultObj);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}catch(Exception e){
			Log4JFactory.instance().error(
					"MarqueeAction deleteMarquee Stream ERROR", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取RSS新闻
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRssNews() {
		JSONObject resutlObj = new JSONObject();
		// 从网络上访问
		try {
			if(rssUrl != null && rssUrl.trim().length()>0)	{
				URL url = new URL(rssUrl.trim());
				InputStream in = url.openStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(in,"utf-8"));
				StringBuilder rssNews = new StringBuilder();
				String re = null;
				boolean isUTF = true;
				String rssEncoding = "utf-8";
				while ((re = bufferedReader.readLine()) != null) {
					if(re.toLowerCase().indexOf("encoding")!=-1){
						 Pattern pattern = Pattern.compile("=\"([\\S]+)\"\\?");
						 Matcher mat = pattern.matcher(re.toLowerCase());
						 if(mat.find()){
							 rssEncoding = mat.group(1).toLowerCase();
							 if(!rssEncoding.equals("utf-8")){
								isUTF = false;
								break;
							 }
						 }
					}
					rssNews.append(re);
				}
				if(!isUTF){
					in = url.openStream();
					bufferedReader = new BufferedReader(
							new InputStreamReader(in,rssEncoding));
					rssNews = new StringBuilder();
					while ((re = bufferedReader.readLine()) != null) {
						rssNews.append(re);
					}
				}
				if (rssNews.length() > 0) {
					resutlObj = Utility.parseRss(rssNews.toString());
				}
			}else{
				resutlObj.put("success", false);
			}
		} catch (MalformedURIException malurie) {
			Log4JFactory.instance().error(
					"WebAction getRssNews MalformedURI Error", malurie);
			resutlObj.put("success", false);
		} catch (ConnectException ce) {
			Log4JFactory.instance().error("WebAction getRssNews timeout Error",
					ce);
			resutlObj.put("success", false);
		} catch (IOException ioe) {
			Log4JFactory.instance().error("WebAction getRssNews parse Error",
					ioe);
			resutlObj.put("success", false);
		} catch (Exception e) {
			Log4JFactory.instance().error("WebAction getRssNews get Error", e);
			resutlObj.put("success", false);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(resutlObj);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("WebAction getRssNews Stream ERROR",
					e);
		}
		return SUCCESS;
	}
	
	private int id;
	
	private String idList;
	
	private String title;

	private String color;
	
	private String size;
	
	private String fontfamily;

	private String content;
	
	private String speed;
	
	private String direction;
	
	private String searchTitle;
	
	private String searchContent;
	
	private int startPage;
	
	private int pageSize;
	
	private String message;
	
	private String applyReason;
	
	private MarqueeService marqueeService;
	
	private AdminsService adminsService;
	
	private int isForDynamicMarquee;//是否用於公車終端跑馬燈查詢
	
	private int colorOrImg;
	
	private String bgcolor;
	
	private int isRss;
	
	private String sortType;
	
	private MaterialService materialService;
	
	private String rssUrl;
	
	private boolean select = false;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getContent() {
		return content;
	}
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMarqueeService(MarqueeService marqueeService) {
		this.marqueeService = marqueeService;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getSearchTitle() {
		return searchTitle;
	}
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getFontfamily() {
		return fontfamily;
	}
	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getIsForDynamicMarquee() {
		return isForDynamicMarquee;
	}
	public void setIsForDynamicMarquee(int isForDynamicMarquee) {
		this.isForDynamicMarquee = isForDynamicMarquee;
	}

	public int getColorOrImg() {
		return colorOrImg;
	}

	public void setColorOrImg(int colorOrImg) {
		this.colorOrImg = colorOrImg;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public int getIsRss() {
		return isRss;
	}

	public void setIsRss(int isRss) {
		this.isRss = isRss;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	
}
