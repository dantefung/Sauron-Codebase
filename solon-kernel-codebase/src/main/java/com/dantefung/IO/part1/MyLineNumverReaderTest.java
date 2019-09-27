package com.dantefung.IO.part1;

import java.io.FileReader;
import java.io.IOException;

public class MyLineNumverReaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
        MyLineNumberReaderChild mlnc = new MyLineNumberReaderChild(new FileReader("my.txt"));
        
        String line = null;
		while ((line = mlnc.readLine()) != null) {
			System.out.println(mlnc.getLineNumber() + ":" + line);
		}
		
		//释放资源
		mlnc.close();

	}

}
