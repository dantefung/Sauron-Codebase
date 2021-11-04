package com.dantefung.net.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class FtpUtil {

	/**
	 * 上传文件
	 *
	 *  ftpHost ftp服务器地址
	 *  ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
	 *  ftpPassword 指定用户密码
	 *  ftpPort ftp服务员器端口号
	 *  ftpPath  ftp文件存放物理路径
	 *  fileName 文件路径
	 *  input 文件输入流，即从本地服务器读取文件的IO输入流
	 */
	public static Boolean uploadFile(FTPPathConfig ftpPathConfig, String ftpPath, String filename, InputStream input,
			FTPClient ftpClient) {
		try {
			log.info(ftpPath + " 开始上传 开始时间:{}", new Date());
			//进入指定路径
			FtpUtil.changeWorkingDirectory(ftpPathConfig.getFtpPrefixPath(), ftpClient);
			//判断当前文件夹是否存在
			if (!FtpUtil.existFile(ftpPath + "/", ftpClient)) {
				FtpUtil.makeDirectory(ftpPath, ftpClient);
			}
			FtpUtil.changeWorkingDirectory(ftpPath, ftpClient);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			//当前分辨率拼接后逇文件名
			String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
			ftpClient.storeFile(fileName, input);

			log.info(ftpPath + " 上传结束 结束时间:{}", new Date());
		} catch (Exception e) {
			log.error("上传ftp服务器失败,文件ID:{}", ftpPath, e);
			throw new RuntimeException("上传ftp服务器失败,文件ID:" + ftpPath);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
				log.error("文件ID:{},文件流关闭失败", ftpPath, e);
			} finally {
				try {
					ftpClient.logout();
				} catch (Exception e) {
					log.error("文件ID:{},ftp关闭连接失败", ftpPath, e);
				}
			}

		}
		return true;
	}

	/**
	 * 下载视频到本地
	 * 必须传入ftppath 和 DownloadFileName文件名
	 * ftpPathConfig.getFtpPrefixPath()+ftpPath  下载文件路径
	 *  ftpPathConfig.getDownloadFilePath()  ftp文件存放物理路径(如果不是特殊路径存储 可以不用传)
	 *  fileName 文件路径
	 */
	public static String downloadFile(FTPPathConfig ftpPathConfig, String ftpPath, String downloadFileName,
			FTPClient ftpClient) {
		String filepath = "";
		OutputStream outputStream = null;
		try {
			log.info("开始下载:{},文件ID:{}", new Date(), ftpPath);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			String downloadFolder = ftpPathConfig.getFtpPrefixPath() + ftpPath;
			log.info("要下载的文件的 ftp路径 {}", downloadFolder);
			ftpClient.changeWorkingDirectory(downloadFolder);
			//判断FTP服务器中当前文件是否存在
			String downloadFileUrl = downloadFolder + File.separator + downloadFileName;
			log.info("所需视频在ftp服务器的路径:{}", downloadFileUrl);
			if (!FtpUtil.existFile(downloadFileUrl, ftpClient)) {
				throw new RuntimeException("FTP服务器中当前文件不存在");
			}
			filepath = downloadFolder;
			File fp = new File(downloadFileUrl);
			// 创建目录
			//判断文件是否存在 判断时间是否大于15分钟
			if (ftpPathConfig.getReadLocal() == 1 && fp.exists() && !compareTime(filepath,
					ftpPathConfig.getInterval())) {
				return downloadFileUrl;
			}
			fp = new File(filepath);
			//判断文件夹是否存在
			if (!fp.exists()) {
				// 目录不存在的情况下，创建目录。
				fp.mkdirs();
			}
			File localFile = new File(downloadFileUrl);
			log.info("下载到本地的路径: {} 文件名: {}", filepath, downloadFileName);
			outputStream = new FileOutputStream(localFile);
			ftpClient.retrieveFile(downloadFileName, outputStream);
			log.info("下载成功 结束时间:{}", new Date());
		} catch (FileNotFoundException e) {
			log.error("没有找到文件", e);
		} catch (SocketException e) {
			log.error("连接FTP失败", e);
		} catch (IOException e) {
			log.error("文件读取错误", e);
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				log.error("文件ID:{},文件流关闭失败", ftpPath, e);
			} finally {
				try {
					ftpClient.logout();
				} catch (Exception e) {
					log.error("文件ID:{},ftp关闭连接失败", ftpPath, e);
				}
			}

		}
		return filepath + "/" + downloadFileName;
	}

	/**
	 * 获取FTPClient对象
	 *
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
			FTPClient ftpClient) {
		try {
			//设置缓存区大小
			ftpClient.setBufferSize(FTPconstant.cache);
			// 连接FTP服务器
			ftpClient.connect(ftpHost, ftpPort);
			// 登陆FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);

			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				log.error("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			log.error("FTP的IP地址可能错误，请正确配置。", e);
			throw new RuntimeException("FTP的IP地址可能错误，请正确配置。");
		}
		return ftpClient;
	}

	//下面方法 为生成多级路径准备
	//改变目录路径
	public static boolean changeWorkingDirectory(String directory, FTPClient ftpClient) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				log.info("进入文件夹 {} 成功！", directory);
			} else {
				log.error("进入文件夹 {} 失败！,请确认文件路径是否正确", directory);
				throw new RuntimeException("进入文件夹 " + directory + " 失败！,请确认文件路径是否正确");
			}
		} catch (IOException e) {
			log.error("进入文件夹 {} 时,发生异常！", e);
			throw new RuntimeException(String.format("进入文件夹 %s 时,发生异常！", e.getMessage()));
		}
		return flag;
	}


	//创建目录
	public static boolean makeDirectory(String dir, FTPClient ftpClient) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				log.info("创建文件夹 {} 成功！", dir);

			} else {
				log.info("创建文件夹 {} 失败！", dir);
			}
		} catch (Exception e) {
			log.error("创建文件夹 {} 时,发生错误！", dir, e);
			throw new RuntimeException(String.format("创建文件夹 %s 时,发生错误%s！",dir, e.getMessage()));
		}
		return flag;
	}

	//判断ftp服务器文件是否存在
	public static boolean existFile(String path, FTPClient ftpClient) {
		boolean flag = false;
		FTPFile[] ftpFileArr = new FTPFile[0];
		try {
			ftpFileArr = ftpClient.listFiles(path);
		} catch (IOException e) {
		}
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断文件时间是否大于某个值.
	 * @param filePath 文件路径
	 * @param interval 间隔时间
	 * @return true 超过间隔 , fasle 未超过间隔
	 */
	public static Boolean compareTime(String filePath, Long interval) {
		//获取文件时间
		FileTime fileTime = null;
		try {
			fileTime = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class).creationTime();
			log.info("文件时间(有时区问题在转换时会处理: " + fileTime);
			Date date = switchTime(fileTime.toString());

			Long overtopTime = new Date().getTime() - date.getTime();
			if (!(overtopTime / 1000 > interval)) {
				log.info("时间未超过设定间隔: " + date);
				return false;
			}
		} catch (IOException e) {
			log.error("Exception:{}", e);
		}

		log.info("时间超过设定间隔 重新从ftp拉取");
		return true;
	}

	//文件时间转javaDate
	public static Date switchTime(String day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String fileTime = day.substring(0, 10) + " " + day.substring(11, 19);
		Date date = null;
		try {
			date = format.parse(fileTime);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, 8);// 24小时制
		date = cal.getTime();
		log.info("转换后的Date时间:" + format.format(date));  //显示更新后的日期
		cal = null;
		return date;
	}
}
