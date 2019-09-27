package com.dantefung.IO.part1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

/*
 *需求：
 *    键盘录入5个学生信息（姓名，语文成绩，数学成绩，英语成绩），按照总分从高到低存入文本文件。 
 * 
 * 分析：
 *     A：创建学生类。
 *     B：创建集合对象
 *         TreeSet<Student>
 *     C:键盘录入学生信息存储到集合。
 *     D：遍历集合，把数据写到文本文件。
 * 
 * 
 * 
 * 
 **/
public class StudentDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//创建集合对象
		TreeSet<Student> ts = new TreeSet<Student>(new Comparator<Student>()
	    {
		    @Override
		    public int compare(Student s1, Student s2)
		    {
		    	int num = s2.getSum() - s1.getSum();
		    	int num2 = num == 0 ? s1.getChinese() - s2.getChinese() : num;
		    	int num3 = num2 == 0 ? s1.getMath() - s2.getMath() : num2;
		    	int num4 = num3 == 0 ? s1.getEnglish() - s2.getEnglish() : num3;
		    	int num5 = num4 == 0 ? s1.getName().compareTo(s2.getName()) : num4;
		    	
				return num5;
		    }
		});
		
		//键盘录入学生信息存储到集合
		for(int x = 1; x <= 5; x++)
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入第" + x +"个学生的学习信息");
			System.out.println("姓名：");
			String name = sc.nextLine();
			System.out.println("语文成绩：");
			int chinese = sc.nextInt();
			System.out.println("数学成绩：");
			int math = sc.nextInt();
			System.out.println("英语成绩：");
			int english = sc.nextInt();
			
			//创建学生对象。
			Student s = new Student();
			s.setName(name);
			s.setChinese(chinese);
			s.setEnglish(english);
			s.setMath(math);
			
			//把学生信息添加到集合
			ts.add(s);				
		}
		
		//遍历集合，把数据写入到文本文件
		BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"));
		bw.write("学生信息如下：");
		bw.newLine();//换行。
		bw.flush();//踹一脚先。
		bw.write("姓名，语文成绩，数学成绩，英语成绩");
		bw.newLine();
		bw.flush();//再踹一脚。
		
		for(Student student : ts)
		{
			StringBuilder sb = new StringBuilder();//单线程的时候用StringBuilder效率高。
			//链式调用。
			sb.append(student.getName())
			  .append(",")
			  .append(student.getChinese())
			  .append(",")
			  .append(student.getMath())
			  .append(",")
			  .append(student.getEnglish());
			
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();//踹一脚先。
		}
		
		//释放资源
		bw.close();
		System.out.println("学生信息存储完毕！！！！");
		
	
	}

}
