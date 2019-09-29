package com.dantefung.dp.proxy;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {
	public static Object newProxyInstance(Class infce,InvocationHandler h) throws Exception
	{
		String methodStr = "";
		String rt = "\r\n";//在windows系统下，回车换行符号。\n newline \r return
		
		/* \" 转移字符 用来表示 双引号 " 避免机器误认为是字符串*/
		Method[] methods = infce.getMethods();
		for(Method m : methods)
		{
			methodStr += "@Override" + rt +
						 "public void " + m.getName()/*用户自定义接口中的方法的名称*/ + "(){" + rt +
						 "	  try {" + rt +
						 "    Method md = " + infce.getName() + ".class.getMethod(\"" + m.getName() +"\");" + rt +
						 "    h.invoke(this, md);" + rt + //h.invoke(this,md)的具体实现是用户自己编写的InvocationHadnler中的invoke()方法。
						 "    }catch(Exception e) {e.printStackTrace();}" + rt +
						 "}";
		}
		
		String src = 
				"package com.dantefung.dp.proxy;" +  rt +
				"import java.lang.reflect.Method;" + rt +
				"public class $Proxy1 implements " + infce.getName() + "{" + rt +
				"    public $Proxy1(InvocationHandler h) {" + rt +
				"        this.h = h;" + rt +
				"    }" + rt +
				
				
				"    com.dantefung.dp.proxy.InvocationHandler h;" + rt +
								
				methodStr + rt +
				"}";
		
		/**Step1:将文件写入指定的路径**/
		String fileName = //文件生成的路径。
				"f:/src/com/dantefung/dp/proxy/$Proxy1.java";//此处曾笔误：$Proxy1.java 写成 $Procy.java  导致后续的载入  $Proxy1.java 与 $Procy1.java名字对不上，载入不了。因此，需注意自己单词的拼写。
		File f = new File(fileName);//通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例。
		FileWriter fw = new FileWriter(f);//FileWriter 用于写入字符流。
		fw.write(src);//写入字符串。 
		fw.flush();//刷新该流的缓冲。 
		fw.close();//关闭此流，但要先刷新它。在关闭该流之后，再调用 write() 或 flush() 将导致抛出 IOException。关闭以前关闭的流无效。 
		
		/**Step2:compile**/
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);
		CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
		t.call();
		fileMgr.close();
		
		/**Step3:将文件中的源码载入内存并且根据内存中的字节码创建类的对象**/
		URL[] urls = new URL[] {new URL("file:/"/*这个必须写上*/ + "f:/src/")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("com.dantefung.dp.proxy.$Proxy1");
		System.out.println(c);
		
		Constructor ctr = c.getConstructor(InvocationHandler.class);//指定构造函数参数的类型。
		Object m = ctr.newInstance(h);//通过构造方法实例化一些必要的参数。
		
		return m;
	}

}
