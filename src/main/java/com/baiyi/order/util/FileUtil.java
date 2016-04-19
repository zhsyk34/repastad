package com.baiyi.order.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	/**
	 * 复制文件
	 * 
	 * @param srcPath
	 * @param dstPath
	 * @throws IOException
	 */
	public static void copyDirectory(File srcPath, File dstPath) throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdirs();
			}
			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				Log4JFactory.instance().error(srcPath + " 不存在");
			} else {
				String directoryPath = dstPath.getAbsolutePath();
				directoryPath = directoryPath.substring(0, directoryPath.lastIndexOf(File.separator) + 1);
				File dir = new File(directoryPath);
				dir.mkdirs();
				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath);// Transfer
				BufferedInputStream bis = new BufferedInputStream(in);
				BufferedOutputStream bos = new BufferedOutputStream(out);
				byte[] buf = new byte[4096];
				int len;
				while ((len = bis.read(buf)) > 0) {
					bos.write(buf, 0, len);
				}
				bis.close();
				in.close();
				bos.close();
				out.close();
			}
		}
	}

	/**
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// System.out.println("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {

				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			// System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			// System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			// System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			// System.out.println("删除目录" + dir + "失败！");
			return false;
		}
	}

	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	public static void main(String[] args) throws Exception {
		FileUtil.copyFile(new File("D:/apache-tomcat-6.0.32/webapps/TopSetWebNetBef/program/hanshen_G_20130415143303435/2967899.jsp"), new File("D:/apache-tomcat-6.0.32/webapps/TopSetWebNetBef/program/hanshen_G_20130415143331216/2967899.jsp"));
	}

	/**
	 * 获取文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File file) throws Exception {// 取得文件大小
		long s = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			s = fis.available();
		} else {
			return 0;
		}
		return s;
	}
}