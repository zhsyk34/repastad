package com.baiyi.order.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.SHADigest;
import com.baiyi.order.util.Utility;

public class AdminsAction extends BasicAction {

	private String username;

	private String password;

	private String verifycode;

	private String oldPassword;

	private String message;

	private int id;

	private int startPage;

	private AdminsService adminsService;

	private int pageSize;

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	public String deleteAdmins() {
		JSONObject resultObj = new JSONObject();
		try {
			if (this.id > 0) {
				int adminId = ((Admins) request.getSession().getAttribute("user")).getId();
				Admins admins = adminsService.findById(id);
				this.adminsService.del(id);
				resultObj.put("success", true);
			} else {
				resultObj.put("errorMsg", this.getText("delete.error"));
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("errorMsg", this.getText("delete.error"));
			resultObj.put("success", false);
			Log4JFactory.instance().error("adminsAction deleteAdmins ERROR", e);
		}
		try {
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
		} catch (Exception e) {
			Log4JFactory.instance().error("adminsAction deleteRoleById Stream ERROR", e);
		}
		return SUCCESS;
	}

	/**
	 * 根据ID查询用户
	 * 
	 * @return
	 */
	public String findAdminsById() {
		try {
			if (this.id > 0) {
				Admins admins = this.adminsService.findById(id);
				request.setAttribute("admins", admins);
				return SUCCESS;
			} else {
				request.setAttribute("message", this.getText("search.error"));
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("RolesAction findRolesById ERROR", e);
		}
		return INPUT;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getPassword() {
		return password;
	}

	public int getStartPage() {
		return startPage;
	}

	public String getUsername() {
		return username;
	}

	public String getVerifycode() {
		return verifycode;
	}

	// 登入首頁
	public String login() {
		try {
			this.username = (String) Utility.checkParmeter(this.username);
			this.password = (String) Utility.checkParmeter(this.password);
			this.verifycode = (String) Utility.checkParmeter(this.verifycode);
			Object sessionCode = session.get("code");
			if (!BeanUtil.isAvailableMachine) {
				request.setAttribute("message", this.getText("machinconfig.error"));
				return INPUT;
			}
			if (sessionCode == null) {
				return INPUT;
			}
			boolean isCheckSuccess = true;
			if ((this.verifycode == null || sessionCode == null) || (!this.verifycode.equals(sessionCode.toString()))) {
				request.setAttribute("message", this.getText("login.validateError"));

				isCheckSuccess = false;
			}
			if (this.username == null) {
				request.setAttribute("userNameError", this.getText("login.usernameRequired"));
				isCheckSuccess = false;
			}
			if (this.password == null) {
				request.setAttribute("passwordError", this.getText("login.passwordRequired"));
				isCheckSuccess = false;
			}
			if (isCheckSuccess) {
				this.password = SHADigest.encode(this.password);
				Admins admin = this.adminsService.findByNamePass(username, password);
				if (admin != null) {
					session.put("user", admin);
					// 獲取權限
					List<Integer> authorityIdList = adminsService.findAuthorityList(admin.getId());
					int[] authorityIds = new int[authorityIdList.size()];
					for (int i = 0; i < authorityIdList.size(); i++) {
						authorityIds[i] = authorityIdList.get(i);
					}
					session.put("authorityIds", authorityIds);
					return SUCCESS;
				} else {
					request.setAttribute("message", this.getText("login.loginError"));
					return INPUT;
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("AdminsAction login Error", e);
		}
		return INPUT;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	public String logout() {
		this.session.clear();
		return SUCCESS;
	}

	// 增加用戶前
	public String saveAdminsByPrepare() {
		admins = null;
		return SUCCESS;
	}

	/**
	 * 添加或修改用户
	 * 
	 * @return
	 */
	public String saveUser() {
		try {
			this.username = (String) Utility.checkParmeter(this.username);
			this.password = (String) Utility.checkParmeter(this.password);
			boolean isCheckSuccess = true;
			if (this.username == null) {
				request.setAttribute("userNameError", this.getText("addUser.usernameRequired"));
				isCheckSuccess = false;
			}
			// 新增時密碼不可為空，修改時密碼為空則不修改
			if (this.password == null && this.id == 0) {
				request.setAttribute("passWordError", this.getText("addUser.passwordRequired"));
				isCheckSuccess = false;
			}
			if (isCheckSuccess) {
				boolean isExistUsername = false;
				Admins admins = this.adminsService.findByName(this.username);
				if (admins != null) {
					isExistUsername = true;
				}
				if (!isExistUsername || (isExistUsername && this.id > 0)) {
					if (this.password != null) {
						this.password = SHADigest.encode(this.password);
					}
					// add authority
					// this.adminsService.saveAdmins(admins,null);
					this.message = URLEncoder.encode(this.getText("write.success"), "utf-8");
				} else {
					request.setAttribute("message", this.getText("addUser.addErrorByName"));
				}
				return SUCCESS;
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("write.error"));
			Log4JFactory.instance().error("AdminUserAction saveUser Error", e);
		}
		return INPUT;
	}

	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	public String searchAdmins() {
		try {
			Admins admins = (Admins) request.getSession().getAttribute("user");
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
			List<Admins> adminsList = this.adminsService.findByPageBean(pageBean);
			request.setAttribute("adminsList", adminsList);
			request.setAttribute("pageBean", pageBean);
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "utf-8");
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("AdminsAction searchAdmins ERROR", e);
		}
		return INPUT;
	}

	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String updatePassword() {
		try {
			this.oldPassword = (String) Utility.checkParmeter(this.oldPassword);
			this.password = (String) Utility.checkParmeter(this.password);
			Admins admin = (Admins) session.get("user");
			boolean isCheckSuccess = true;
			if (this.oldPassword == null) {
				request.setAttribute("oldpasswordError", this.getText("passwordUpdata.oldPassRequird"));
				isCheckSuccess = false;
			}
			if (this.password == null) {
				request.setAttribute("newpasswordError", this.getText("passwordUpdata.newPassRequird"));
				isCheckSuccess = false;
			}
			if (admin == null) {
				request.setAttribute("message", this.getText("login.required"));
				isCheckSuccess = false;
			}
			if (isCheckSuccess) {
				this.password = SHADigest.encode(this.password);
				this.oldPassword = SHADigest.encode(this.oldPassword);
				this.username = admin.getUsername();
				int result = this.adminsService.updatePassword(username, oldPassword, password);
				if (result > 0) {
					request.setAttribute("message", this.getText("passwordUpdata.success"));
				} else {
					request.setAttribute("message", this.getText("passwordUpdata.error"));
				}
				return SUCCESS;
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("passwordUpdata.error"));
			Log4JFactory.instance().error("AdminUserAction updatePassword Error", e);
		}
		return INPUT;
	}
}
