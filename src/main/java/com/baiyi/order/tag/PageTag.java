package com.baiyi.order.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.baiyi.order.util.BeanUtil;


public class PageTag extends SimpleTagSupport {


	private String formName;

	private String startPage;

	private String pageSize;

	private String maxPage;

	private String rowCount;
	
	private String language;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void doTag() throws JspException, IOException {
		int intPageNumber = 1;
		int intMaxPage = 1;
		int intRowCount = 0;
		Map<String, String> word = BeanUtil.multiLanguage.get(language).get("right");
		try {
			intPageNumber = Integer.parseInt(startPage);
		} catch (NumberFormatException e) {
			intPageNumber = 1;
		}
		try {
			intMaxPage = Integer.parseInt(maxPage);
		} catch (NumberFormatException e) {
			intMaxPage = 0;
		}
		try {
			intRowCount = Integer.parseInt(rowCount);
		} catch (NumberFormatException e) {
			intRowCount = 0;
		}

		String preDisabled = "";
		String nexDisabled = "";
		if (intPageNumber >= intMaxPage)
			nexDisabled = "style=\"color: gray;cursor:auto;\"";
		if (intPageNumber <= 1)
			preDisabled = "style=\"color: gray;cursor:auto;\"";

		StringBuffer selectButton = new StringBuffer();
		String selected = "";

		for (int i = 1; i <= intMaxPage; i++) {
			if (i == intPageNumber){
				selected = "selected";
			}else{
				selected = "";
			}
			selectButton.append("<option value=" + i + " " + selected + ">" + i + "</option>");
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("<div id=\"page\">");
		buffer.append("<input type=\"hidden\" name=\"startPage\" value=\"" + intPageNumber + "\"/>");
		buffer.append("<ul>");
		buffer.append("<li class=\"first\" " + preDisabled);
		if(preDisabled.equals("")){
			buffer.append(" onClick=\"document."+formName+".startPage.value=1;" + "document." + formName + ".submit();\"");
		}
		buffer.append(">" + word.get("text15") + "</li>");
		buffer.append("<li class=\"prev\" " + preDisabled);
		if(preDisabled.equals("")){
			buffer.append(" onClick=\"document."+formName+".startPage.value = eval(document."+formName+".startPage.value) - 1;" + "document." + formName + ".submit();\"");
		}
		buffer.append(">" + word.get("text16") + "</li>");
		buffer.append("<li>|</li>");
		buffer.append("<li class=\"next\" " + nexDisabled );
		if(nexDisabled.equals("")){
			buffer.append(" onClick=\"document."+formName+".startPage.value = eval(document."+formName+".startPage.value) + 1;" + "document." + formName + ".submit();\"");
		}
		buffer.append(">" + word.get("text17") + "</li>");
		buffer.append("<li class=\"last\" " + nexDisabled );
		if(nexDisabled.equals("")){
			buffer.append(" onClick=\"document."+formName+".startPage.value = " +intMaxPage+ ";" + "document." + formName + ".submit();\"");
		}
		buffer.append(">" + word.get("text18") + "</li>");
		buffer.append("<li>|</li>");
		buffer.append("<li>" + word.get("text19") + " <input type=\"text\" name=\"pageSize\" value=\""+this.pageSize+"\" size=\"1\"/> " + word.get("text20") + " | " + word.get("text21") + " "+ intMaxPage + " " + word.get("text22") + "/ " + intRowCount + " " + word.get("text23") + "</li>");
		buffer.append("<li>|</li>");
		buffer.append("<li>" + word.get("text24") + "  <select id=\"pageSelect\" onChange=\"javascript:document."+formName+".startPage.value = eval(this.value);"
				+ "document." + formName + ".submit();\">" + selectButton.toString() + "</select> " + word.get("text22") + "</li>");
		buffer.append("</ul></div>");
		getJspContext().getOut().write(buffer.toString());
	}
}