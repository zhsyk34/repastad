package com.baiyi.order.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.Authority;
import com.baiyi.order.pojo.Role;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.AuthorityService;
import com.baiyi.order.service.RoleService;
import com.baiyi.order.util.SHADigest;

//權限管理
public class RightManageAction extends BasicAction {

	// service
	private AdminsService adminsService;

	private RoleService roleService;

	private AuthorityService authorityService;

	// pojo of admin
	private Integer adminId;

	private String adminName;

	private String password;

	private String oldpassword;

	private Admins admins = new Admins();;

	// pojo of role
	private Integer roleId;

	private String roleName;

	private Role role = new Role();

	// pojo of authority
	private Integer authorityId;

	private String authorityName;

	private Authority authority = new Authority();

	// pojo list
	private List<Admins> adminsList;

	private List<Role> roleList;

	private List<Authority> authorityList;

	// oauth ids
	private int[] roleIds;

	private int[] authorityIds;

	// page query
	private int pageNo;

	private int pageSize;

	private int pageCount;

	private int count;

	// language
	private String language;

	// return data
	private Map jsonMap = new HashMap<String, Object>();

	@Resource
	public void setadminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public Admins getAdmins() {
		return admins;
	}

	public void setAdmins(Admins admins) {
		this.admins = admins;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public List<Admins> getAdminsList() {
		return adminsList;
	}

	public void setAdminsList(List<Admins> adminsList) {
		this.adminsList = adminsList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	public int[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(int[] roleIds) {
		this.roleIds = roleIds;
	}

	public int[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(int[] authorityIds) {
		this.authorityIds = authorityIds;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	/* ==================action method================================= */

	// 0.角色-權限初始化 暫禁用
	public void resetOauth() {
		// 清空原有角色權限數據
		roleService.initRole();
		// 初始化數據
		String[] module = { "用戶", "角色", "素材", "口味", "類型", "餐點", "跑馬燈", "模板", "訂單" };
		String[] operation = { "增加", "刪除", "修改", "查詢" };
		for (int i = 0; i < module.length; i++) {
			for (int j = 0; j < operation.length; j++) {
				authorityName = module[i] + operation[j];
				// authority.setName(authorityName);
				authorityService.save(authority);
				if (i == 0 && j == 0) {
					authorityId = authority.getId();
				}
			}
			roleName = module[i] + "模塊管理";
			role.setName(roleName);
			role.setPermanent(1);
			authorityIds = new int[operation.length];
			for (int k = 0; k < operation.length; k++) {
				authorityIds[k] = operation.length * i + k + authorityId;
			}
			roleService.save(role, authorityIds);
		}
	}

	// 1.自定義角色列表
	public String roleList() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		roleList = roleService.findList(0, roleName, (pageNo - 1) * pageSize, pageSize);

		// TODO 暫時做法 待改進
		if (roleService.findList(0) != null) {
			count = roleService.findList(0, roleName, 0, 0).size();
		}
		if (pageSize > 0) {
			pageCount = (count + pageSize - 1) / pageSize;
			jsonMap.put("pageCount", pageCount);
		}
		jsonMap.put("roleList", roleList);
		return SUCCESS;
	}

	// 2.添加角色頁面,系統内置角色-權限清單展示
	public String roleAdd() {
		language = this.getText("language");
		// 系統角色及其所對應的權限
		roleList = roleService.findList(1);// prarm:1--->系統角色
		Map<Integer, List<Authority>> authorityListMap = new HashMap();
		for (int i = 0; i < roleList.size(); i++) {
			roleId = roleList.get(i).getId();
			authorityList = roleService.findAuthorityList(roleId);
			authorityListMap.put(roleId, authorityList);
		}
		// 選擇清單
		jsonMap.put("roleList", roleList);
		jsonMap.put("authorityListMap", authorityListMap);
		return SUCCESS;
	}

	// 3.角色編輯頁面
	public String roleEdit() {
		language = this.getText("language");
		// 判斷是否編輯角色
		if (roleId != null && roleId > 0) {
			role = roleService.find(roleId);
			authorityList = roleService.findAuthorityList(roleId);
			authorityIds = new int[authorityList.size()];
			for (int i = 0; i < authorityList.size(); i++) {
				// 當前角色所擁有的權限
				authorityIds[i] = authorityList.get(i).getId();
			}
		}
		// 系統角色及其所對應的權限
		roleList = roleService.findList(1);// prarm:1--->系統角色
		Map<Integer, List<Authority>> authorityListMap = new HashMap();
		for (int i = 0; i < roleList.size(); i++) {
			roleId = roleList.get(i).getId();
			authorityList = roleService.findAuthorityList(roleId);
			authorityListMap.put(roleId, authorityList);
		}
		// 編輯角色用
		jsonMap.put("role", role);
		jsonMap.put("authorityIds", authorityIds);
		// 選擇清單
		jsonMap.put("roleList", roleList);
		jsonMap.put("authorityListMap", authorityListMap);
		return SUCCESS;
	}

	// 4.保存信息 增加 OR 修改
	public String roleSave() {
		role.setName(roleName);
		// 自定義角色標志
		role.setPermanent(0);
		// 保存
		if (roleId == null) {
			roleService.save(role, authorityIds);
		} else {
			role.setId(roleId);
			roleService.mod(role, authorityIds);
		}
		return SUCCESS;
	}

	// 5.自定義角色刪除
	public String roleDel() {
		roleService.del(roleId);
		return SUCCESS;
	}

	// 6.角色名是否已存在檢查
	public String roleNameCheck() {
		boolean exsit = false;
		List<Role> list = roleService.findByName(roleName);
		if (list != null && list.size() > 0) {
			exsit = true;
		}
		jsonMap.put("exsit", exsit);
		return SUCCESS;
	}

	// 7.用戶列表
	public String adminList() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		adminsList = adminsService.findList(adminName, (pageNo - 1) * pageSize, pageSize);
		if (adminsService.findList() != null) {
			count = adminsService.findList(adminName, 0, 0).size();
		}
		pageCount = (count + pageSize - 1) / pageSize;
		jsonMap.put("adminsList", adminsList);
		jsonMap.put("pageCount", pageCount);
		return SUCCESS;

	}

	// 8.用戶增加頁面
	public String adminAdd() {
		language = this.getText("language");
		roleList = roleService.findList(0);
		jsonMap.put("roleList", roleList);
		return SUCCESS;
	}

	// 9.用戶編輯頁面
	public String adminEdit() {
		language = this.getText("language");
		// 編輯的用戶信息
		if (adminId != null && adminId > 0) {
			admins = adminsService.findById(adminId);
			roleList = adminsService.findRoleList(adminId);
			roleIds = new int[roleList.size()];
			for (int i = 0; i < roleList.size(); i++) {
				// 編輯用戶時顯示用戶已擁有的角色
				roleIds[i] = roleList.get(i).getId();
			}
		}
		// 所有角色
		roleList = roleService.findList(0);
		jsonMap.put("admins", admins);
		jsonMap.put("roleIds", roleIds);
		jsonMap.put("roleList", roleList);
		return SUCCESS;
	}

	// 10.保存用戶
	public String adminSave() {
		// 加密
		if (password != null && password.trim().length() > 0) {
			password = SHADigest.encode(password);
		}
		if (adminId == null || adminId <= 0) {
			admins.setUsername(adminName);
			admins.setPassword(password);
			// TODO 暫時
			admins.setPageCount(10);
			admins.setAddtime(new Date());
			adminsService.save(admins, roleIds);
		} else {
			admins = adminsService.findById(adminId);
			admins.setPageCount(10);
			admins.setUsername(adminName);
			// 修改時密碼爲空時視爲不修改密碼
			if (password != null && password.trim().length() > 0) {
				admins.setPassword(password);
			}
			adminsService.mod(admins, roleIds);
		}
		return SUCCESS;
	}

	// 11.用戶刪除
	public String adminDel() {
		adminsService.del(adminId);
		return SUCCESS;
	}

	// 12.用戶名是否已存在、原密碼是否正確檢查
	public String adminCheck() {
		// 用戶名
		boolean exsit = false;
		if (adminName != null && adminName.trim().length() > 0) {
			Admins exsitUser = adminsService.findByName(adminName);
			// 已存在
			if (exsitUser != null) {
				if (adminId == null || adminId == 0) {
					// 新增用戶時直接認定爲已存在
					exsit = true;
				} else if (exsitUser.getId() != adminId) {
					//
					exsit = true;
				}
			}
		}
		jsonMap.put("exsit", exsit);
		// 原密碼 默認錯誤
		boolean check = false;
		if (adminId != null && adminId > 0) {
			Admins wordUser = adminsService.findById(adminId);
			if (wordUser != null) {
				password = wordUser.getPassword();
				if (oldpassword != null && oldpassword.length() > 0) {
					oldpassword = SHADigest.encode(oldpassword);
					// 比對通過 true
					check = oldpassword.equals(password);
				}

			}
		}
		jsonMap.put("check", check);
		return SUCCESS;
	}

	// 13.用戶修改 --- 密碼修改
	public String adminModWord2() {
		admins = (Admins) session.get("user");
		boolean check = true;
		// 當前用戶登錄判斷
		if (admins == null) {
			check = false;
		} else {
			adminName = admins.getUsername();
		}
		// 新密碼合法性判斷
		if (password == null || password.length() == 0) {
			check = false;
		} else {
			password = SHADigest.encode(password);
		}
		// 原密碼驗證
		if (oldpassword == null || oldpassword.length() == 0) {
			check = false;
		} else {
			oldpassword = SHADigest.encode(oldpassword);
			check = oldpassword.equals(admins.getPassword());

		}
		if (check) {
			int r = adminsService.updatePassword(adminName, oldpassword, password);
			System.out.println(r);
		}
		return SUCCESS;
	}

	// 13.用戶修改 --- 密碼修改
	public String adminModWord() {
		admins = (Admins) session.get("user");

		// 合法性判斷
		if (admins == null || StringUtils.isBlank(password) || StringUtils.isBlank(oldpassword)) {
			return SUCCESS;
		}
		adminName = admins.getUsername();
		password = SHADigest.encode(password);
		oldpassword = SHADigest.encode(oldpassword);

		// 密碼驗證
		if (oldpassword.equals(admins.getPassword())) {
			int r = adminsService.updatePassword(adminName, oldpassword, password);
			jsonMap.put("success", r > 0);
		}

		return SUCCESS;
	}

	// 14.獲取用戶擁有的權限清單
	public String findOauthList() {
		// 用戶擁有的權限
		authorityIds = (int[]) session.get("authorityIds");
		// 權限對照表
		Map<String, Integer> oauthList = (Map) servletContext.getAttribute("oauth");
		jsonMap.put("authorityIds", authorityIds);
		jsonMap.put("oauthList", oauthList);
		return SUCCESS;
	}

}
