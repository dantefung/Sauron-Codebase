/*
 * Copyright (C), 2015-2020
 * FileName: CompileScript
 * Author:   DANTE FUNG
 * Date:     2021/7/28 20:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/28 20:20   V1.0.0
 */
package com.dantefung.scriptengine;

import org.apache.commons.io.FileUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

/**
 * @Title: CompileScript
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/28 20/20
 * @since JDK1.8
 */
public class CompileScript {

	public static void main(String[] args) throws IOException, ScriptException {

		String js = FileUtils.readFileToString(new File("F:\\demoCode\\Sauron-Codebase\\solon-kernel-codebase\\src\\main\\java\\com\\dantefung\\scriptengine\\rool.js")); // 读取脚本。

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");

		// 向脚本传递参数
		engine.put("RESIDE_CITY", "440300");
		engine.put("HAS_DRIVER_LICENSE", "Y");
		engine.put("HAS_CAR", "Y");

		// 执行并打印
		System.out.println(engine.eval(js));
	}
}
