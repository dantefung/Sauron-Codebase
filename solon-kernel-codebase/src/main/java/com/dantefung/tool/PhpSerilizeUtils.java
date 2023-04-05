
package com.dantefung.tool;

import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.phprpc.util.AssocArray;
import org.phprpc.util.Cast;
import org.phprpc.util.PHPSerializer;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PhpSerilizeUtils {

	public static void main(String[] args) throws Exception {
//		String phpSerilizeStr = "a:4:{i:0;a:2:{s:11:\"province\";s:8:\"0016\";s:7:\"img\";s:49:\"20150117105023_kk-1.jpg\";}\n"
//				+ "\n" + "i:1;a:2:{s:11:\"province\";s:8:\"0017\";s:7:\"img\";s:49:\"20150117105025_kk-1.jpg\";}}";
//		System.out.println(getUnserializeList(phpSerilizeStr));

		String content ="a:10:{i:0;s:18:\"舞蹈（私教）\";i:1;s:5:\"Zumba\";i:2;s:12:\"完美塑造\";i:3;s:12:\"战斗有氧\";i:4;s:12:\"杠铃雕塑\";i:5;s:12:\"核心特训\";i:6;s:12:\"漫步舞林\";i:7;s:12:\"身体平衡\";i:8;s:12:\"高效冲击\";i:9;s:12:\"魅力热舞\";}";
		List<String> reusltList = unserializePHParray(content);
		System.out.println(reusltList);

		MixedArray mixedArray = Pherialize.unserialize(content).toArray();
		System.out.println(mixedArray);

	}

	/**
	 *  <dependency>
	 *      <groupId>org.sction</groupId>
	 *      <artifactId>phprpc</artifactId>
	 *      <version>3.0.2</version>
	 *  </dependency>
	 *  对php序列化的字符串，进行反序列化
	 */
	public  List<String> unserializePHParray(String content){
		List<String> list = new ArrayList<String>();
		PHPSerializer p = new PHPSerializer();
		if (StringUtils.isEmpty(content))
			return list;
		try {
			AssocArray array = (AssocArray) p.unserialize(content.getBytes());
			for (int i = 0; i < array.size(); i++) {
				String t = (String) Cast.cast(array.get(i), String.class);
				list.add(t);
			}
		}catch (Exception e){
			System.out.println("反序列化PHParray: " + content + " 失败！！！" );
		}
		return list;
	}

	public static List<Test> getUnserializeList(String content) throws Exception {
		List<Test> list = new ArrayList<Test>();
		PHPSerializer p = new PHPSerializer();
		if (StringUtils.isEmpty(content))
			return list;
		AssocArray array = (AssocArray) p.unserialize(content.getBytes());
		for (int i = 0; i < array.size(); i++) {
			Test t = (Test) Cast.cast(array.get(i), Test.class);
			list.add(t);
		}
		return list;
	}

	public static class Test {

		private String province;

		private String img;
	}
}
