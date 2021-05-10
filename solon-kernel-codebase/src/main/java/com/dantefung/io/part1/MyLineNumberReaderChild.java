package com.dantefung.io.part1;

import java.io.IOException;
import java.io.Reader;

public class MyLineNumberReaderChild extends MyLineNumberReader {
    private Reader r;
 
    private int lineNumber = 0;
	
	public MyLineNumberReaderChild(Reader r) {
		super(r);
		// TODO Auto-generated constructor stub
	}
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	
	@Override
	public String readLine() throws IOException 
	{
		lineNumber++;
		return super.readLine();
	}
}
