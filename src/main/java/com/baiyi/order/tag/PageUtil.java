package com.baiyi.order.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PageUtil extends TagSupport {

	private float price;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		int temp = Math.round(price * 100);
		try {
			if (temp % 100 == 0) {
				temp = temp / 100;
				out.print(temp);
			} else if (temp % 10 == 0) {
				temp = temp / 10;
				out.print((float) temp / 10);
			} else {
				out.print((float) temp / 100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
