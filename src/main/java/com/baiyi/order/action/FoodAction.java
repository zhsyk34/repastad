package com.baiyi.order.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.Taste;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.FoodTypeService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.TasteService;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;

@SuppressWarnings("serial")
public class FoodAction extends BasicAction {

	public String saveFood() throws Exception {
		try {
			String lan = (String) session.get("language");
			if ("zh-tw".equals(lan)) {
				price = (int) price;
			}
			this.name = (String) Utility.checkParmeter(this.name);
			this.introduce = (String) Utility.checkParmeter(this.introduce);
			if (tasteIds != null && tasteIds.length > 0) {
				taste = Arrays.toString(tasteIds);
				taste = taste.substring(1, taste.length() - 1);
			}
			this.foodService.saveFood(id, name, shortname, alias, type, price, materialId, introduce, admins.getId(), taste, necessary);
			this.message = URLEncoder.encode(this.getText("write.success"), "utf-8");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("write.error"));
			Log4JFactory.instance().error("FoodAction saveFood Error", e);
		}
		return INPUT;
	}

	@SuppressWarnings("unchecked")
	public String deleteFood() {
		JSONObject resultObj = new JSONObject();
		try {
			if (id > 0) {
				this.foodService.deleteById(id);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("FoodAction deleteFood ERROR", e);
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
			Log4JFactory.instance().error("FoodAction deleteFood stream ERROR", e);
		}
		return SUCCESS;
	}

	public String findFood() {
		if (id > 0) {
			food = foodService.findById(id);
		}
		if (food != null) {
			Material m = materialService.findById(food.getMaterialId());
			if (m != null) {
				food.setPath(m.getPath().replace("\\", "/"));
			}
		}
		foodTypeList = foodTypeService.findList();
		List<Taste> tasteList = tasteService.findList();
		// group
		tasteMap = new TreeMap<Integer, List<Taste>>();
		if (tasteList != null && !tasteList.isEmpty()) {
			for (Taste taste : tasteList) {
				int typeId = taste.getType();
				if (tasteMap.containsKey(typeId)) {
					List<Taste> exsit = tasteMap.get(typeId);
					exsit.add(taste);
				} else {
					List<Taste> insert = new ArrayList<Taste>();
					insert.add(taste);
					tasteMap.put(typeId, insert);
				}
			}
		}

		return SUCCESS;
	}

	public String searchFood() {
		try {
			this.searchName = (String) Utility.checkParmeter(this.searchName);
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			List<Food> foodList = this.foodService.findByPageBean(this.searchType, this.searchName, pageBean);
			for (Food f : foodList) {
				Material m = materialService.findById(f.getMaterialId());
				if (m != null) {
					f.setPath(m.getPath().replace("\\", "/"));
				}
			}
			List<FoodType> foodTypeList = foodTypeService.findList();

			Map<Integer, List<Taste>> foodTasteMap = new HashMap<Integer, List<Taste>>();

			for (Food food : foodList) {
				String tastes = food.getTaste();
				String tasteStr = "";
				List<Taste> tasteList = new ArrayList<Taste>();
				if (tastes != null && tastes.trim().length() > 0) {
					for (String id : food.getTaste().split(",")) {
						Taste taste = tasteService.find(Integer.parseInt(id.trim()));
						tasteList.add(taste);
						tasteStr += taste.getName() + "、";
					}
				}
				foodTasteMap.put(food.getId(), tasteList);
				tasteStr = tasteStr.replaceAll("\\、$", "");
				food.setTaste(tasteStr);
			}

			request.setAttribute("foodList", foodList);
			request.setAttribute("foodTypeList", foodTypeList);
			request.setAttribute("foodTasteMap", foodTasteMap);
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("forwardPage", "food");
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			if (select) {
				return "select";
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("FoodAction searchFood ERROR", e);
		}
		return INPUT;
	}

	public String deleteFoodByIdList() {
		JSONObject object = new JSONObject();
		try {
			this.idList = (String) Utility.checkParmeter(this.idList);
			if (idList != null) {
				String[] idArray = this.idList.split(",");
				adminId = 1;
				for (int i = 0; i < idArray.length; i++) {
					Integer id = new Integer(idArray[i]);
					this.foodService.deleteById(id);
				}
				object.put("success", true);
			}
		} catch (Exception e) {
			object.put("success", false);
			e.printStackTrace();
			Log4JFactory.instance().error("SourceMaterailAction deleteByIdList ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(object);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("SourceMaterailAction deleteMaterial stream ERROR", e);
		}
		return SUCCESS;
	}

	/**/
	private FoodService foodService;

	private MaterialService materialService;

	private FoodTypeService foodTypeService;

	private TasteService tasteService;

	private AdminsService adminsService;

	private int id;// 素材ID

	private String name;// 食物名稱

	private String shortname;

	private String alias;

	private float price;// 價格

	private int type;// 類型0為飲料，1為餅

	private int materialId;// 路徑

	private String introduce;// 介紹

	private String searchName;// 搜尋名稱

	private int searchType;// 搜尋類型

	private int startPage;

	private int maxPage;

	private int pageSize;

	private int adminId;

	private String message;

	private String idList;

	private boolean select = false;

	private String taste;

	private int[] tasteIds;

	private Food food;

	private List<Food> foodList;

	private List<FoodType> foodTypeList;

	private Map<Integer, List<Taste>> tasteMap;

	private String necessary;// 必须的调味

	public String getNecessary() {
		return necessary;
	}

	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}

	/* ------------------------getter and setter-------------------------------- */
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	@Resource
	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	@Resource
	public void setFoodTypeService(FoodTypeService foodTypeService) {
		this.foodTypeService = foodTypeService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	@Resource
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	@Resource
	public void setTasteService(TasteService tasteService) {
		this.tasteService = tasteService;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	public Map<Integer, List<Taste>> getTasteMap() {
		return tasteMap;
	}

	public void setTasteMap(Map<Integer, List<Taste>> tasteMap) {
		this.tasteMap = tasteMap;
	}

	public List<FoodType> getFoodTypeList() {
		return foodTypeList;
	}

	public void setFoodTypeList(List<FoodType> foodTypeList) {
		this.foodTypeList = foodTypeList;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int[] getTasteIds() {
		return tasteIds;
	}

	public void setTasteIds(int[] tasteIds) {
		this.tasteIds = tasteIds;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
