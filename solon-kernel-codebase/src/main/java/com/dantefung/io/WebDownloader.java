package com.dantefung.io;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class WebDownloader {
	/**
	 * 下载文件 （歌曲，图片，视频）
	 * @param url 文件的http路径
	 * @param fileName 保存的文件路径
	 */
	public static void downloader(String url, String fileName) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * NIO读取文件返回字符串
	 *
	 * @param path
	 * @param cs 编码格式 Charset.forName("UTF8)
	 * @return
	 * @throws IOException
	 */
	public static String readText(Path path, Charset cs) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, cs)) {
			StringBuffer stringBuffer = new StringBuffer();
			for (; ; ) {
				String line = reader.readLine();
				if (line == null) {break;}
				stringBuffer.append(line);
			}
			return stringBuffer.toString();
		}
	}

	/**
	 * 处理文件上传
	 *
	 * @param file
	 * @param basePath 存放文件的目录的绝对路径 servletContext.getRealPath("/upload")
	 * @return
	 */
	/*public static String upload(MultipartFile file, String basePath) throws IOException {
		String filePath = "C:\\java\\mgr_resource\\friendList";
		// 设置文件存储路径
		byte[] bytes = file.getBytes();
		Path path = Paths.get(filePath);
		//保存在本地
		Files.write(path, bytes);
		return path.toString();
	}*/

	/**
	 * 下载网络文件返回byte数组
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String url) throws IOException {
		//网络下载
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

		InputStream is = conn.getInputStream();
		ByteArrayOutputStream baos = null;

		if (conn.getResponseCode() == 200) {
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];//1k
			int len = -1;
			//获取资源的总长度
			int totalLen = conn.getContentLength();
			int curLen = 0;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
				//3.计算下载 进度
				curLen += len;
				int p = curLen * 100 / totalLen;
				System.out.println("jindu" + p + "%");
			}
			is.close();
			conn.disconnect();
		}
		return baos.toByteArray();
	}

	/**
	 * 下载文件到本地
	 * @param urlString
	 * @param filename
	 * @throws Exception
	 */
	public static void download(String urlString, String filename) throws Exception {
		URL url = new URL(urlString);// 构造URL
		URLConnection con = url.openConnection();// 打开连接
		InputStream is = con.getInputStream();// 输入流
		String code = con.getHeaderField("Content-Encoding");
		if ((null != code) && code.equals("gzip")) {
			GZIPInputStream gis = new GZIPInputStream(is);
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = gis.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			gis.close();
			os.close();
			is.close();
		} else {
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		}
	}

	/**
	 * 根据url下载文件流
	 * @param urlStr
	 * @return
	 */
	public static InputStream getInputStreamFromUrl(String urlStr) {
		InputStream inputStream=null;
		try {
			//url解码
			URL url = new URL(java.net.URLDecoder.decode(urlStr, "UTF-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//得到输入流
			inputStream = conn.getInputStream();
		} catch (IOException e) {

		}
		return inputStream;
	}

	public static void downloadNet(String fileUrl) throws Exception {
		// 下载网络文件
		int bytesum = 0;
		int byteread = 0;

		URL url = new URL(fileUrl);

		try {
			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tmp" + System.currentTimeMillis() +".png";
			System.out.println(filePath);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(filePath);

			byte[] buffer = new byte[1204];
			int length;
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void downloadPic(String fileUrl) throws Exception {
		//定义一个URL对象，就是你想下载的图片的URL地址
		URL url = new URL(fileUrl);
		//打开连接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求方式为"GET"
		conn.setRequestMethod("GET");
		//超时响应时间为10秒
		conn.setConnectTimeout(10 * 1000);
		//通过输入流获取图片数据
		InputStream is = conn.getInputStream();
		//得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(is);
		//创建一个文件对象用来保存图片，默认保存当前工程根目录，起名叫Copy.jpg
		File imageFile = new File("D:/Copy" + System.currentTimeMillis() + ".jpg");
		//创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		//写入数据
		outStream.write(data);
		//关闭输出流，释放资源
		outStream.close();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//创建一个Buffer字符串
		byte[] buffer = new byte[6024];
		//每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len;
		//使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		//关闭输入流
		inStream.close();
		//把outStream里的数据写入内存
		return outStream.toByteArray();
	}


	public static void main(String[] args) throws Exception {
		String url = "https://pics2.baidu.com/feed/cdbf6c81800a19d8557ea2fa139e1281a71e46ba.jpeg?token=4213a510f926837c9e9e913337af81a4";
		String fileName = "D:/tmp_download";
		String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tmp" + System.currentTimeMillis();
		downloader(url, fileName);
		System.out.println(filePath);
		FileUtils.copyURLToFile(new URL(url), new File(filePath));
		System.out.println(FileUtil.getInputStream(Paths.get(filePath)));
		FileUtils.deleteQuietly(new File(fileName));
	}
}