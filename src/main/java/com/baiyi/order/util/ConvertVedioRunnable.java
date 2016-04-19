package com.baiyi.order.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.baiyi.order.vo.ConvertInfo;

public class ConvertVedioRunnable implements Runnable {

	public static void main(String[] args) throws Exception {
		String path = "e:/vedio/test.VOB";

		List<String> commend = new java.util.ArrayList<String>();
		commend.add("C:/vedio/mencoder/mencoder.exe");
		commend.add(path);
		commend.add("-srate");
		commend.add("32000");
		commend.add("-vf-add");
		commend.add("scale=800:600");
		commend.add("-ofps");
		commend.add("24");
		commend.add("-oac");
		commend.add("mp3lame");
		commend.add("-lameopts");
		commend.add("cbr:br=32:mode=0");
		commend.add("-ovc");
		commend.add("xvid");
		commend.add("-xvidencopts");
		commend.add("bitrate=800");
		commend.add("-o");
		commend.add("c:/result.flv");
		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true);
		builder.command(commend);
		Process process = builder.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		while ((line = br.readLine()) != null) {
			// System.out.println(line);
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int convert_avi_compelte = 0;

	public int convert_flv_compelte = 0;

	public int convert_vob_compelte = 0;

	public String sourcePath;

	public String destPath;

	public int id;

	ConcurrentLinkedQueue<ConvertInfo> linkedQueue;

	public ConvertVedioRunnable(ConcurrentLinkedQueue<ConvertInfo> linkedQueue) {
		this.linkedQueue = linkedQueue;
	}

	public void run() {
		try {
			System.out.println("-------------vedio convert start-------------");
			int size = linkedQueue.size();
			if (size > 0) {
				ConvertInfo info = linkedQueue.poll();
				if (info != null) {
					this.sourcePath = info.getPath();
					this.id = info.getId();
					try {
						convertVedio();
					} catch (Exception e) {
						Log4JFactory.instance().error(sourcePath + " transform ERROR", e);
					}
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error(sourcePath + " ConvertThread run ERROR", e);
		}
	}

	public void convertVedio() {
		int fileType = this.getVedioExtType();
		switch (fileType) {
		// 转flv
		case 0:
			Thread flvThread = new Thread(new FlvConvert(this.sourcePath, this.destPath));
			flvThread.start();
			try {
				while (convert_flv_compelte < 1) {
					Thread.sleep(10000);
				}
				Thread ImgGenerateThread = new Thread(new ImgGenerate(this.destPath));
				ImgGenerateThread.start();
				ImgGenerateThread.join();
			} catch (InterruptedException e) {
				Log4JFactory.instance().error("convertVedio flv convert ERROR", e);
			}

			break;
		// 先转avi,再转flv
		case 1:
			Thread aviThread = new Thread(new AviConvert(this.sourcePath, this.destPath));
			aviThread.start();
			try {
				while (convert_avi_compelte < 1) {
					Thread.sleep(10000);
				}
				Thread flvThread2 = new Thread(new FlvConvert(this.destPath, this.destPath));
				flvThread2.start();
				while (convert_flv_compelte < 1) {
					Thread.sleep(10000);
				}
				Thread ImgGenerateThread = new Thread(new ImgGenerate(this.destPath));
				ImgGenerateThread.start();
				ImgGenerateThread.join();
			} catch (InterruptedException e) {
				Log4JFactory.instance().error("convertVedio avi,flv convert ERROR", e);
			}
			break;
		case 2:
			Thread vobThread = new Thread(new VobConvert(this.sourcePath, this.destPath));
			vobThread.start();
			try {
				while (convert_vob_compelte < 1) {
					Thread.sleep(10000);
					System.out.println("vob loop");
				}
				System.out.println("ImgGenerateThread start");
				Thread ImgGenerateThread = new Thread(new ImgGenerate(this.destPath));
				ImgGenerateThread.start();
				ImgGenerateThread.join();
			} catch (InterruptedException e) {
				Log4JFactory.instance().error("convertVedio vob2flv convert ERROR", e);
			}

			break;
		}

	}

	public int getVedioExtType() {
		String extType = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length()).toLowerCase();
		if (extType.equals("avi")) {
			return 0;
		} else if (extType.equals("mpg")) {
			return 0;
		} else if (extType.equals("wmv")) {
			return 0;
		} else if (extType.equals("3gp")) {
			return 0;
		} else if (extType.equals("mov")) {
			return 0;
		} else if (extType.equals("mp4")) {
			return 0;
		} else if (extType.equals("asf")) {
			return 0;
		} else if (extType.equals("asx")) {
			return 0;
		} else if (extType.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (extType.equals("wmv9")) {
			return 1;
		} else if (extType.equals("rm")) {
			return 1;
		} else if (extType.equals("rmvb")) {
			return 1;
		} else if (extType.equals("vob")) {
			return 1;
		}
		return 9;
	}

	class AviConvert implements Runnable {

		private String sourceFile;

		private String destFile;

		public AviConvert(String sourceFile, String destFile) {
			this.sourceFile = sourceFile;
			this.destFile = destFile;
		}

		public void run() {
			try {
				processAVI();
			} catch (IOException ioe) {
				Log4JFactory.instance().error("AviConvert ERROR", ioe);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void processAVI() throws IOException {
			// System.out.println("------------------------avi converted
			// start------------------------");
			String extType = sourceFile.substring(sourceFile.lastIndexOf(".") + 1, sourceFile.length());
			// 判断文件是否存在
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
			this.destFile = this.sourceFile.replace(extType, "avi");
			List<String> commend = new java.util.ArrayList<String>();
			if (BeanUtil.os.equals("windows")) {
				commend.add("C:/vedio/mencoder/mencoder.exe");
			} else {
				commend.add("mencoder");
			}
			commend.add(this.sourceFile);
			commend.add("-srate");
			commend.add("32000");
			commend.add("-vf-add");
			commend.add("scale=800:600");
			commend.add("-ofps");
			commend.add("24");
			commend.add("-oac");
			commend.add("mp3lame");
			commend.add("-lameopts");
			commend.add("cbr:br=32:mode=0");
			commend.add("-ovc");
			commend.add("xvid");
			commend.add("-xvidencopts");
			commend.add("bitrate=800");
			commend.add("-o");
			commend.add(this.destFile);
			ProcessBuilder builder = new ProcessBuilder();
			builder.redirectErrorStream(true);
			builder.command(commend);
			Process process = builder.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
			}
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 删除源文件
			File file = new File(this.sourceFile);
			if (file.exists() && file.isFile()) {
				try {
					Thread.sleep(2000);
					file.delete();
				} catch (InterruptedException e) {
					Log4JFactory.instance().error(e);
					e.printStackTrace();
				}
			}
			destPath = this.destFile;
			convert_avi_compelte = 1;
			// System.out
			// .println("------------------------avi converted
			// end------------------------");
		}
	}

	class FlvConvert implements Runnable {

		private String sourceFile;

		private String destFile;

		public FlvConvert(String sourceFile, String destFile) {
			this.sourceFile = sourceFile;
			this.destFile = destFile;
		}

		public void run() {
			try {
				processFLV();
			} catch (IOException ioe) {
				Log4JFactory.instance().error("FlvConvert ", ioe);
			} catch (Exception e) {
				e.printStackTrace();
				Log4JFactory.instance().error(e);
			}
		}

		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		private void processFLV() throws IOException {
			String extType = sourceFile.substring(sourceFile.lastIndexOf(".") + 1, sourceFile.length());
			// 判断文件是否存在
			System.out.println("------------------------flv converted start------------------------");
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
			this.destFile = this.sourceFile.replace(extType, "flv");
			List<String> command = new java.util.ArrayList<String>();
			if (BeanUtil.os.equals("windows")) {
				command.add("C:/vedio/ffmpeg/ffmpeg.exe");
			} else {
				command.add("ffmpeg");
			}
			// command.add("-i");// 指定要转换的文件路径
			// command.add(this.sourceFile);
			// command.add("-ab");// 声音比特率
			// command.add("64");
			// command.add("-ac");// 声道数量2双声道
			// command.add("2");
			// command.add("-ar");// 设定声音采样率
			// command.add("22050");
			// command.add("-b");// 画面压缩比特率
			// command.add("1500");
			// command.add("-r");// 帧速率(非标准数值会导致音画不同步标准值为15或29.97)
			// command.add("24");
			// command.add("-y");// 覆盖输出文件
			// command.add("-sameq");// 使用与源视频相同的质量

			// -qscale 是视频输出质量，后边的值越小质量越高，但是输出文件就越“肥”-qscale 15
			// -i E:\vedio\VIDEO0005.avi -ab 128 -acodec libmp3lame -ac 1 -ar
			// 22050 -r 29.97 -b 512 -y E:\vedio\VIDEO0005.flv//低品质

			command.add("-i");// 指定要转换的文件路径
			command.add(this.sourceFile);
			command.add("-ab");
			command.add("128");
			command.add("-acodec");
			command.add("libmp3lame");
			command.add("-ac");
			command.add("2");
			command.add("-ar");
			command.add("22050");
			command.add("-r");
			command.add("29.97");
			command.add("-b");
			command.add("512");
			command.add("-y");// 覆盖输出文件
			command.add("-qscale");
			command.add("10");
			command.add(this.destFile);
			ProcessBuilder builder = new ProcessBuilder();
			builder.redirectErrorStream(true);
			builder.command(command);
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
				Log4JFactory.instance().error(e);
			}

			// 删除源文件
			File file = new File(this.sourceFile);
			try {
				Thread.sleep(1000 * 2);
				FileUtil.deleteFile(file.getAbsolutePath());
			} catch (InterruptedException e) {
				Log4JFactory.instance().error(e);
			}
			JDBCUtil jdbcUitl = JDBCUtil.getInstance();
			jdbcUitl.modifyMaterial(id);
			destPath = this.destFile;
			convert_flv_compelte = 1;
			// System.out
			// .println("------------------------flv converted
			// end------------------------");
		}
	}

	/**
	 * vob視頻格式通過memcoder直接轉換成flv
	 * 
	 * @author Administrator
	 * 
	 */
	class VobConvert implements Runnable {

		private String sourceFile;

		private String destFile;

		public VobConvert(String sourceFile, String destFile) {
			this.sourceFile = sourceFile;
			this.destFile = destFile;
		}

		public void run() {
			try {
				processVOB();
			} catch (IOException ioe) {
				Log4JFactory.instance().error("AviConvert ERROR", ioe);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void processVOB() throws IOException {
			// System.out.println("------------------------avi converted
			// start------------------------");
			String extType = sourceFile.substring(sourceFile.lastIndexOf(".") + 1, sourceFile.length());
			// 判断文件是否存在
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
			this.destFile = this.sourceFile.replace(extType, "flv");
			List<String> commend = new java.util.ArrayList<String>();
			if (BeanUtil.os.equals("windows")) {
				commend.add("C:/vedio/mencoder/mencoder.exe");
			} else {
				commend.add("mencoder");
			}
			commend.add(this.sourceFile);
			commend.add("-srate");
			commend.add("32000");
			commend.add("-vf-add");
			commend.add("scale=800:600");
			commend.add("-ofps");
			commend.add("24");
			commend.add("-oac");
			commend.add("mp3lame");
			commend.add("-lameopts");
			commend.add("cbr:br=32:mode=0");
			commend.add("-ovc");
			commend.add("xvid");
			commend.add("-xvidencopts");
			commend.add("bitrate=800");
			commend.add("-o");
			commend.add(this.destFile);
			ProcessBuilder builder = new ProcessBuilder();
			builder.redirectErrorStream(true);
			builder.command(commend);
			Process process = builder.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
			}
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 删除源文件
			File file = new File(this.sourceFile);
			if (file.exists() && file.isFile()) {
				try {
					Thread.sleep(2000);
					file.delete();
				} catch (InterruptedException e) {
					Log4JFactory.instance().error(e);
					e.printStackTrace();
				}
			}
			destPath = this.destFile;
			JDBCUtil jdbcUitl = JDBCUtil.getInstance();
			jdbcUitl.modifyMaterial(id);
			convert_vob_compelte = 1;

			System.out.println("convert_vob_compelte = 1;");
		}
	}

}
