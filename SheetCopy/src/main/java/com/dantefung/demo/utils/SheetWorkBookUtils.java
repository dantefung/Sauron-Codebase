package com.dantefung.demo.utils;

/**
 * ClassName: SheetWorkBookUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016-11-24 下午6:04:04 <br/>
 *
 * @author Dante Fung
 * @since JDK 1.6
 */
public class SheetWorkBookUtils {
    /**
     * saveCopyAs:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @param xlsfile
     * @param saveAsFile
     * @author Dante Fung
     * @since JDK 1.6
     */
    @SuppressWarnings({"deprecation", "static-access"})
    public static void saveCopyAs(String xlsfile, String saveAsFile) {
        SheetCopy sc = new SheetCopy();
        sc.setXlsfile(xlsfile);
        sc.setSaveAsFile(saveAsFile);
        Thread t = new Thread(sc, "sc" + System.currentTimeMillis());
        Thread tmain = Thread.currentThread();
        try {
            t.start();
            while (sc.getIsContinue() != 1) {
                tmain.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            t.stop();
        }

    }

    public static void main(String[] args) {
        SheetWorkBookUtils.saveCopyAs("D:/tt.xls", "D:/tt2.xls");

    }

}
