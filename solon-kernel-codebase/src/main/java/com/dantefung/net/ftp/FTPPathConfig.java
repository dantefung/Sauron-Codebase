package com.dantefung.net.ftp;

import lombok.Data;

@Data
public class FTPPathConfig {
	//ftp保存文件的路径前缀
	private String ftpPrefixPath = "/vsdata/videostream/ftp/vod/";

	//本地文件存储路径前缀
	// 处理时从FTP下载源视频文件到时该路径存储然后处理（需要定时清理）
	private String localPrefixPath = "/data/videostream/tmpfiles/mv/";

	//是否直接访问ftp服务器
	private int readLocal = 1;

	//设置间隔时间 单位是秒
	private Long interval = 0L;
}
