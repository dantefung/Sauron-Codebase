package com.dantefung.io.part1;

import java.io.IOException;
import java.io.Reader;

public class MyLineNumberReader {
    private Reader r;//聚合对象。
    private int lineNumber =0;
    
    public MyLineNumberReader(Reader r)
    {
    	this.r = r;//此处采取   依赖注入  会更好。IOC(控制反转)
    }
    
    public int getLineNumber()
    {
    	return lineNumber;
    }
    
    public void setLineNumber(int lineNumber)
    {
    	this.lineNumber = lineNumber;
    }
    
    public String readLine() throws IOException
    {
    	lineNumber++;
    	
    	StringBuilder sb = new StringBuilder();
    	
    	int ch = 0;
    	while((ch=r.read()) != -1)
    	{
    		if( ch == '\r')
    		{
    			continue;
    		}
    		
    		if( ch == '\n')
    		{
    			return sb.toString();
    		}
    		else
    		{
    			sb.append((char)ch);
    		}
    	}
    	
    	if(sb.length() > 0)
    	{
    		sb.toString();
    	}
    	return null;
    }
    
    //释放资源
    public void close() throws IOException
    {
    	this.r.close();
    }
}
