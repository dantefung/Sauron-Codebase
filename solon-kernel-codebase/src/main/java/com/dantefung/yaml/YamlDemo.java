/*
 * Copyright (C), 2015-2020
 * FileName: YamlDemo
 * Author:   DANTE FUNG
 * Date:     2021/7/29 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/29 14:14   V1.0.0
 */
package com.dantefung.yaml;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.google.common.collect.Lists;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: YamlDemo
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/29 14/14
 * @since JDK1.8
 */
public class YamlDemo {

	private static String OUT_PUT_PATH = null;

	static {
		try {
			OUT_PUT_PATH = YamlDemo.class.getResource("/yamls/").toURI().getPath()+"output.yml";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws Exception{
//		System.out.println(YamlDemo.class.getResource(""));
//		System.out.println(YamlDemo.class.getResource("/"));
//		System.out.println(YamlDemo.class.getResource("/yamls/contact.yml"));
		read();
//		write();
//		write2();
		write3();
	}


	private static void read() throws FileNotFoundException, YamlException, URISyntaxException {
		YamlReader reader = new YamlReader(new FileReader(new File(YamlDemo.class.getResource("/yamls/contact.yml").toURI())));
		Object object = reader.read();
		System.out.println(object);
		Map map = (Map)object;
		System.out.println(map.get("address"));
	}

	private static void write() throws IOException, URISyntaxException {
		Contact contact = new Contact();
		contact.name = "Nathan Sweet";
		contact.age = 28;
		YamlWriter writer = new YamlWriter(new FileWriter(OUT_PUT_PATH));
		writer.write(contact);
		writer.close();
	}

	private static void write2() throws IOException {
		Contact contact = new Contact();
		contact.name = "Bill";
		List<Phone> phones = Lists
				.newArrayList(new Phone("","206-555-1234"), new Phone("", "206-555-5678"), new Phone("", "206-555-7654"));
		contact.phoneNumbers = phones;
		YamlWriter writer = new YamlWriter(new FileWriter(OUT_PUT_PATH));
		writer.getConfig().setPropertyElementType(Contact.class, "phoneNumbers", Phone.class);
		writer.write(contact);
		writer.close();
	}

	private static void write3() throws IOException {
		WebSites webSites = new WebSites();
		Map<String,String> map = new HashMap();
		map.put("python", "python.org");
		map.put("ruby", "ruby.org");
		map.put("java", "java.org");
		map.put("yaml", "yaml.org");
		webSites.setWebsites(map);
		YamlWriter yamlWriter = new YamlWriter(new FileWriter(OUT_PUT_PATH));
		yamlWriter.write(webSites);
		yamlWriter.close();

	}
}
