/*
 * Copyright (C), 2015-2020
 * FileName: FTPClientBootstrap
 * Author:   DANTE FUNG
 * Date:     2021/11/2 16:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/11/2 16:18   V1.0.0
 */
package com.dantefung.net.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * @Title: FTPClientBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2021/11/02 16/18
 * @since JDK1.8
 */
public class FTPClientBootstrap {

	public static void main(String[] args) throws FileNotFoundException {
		FTPClientConfig ftpConfig = new FTPClientConfig();
		FTPPathConfig ftpPathConfig = new FTPPathConfig();
		FTPClient ftpClient = ftpConfig.ftpClient();
		FileInputStream fileInputStream = new FileInputStream(new File(
				FTPClientBootstrap.class.getResource("/").getPath() + File.separator + "static/vedio/mov_bbb.mp4"));
		String vId = UUID.randomUUID().toString().replace("-", "");
		String ftpPath = vId + "/";
		String filename = vId+"_320x176p_original.mp4";
		FtpUtil.uploadFile(ftpPathConfig, ftpPath, filename, fileInputStream, ftpClient);
	}

}
