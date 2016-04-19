package com.baiyi.order.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 视频截取图片
 * 
 * @author Administrator
 * 
 */
public class ImgGenerate implements Runnable {

	public static void main(String[] args) {
		String file = "D:/apache-tomcat-6.0.32/webapps/TopSetWebNet/vedios/201110282/yingxiang.flv";
		Thread t = new Thread(new ImgGenerate(file));
		t.start();
	}

	private String sourceFile;

	public ImgGenerate(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void run() {
		// System.out.println("sourceFile:"+sourceFile);
		// this.sourceFile = this.sourceFile.replace(File.separator, "/");
		try {
			System.out.println("--------------ImgGenerate- run-----------------");
			generateImg();
		} catch (IOException ioe) {
			Log4JFactory.instance().error("ImgGenerate ERROR", ioe);
		} catch (Exception e) {
			Log4JFactory.instance().error(e);
		}
	}

	private void generateImg() throws IOException {
		System.out.println("------------------------img generate start------------------------");
		try {
			File srcFile = new File(this.sourceFile);
			if (this.sourceFile == null || !srcFile.exists()) {
				Thread.interrupted();
			}
			long fileSize = FileUtil.getFileSizes(srcFile);
			if (fileSize == 0) {
				Thread.interrupted();
			}
		} catch (Exception e) {
			Thread.interrupted();
		}
		String imgFileName = this.sourceFile.substring(0, this.sourceFile.lastIndexOf(".")) + ".jpg";
		List<String> commend = new java.util.ArrayList<String>();
		if (BeanUtil.os.equals("windows")) {
			commend.add("C:/vedio/ffmpeg/ffmpeg");
		} else {
			commend.add("ffmpeg");
		}
		commend.add("-i");
		commend.add(this.sourceFile);
		commend.add("-y");
		commend.add("-ss");
		commend.add("00:00:8");
		commend.add("-t");
		commend.add("00:00:10");
		commend.add("-s");
		commend.add("300*200");
		commend.add("-f");
		commend.add("mjpeg");
		commend.add("-vframes");
		commend.add("10");
		commend.add(imgFileName);
		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true);
		builder.command(commend);
		Process process = builder.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("---------------img generate end--------------------");
	}

}