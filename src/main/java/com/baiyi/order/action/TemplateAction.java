package com.baiyi.order.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.FoodTypeService;
import com.baiyi.order.service.MarqueeService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.TemplateService;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;

@SuppressWarnings("serial")
public class TemplateAction extends BasicAction {

	@Resource
	private FoodTypeService foodTypeService;

	// 保存修改
	public String saveTemplate() throws Exception {
		JSONObject resultObj = new JSONObject();
		try {
			this.name = (String) Utility.checkParmeter(this.name);
			this.banner = (String) Utility.checkParmeter(this.banner);
			this.marquee = (String) Utility.checkParmeter(this.marquee);
			this.cakeId = (String) Utility.checkParmeter(this.cakeId);
			this.selectPart = (String) Utility.checkParmeter(this.selectPart);
			this.video = (String) Utility.checkParmeter(this.video);
			this.picture = (String) Utility.checkParmeter(this.picture);
			this.picTime = (String) Utility.checkParmeter(this.picTime);
			this.effect = (String) Utility.checkParmeter(this.effect);
			this.templateService.saveTemplate(id, row, column, name, type, size, banner, marquee, cakeId, titleImg, selectPart, video, picture, picTime, effect, admins.getId());
			resultObj.put("success", true);
		} catch (Exception e) {
			resultObj.put("success", false);
			resultObj.put("errorMsg", this.getText("write.error"));
			Log4JFactory.instance().error("TemplateAction saveTemplate Error", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
		} catch (Exception e) {
			Log4JFactory.instance().error("TemplateAction saveTemplate stream ERROR", e);
		}
		return null;
	}

	// 删除
	public String deleteTemplate() {
		if (id > 0) {
			List<Terminal> terminalList = terminalService.findAll();
			if (terminalList != null && terminalList.size() > 0) {
				for (int i = 0; i < terminalList.size(); i++) {
					int templateId = terminalList.get(i).getTemplatePlay();
					if (templateId == id) {
						jsonMap.put("result", "used");
						return SUCCESS;
					}
				}
			}
			try {
				templateService.deleteById(id);
				jsonMap.put("result", "del");
			} catch (Exception e) {
				jsonMap.put("result", "error");
			}
		}
		return SUCCESS;
	}

	public String saveTemplateByPre() {
		return SUCCESS;
	}

	public String loadTemplate() {
		Template template = null;
		if (id > 0) {
			template = templateService.findById(id);
		}

		List<FoodType> typeList = foodTypeService.findList();
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (CollectionUtils.isNotEmpty(typeList)) {
			for (FoodType type : typeList) {
				typeMap.put(type.getId(), type.getTypeName());
			}
		}

		List<Food> foodList = foodService.search(null, -1, -1);
		Map<Integer, Food> foodMap = new HashMap<Integer, Food>();
		if (CollectionUtils.isNotEmpty(foodList)) {
			for (Food food : foodList) {
				foodMap.put(food.getId(), food);
			}
		}

		List<Material> materialList = materialService.search(null, -1, -1);
		Map<Integer, String> materialMap = new HashMap<Integer, String>();
		if (CollectionUtils.isNotEmpty(materialList)) {
			for (Material m : materialList) {
				Integer mid = m.getId();
				String path = m.getPath().replace("\\", "/");
				materialMap.put(mid, path);
			}
		}

		List<Marquee> marqueeList = marqueeService.findMarquee(null, null);
		Map<Integer, String> marqueeMap = new HashMap<Integer, String>();
		if (CollectionUtils.isNotEmpty(marqueeList)) {
			for (Marquee m : marqueeList) {
				marqueeMap.put(m.getId(), m.getTitle());
			}
		}

		jsonMap.put("marqueeMap", marqueeMap);
		jsonMap.put("materialMap", materialMap);
		jsonMap.put("foodMap", foodMap);
		jsonMap.put("template", template);
		jsonMap.put("typeMap", typeMap);
		return SUCCESS;
	}

	public String findTemplate() {
		try {
			Template t = this.templateService.findById(id);

			// 取得信息
			String[] cakeIdStr = t.getCakeId().split(",");
			String cakeStr = "";
			for (String cakeId : cakeIdStr) {
				if (cakeId.trim().length() != 0) {
					Integer id = Integer.valueOf(cakeId);
					Food food = foodService.findById(id);
					if (food != null) {
						cakeStr += food.getId() + ";" + food.getName() + ";" + food.getPrice() + ";";
						Material m = materialService.findById(food.getMaterialId());
						if (m != null) {
							cakeStr += m.getPath().replace("\\", "/");
						}
						cakeStr += "@";
					}
				}
			}
			t.setCakePath(cakeStr);
			if (t.getTitleImg() != 0) {
				Material m = materialService.findById(t.getTitleImg());
				if (m != null) {
					t.setTitleImgPath(m.getPath().replace("\\", "/"));
				}
			}
			if (t.getBanner() != null && t.getBanner().length() != 0) {
				String[] bannerIdStr = t.getBanner().split(",");
				String bannerStr = "";
				for (String bannerId : bannerIdStr) {
					if (bannerId.trim().length() != 0) {
						Integer id = Integer.valueOf(bannerId);
						Material m = materialService.findById(id);
						if (m != null) {
							bannerStr += m.getPath().replace("\\", "/") + "@";
						}
					}
				}
				t.setBannerPath(bannerStr);
			}
			if (t.getSelectPart() != null && t.getSelectPart().contains("isMarquee") && t.getMarquee() != null && t.getMarquee().length() != 0) {
				String[] marqueeIdStr = t.getMarquee().split(",");
				String marqueeStr = "";
				for (String marqueeId : marqueeIdStr) {
					if (marqueeId.trim().length() != 0) {
						Integer id = Integer.valueOf(marqueeId);
						Marquee m = marqueeService.findById(id);
						if (m != null) {
							marqueeStr += m.getTitle() + "<br/>";
						}
					}
				}
				t.setMarqueeStr(marqueeStr);
			}
			if (t.getSelectPart() != null && t.getSelectPart().contains("pictureCtrl") && t.getPicture() != null && t.getPicture().length() != 0) {
				String[] pictureIdStr = t.getPicture().split(",");
				String pictureStr = "";
				for (String pictureId : pictureIdStr) {
					if (pictureId.trim().length() != 0) {
						Integer id = Integer.valueOf(pictureId);
						Material m = materialService.findById(id);
						if (m != null) {
							pictureStr += id + ";" + m.getPath().replace("\\", "/") + "@";
						}
					}
				}
				t.setPictureStr(pictureStr);
			}
			if (t.getSelectPart() != null && t.getSelectPart().contains("videoCtrl") && t.getVideo() != null && t.getVideo().length() != 0) {
				String[] videoIdStr = t.getVideo().split(",");
				String videoStr = "";
				for (String videoId : videoIdStr) {
					if (videoId.trim().length() != 0) {
						Integer id = Integer.valueOf(videoId);
						Material m = materialService.findById(id);
						if (m != null) {
							videoStr += id + ";" + m.getPath().replace("\\", "/").replace(".flv", ".jpg") + "@";
						}
					}
				}
				t.setVideoStr(videoStr);
			}
			request.setAttribute("template", t);

			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TemplateAction findTemplate ERROR", e);
		}
		return INPUT;
	}

	// 查询模板
	public String searchTemplate() {
		try {
			this.searchName = (String) Utility.checkParmeter(this.searchName);
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			List<Template> templateList = this.templateService.findByPageBean(this.searchName, pageBean);
			request.setAttribute("templateList", templateList);
			request.setAttribute("pageBean", pageBean);
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TemplateAction findTemplate ERROR", e);
		}
		return INPUT;
	}

	// 批量删除
	public String deleteTemplateByIdList() {
		try {
			this.idList = (String) Utility.checkParmeter(this.idList);
			if (idList != null) {
				String[] idArray = this.idList.split(",");
				// 模板是否正被使用
				List<Terminal> terminalList = terminalService.findAll();
				if (terminalList != null && terminalList.size() > 0) {
					for (int i = 0; i < terminalList.size(); i++) {
						int templateId = terminalList.get(i).getTemplatePlay();
						for (int j = 0; j < idArray.length; j++) {
							int id = Integer.parseInt(idArray[j]);
							if (templateId == id) {
								jsonMap.put("result", "used");
								return SUCCESS;
							}
						}
					}
				}

				// 批量删除
				for (int i = 0; i < idArray.length; i++) {
					Integer id = new Integer(idArray[i]);
					this.templateService.deleteById(id);

				}
				jsonMap.put("result", "del");
			}
		} catch (Exception e) {
			jsonMap.put("result", "error");
			e.printStackTrace();
			Log4JFactory.instance().error("TemplateAction deleteByIdList ERROR", e);
		}
		return SUCCESS;
	}

	private FoodService foodService;

	private MaterialService materialService;

	private TemplateService templateService;

	private TerminalService terminalService;

	private int id;// ID

	private String name;// 模板名稱

	private String type;

	private String size;

	private int row;

	private int column;

	private String banner;

	private String marquee;

	private String cakeId;

	private String searchName;// 搜尋名稱

	private int startPage;

	private int maxPage;

	private int pageSize;

	private int adminId;

	private int titleImg;

	private String message;

	private AdminsService adminsService;

	private MarqueeService marqueeService;

	private String idList;

	private String selectPart;// 選擇廣告內容

	private String video;

	private String picture;

	private String picTime;

	private String effect;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
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

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public AdminsService getAdminsService() {
		return adminsService;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodService getFoodService() {
		return foodService;
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public String getCakeId() {
		return cakeId;
	}

	public void setCakeId(String cakeId) {
		this.cakeId = cakeId;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public int getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(int titleImg) {
		this.titleImg = titleImg;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public void setMarqueeService(MarqueeService marqueeService) {
		this.marqueeService = marqueeService;
	}

	public String getSelectPart() {
		return selectPart;
	}

	public void setSelectPart(String selectPart) {
		this.selectPart = selectPart;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicTime() {
		return picTime;
	}

	public void setPicTime(String picTime) {
		this.picTime = picTime;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	private Map<String, Object> jsonMap = new HashMap<String, Object>();

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String validateName() {
		List<Template> list = templateService.findByName(searchName);
		if (list != null && list.size() > 0) {
			jsonMap.put("exsit", true);
		} else {
			jsonMap.put("exsit", false);
		}
		return SUCCESS;
	}

	@Resource
	public void setTerminalService(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
