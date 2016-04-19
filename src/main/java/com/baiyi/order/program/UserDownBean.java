package com.baiyi.order.program;

/**
 * 终端用户下载节目信息存储
 * 
 * @author Administrator
 * 
 */
public class UserDownBean {

	public int templateId; // 节目ID

	public String username;// 终端用户名称

	private long totalSize;// 节目总大小

	private long downSize;// 已下载的节目大小

	private boolean isCancle;// 是否取消下載

	public UserDownBean() {

	}

	public UserDownBean(int templateId, String username, long totalSize, long downSize) {
		this.templateId = templateId;
		this.username = username;
		this.totalSize = totalSize;
		this.downSize = downSize;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getDownSize() {
		return downSize;
	}

	public void setDownSize(long downSize) {
		this.downSize = downSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + templateId;
		result = PRIME * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserDownBean other = (UserDownBean) obj;
		if (templateId != other.templateId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public boolean isCancle() {
		return isCancle;
	}

	public void setCancle(boolean isCancle) {
		this.isCancle = isCancle;
	}

}
