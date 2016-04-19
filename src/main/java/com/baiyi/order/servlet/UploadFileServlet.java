package com.baiyi.order.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baiyi.order.util.BeanUtil;

@SuppressWarnings("serial")
public class UploadFileServlet extends HttpServlet {

	private String uploadPath = BeanUtil.path + File.separator + "tempimag" + File.separator; // 上传文件的目录

	private String tempPath = BeanUtil.path + File.separator + "temp" + File.separator; // 临时文件目录

	File tempPathFile;

	public UploadFileServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
			factory.setRepository(tempPathFile);// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB

			List items = upload.parseRequest(request);// 得到所有的文件
			System.out.println("items:" + items.size());
			Iterator i = items.iterator();
			//
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();

				if (fi.isFormField()) {// 普通的表单域
					System.out.println("isFormField");
				} else {// 处理上传文件
					String fileName = fi.getName();
					if (fileName != null) {
						File fullFile = new File(fi.getName());
						File toDeleteFile = new File(uploadPath + fullFile.getName().replace("_min", ""));
						if (toDeleteFile.exists() && toDeleteFile.isFile()) {
							toDeleteFile.delete();
						}
						// System.out.println("fi.getName():"+fi.getName());
						File savedFile = new File(uploadPath, fullFile.getName());
						System.out.println(savedFile.getAbsolutePath());
						fi.write(savedFile);
						boolean flag = copyfile(uploadPath + fullFile.getName(), uploadPath + fullFile.getName().replace("_min", ""));
						System.out.println("複製文件 " + fi.getName() + " 是否成功：" + flag);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.write("You are success!");
		out.flush();
		out.close();

	}

	public void init() throws ServletException {

		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
	}

	public static String getRealPath() {

		String realPath = UploadFileServlet.class.getClassLoader().getResource("").getFile();
		File file = new File(realPath);
		realPath = file.getAbsolutePath();
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
			realPath = realPath.replace("\\WEB-INF\\classes", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(realPath);
		return realPath;

	}

	public boolean copyfile(String file1, String file2) {
		File in = new File(file1);
		File out = new File(file2);
		if (!in.exists()) {
			System.out.println(in.getAbsolutePath() + "源文件路径错误！！！");
			return false;
		}
		if (!out.exists()) {
			try {
				out.createNewFile();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		FileInputStream fin = null;
		FileOutputStream fout = null;

		if (in.isFile()) {
			try {
				try {
					fin = new FileInputStream(in);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("in.name=" + in.getName());
				try {
					fout = new FileOutputStream(out);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				int c;
				byte[] b = new byte[1024 * 5];
				try {
					while ((c = fin.read(b)) != -1) {
						fout.write(b, 0, c);
					}
					fin.close();
					fout.flush();
					fout.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

}
