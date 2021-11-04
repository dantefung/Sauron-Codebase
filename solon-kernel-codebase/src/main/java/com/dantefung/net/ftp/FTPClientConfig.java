package com.dantefung.net.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class FTPClientConfig {

    private static String ftpHost;
    private static Integer ftpPort;
    private static String ftpUserName;
    private static String ftpPassword;


	static {
		String path = FTPClientConfig.class.getResource("/").getPath();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File(path + File.separator + "ftp.properties"));
			Properties properties = new Properties();
			properties.load(fileReader);
			ftpHost = properties.getProperty("ftpHost");
			ftpUserName = properties.getProperty("ftpUserName");
			ftpPort = Integer.valueOf(properties.getProperty("ftpPort"));
			ftpPassword = properties.getProperty("ftpPassword");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    public FTPClient ftpClient(){
        FTPClient ftpClient =new FTPClient();
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
                throw new RuntimeException("未连接到FTP，用户名或密码错误。");
            }
        } catch (Exception e) {
            log.error("FTP的IP地址可能错误，请正确配置。",e);
			throw new RuntimeException("FTP的IP地址可能错误，请正确配置。");
        }
        log.info("FTP建连接成功"+ftpClient.hashCode());
        return ftpClient;
    }
}