package com.dantefung.IO;



	import java.io.File;

	/*
	 * 需求：把E:\\liuyi\\FileBatchModify_Test下面的文件名称修改为
	 * 		00?_ccc.txt
	 * 
	 * 思路：
	 * 		A:封装目录
	 * 		B:获取该目录下所有的文件的File数组
	 * 		C:遍历该File数组，得到每一个File对象
	 * 		D:拼接一个新的名称，然后重命名即可。
	 */
public class FileBatchModify {
		public static void main(String[] args) {
			// 封装目录
			File srcFolder = new File("E:\\liuyi\\FileBatchModify_Test");

			// 获取该目录下所有的文件的File数组
			File[] fileArray = srcFolder.listFiles();

			// 遍历该File数组，得到每一个File对象
			for (File file : fileArray) {
				// System.out.println(file);
				// E:\liuyi\FileBatchModify_Test\aaa_bbb_001_ccc.txt
				// 改后：E:\liuyi\FileBatchModify_Test\001_ccc.txt
				String name = file.getName(); //aaa_bbb_001_ccc.txt

				int index = name.indexOf("_");
				String numberString = name.substring(index + 5, index + 8);
				System.out.println(numberString);

				// int startIndex = name.lastIndexOf('_');
				// int endIndex = name.lastIndexOf('.');
				// String nameString = name.substring(startIndex + 1, endIndex);
				// System.out.println(nameString);
				int endIndex = name.lastIndexOf('_');
				String nameString = name.substring(endIndex);
				//System.out.println(nameString);

				String newName = numberString.concat(nameString); // 001_ccc.txt
				//System.out.println(newName);

				File newFile = new File(srcFolder, newName); //E:\liuyi\FileBatchModify_Test\001_ccc.txt
				// 重命名即可
				file.renameTo(newFile);
			}
		}
	}


