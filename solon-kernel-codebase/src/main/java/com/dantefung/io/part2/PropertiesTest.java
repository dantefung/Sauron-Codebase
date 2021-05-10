package com.dantefung.io.part2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.Set;

/*
 *我有一个文本文件（user.txt） ，我知道数据是键值对形式的，但是不知道内容是什么。
 *请写一个程序判断是否有"lisi"这样的键存在，如果有键改变其值为"100"
 * 
 * 分析：
 *     A：把文件中的数据加载到集合中
 *     B：遍历集合，获取得到每一键
 *     C：判断键是否有为"lisi"的，如果有就修改其值为"100"
 *     D：把集合中的数据重新存储到文件中
 */
public class PropertiesTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//创建一个集合对象
		Properties prop = new Properties();
		
		Reader r = new FileReader("User.txt");
		//将User.txt中的内容加载到集合中。
		prop.load(r);
		
	    //获取所有键的集合
		Set<String> set = prop.stringPropertyNames();
		//遍历set集合并且判断是否有为"lisi"的键，如果有就修改其值为"100"
		for(String key : set)
		{
		    //之所以这样写判断条件是防止出现空指针异常，key有可能为空。
			if("lisi".equals(key))
			{
				prop.setProperty(key, "100");
				break;
			}
		}
		
		// 把集合中的数据重新存储到文件中
		Writer w = new FileWriter("User.txt");
		prop.store(w, null);
		w.close();
	}

}
