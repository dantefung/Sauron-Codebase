package com.dantefung.tika;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class HelloParser {

	/**
	 * 限制接口返回图片附件mime类型
	 */
	public static String limitImageMimeType = "image/png,image/jpeg,image/jpg";

	public static String getMimeType(InputStream inputStream){
		AutoDetectParser parser = new AutoDetectParser();
		parser.setParsers(new HashMap<MediaType, Parser>());

		Metadata metadata = new Metadata();

		try {
			parser.parse(inputStream, new DefaultHandler(), metadata, new ParseContext());
			inputStream.close();
		} catch (TikaException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return metadata.get(HttpHeaders.CONTENT_TYPE);
	}


	public static void main(String[] args) {
		String classPath = ClassUtil.getClassPath();
		File folder = FileUtil.file(classPath + File.separator + "tika");
		for (File file : folder.listFiles()) {
			String mimeType = getMimeType(FileUtil.getInputStream(file));
			System.out.println(
					file.getName() + "==" + mimeType + "==" + StringUtils
							.contains(limitImageMimeType, mimeType));
		}

		System.out.println("=========HUTOOL =========");
		for (File file : folder.listFiles()) {
			String mimeType = FileUtil.getMimeType(file.getName());// 这种不准确!
			System.out.println(
					file.getName() + "==" + mimeType + "==" + StringUtils
							.contains(limitImageMimeType, mimeType));
		}

	}

}
