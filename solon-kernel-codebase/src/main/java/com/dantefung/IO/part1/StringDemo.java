package com.dantefung.IO.part1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/*
 * 已知s.txt文件中有这样的一个字符串："hcexfgijkamdnoqrzstuvwybpl"
 * 编写程序读取数据内容，把数据排序后写入ss.txt中。
 * 
 * 分析：
 *     A：把s.txt这个文件给做出来。
 *     B：读取该文件的内容，存储到一个字符串中
 *     C：把字符串转换为字符数组
 *     D：对字符数组进行排序
 *     E：把排序后的字符数组转换为字符串
 *     F：把字符串再次写入ss.txt中
 * 
 *运行完成后，点击项目dantefung 右键-- refresh即可看到项目根目录下有ss.txt文件生成。
 * 
 * */
public class StringDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new FileReader("s.txt"));
        
        //一次读写一个字符串。
        String line = null;
        
        line = br.readLine();
        
        //把字符串转换为字符数组。
        char[] chs = line.toCharArray();

        //释放资源
        br.close();
        
        //对字符数组进行排序
        Arrays.sort(chs);
        
        //把排序后的字符数组转换为字符串
        String s = new String(chs);
        
        //把字符串再次写入ss.txt中。
        BufferedWriter bw = new BufferedWriter(new FileWriter("ss.txt"));
        
        bw.write(chs);
        bw.newLine();
        bw.flush();//踹一脚
        
        //释放资源
        bw.close();
 
	}

}
