/**
 * 
 */
package utils;

import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;


/**
 * 
 * ClassName: SheetCopy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016-11-24 下午6:02:08 <br/>
 *
 * @author Dante Fung
 * @version 
 * @since JDK 1.6
 */
public class SheetCopy implements Runnable{
	
	private  String xlsfile;
	private  String saveAsFile;
	private  int  isContinue=0;
	
	/**
	 * @return String xlsfile
	 */
	public String getXlsfile() {
		return xlsfile;
	}

	/**
	 * @param String xlsfile
	 */
	public void setXlsfile(String xlsfile) {
		this.xlsfile = xlsfile;
	}
	
	public int getIsContinue() {
		return isContinue;
	}

	public void setIsContinue(int isContinue) {
		this.isContinue = isContinue;
	}

	/**
	 * @return String saveAsFile
	 */
	public String getSaveAsFile() {
		return saveAsFile;
	}
	/**
	 * @param String saveAsFile
	 */
	public void setSaveAsFile(String saveAsFile) {
		this.saveAsFile = saveAsFile;
	}
	
	public  void saveCopyAs(String xlsfile, String saveAsFile){
		try  
	      {   
	    	  Ole32.CoInitialize();
		      DispatchPtr app = new DispatchPtr("Excel.Application");//�½�һ������

		      app.put("Visible", false);   
		      DispatchPtr preses = (DispatchPtr)app.get("Workbooks");//ȡ�ĵ�ǰ�Ľ��
			  DispatchPtr pres = (DispatchPtr) preses.invoke("open", xlsfile);//��ppt//,"MsoTriState.msoFalse, MsoTriState.msoFalse, MsoTriState.msoFalse");
			  pres.invoke("SaveCopyAs",saveAsFile);
			  pres.invoke("Close", false);
			  app.invoke("Quit");
			 
	      }   
	      catch (Exception e)   
	      {   
	          e.printStackTrace();   
	      }   
	      finally  
	      {   
	    	  try {
				Ole32.CoUninitialize();
			} catch (COMException e) {
				e.printStackTrace();
			}finally{
				isContinue = 1;
			}
	      }   
	}
	@Override
	public void run() {
		saveCopyAs(xlsfile, saveAsFile);
	}

}
