package com.dantefung.tool;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Title: CodeSnippetGenerator
 * @Description:
 * @author fenghaolin
 * @since JDK1.8
 */
public class CodeSnippetGenerator {

	public static final String DAO_FUNC_GET_LIST_BY_PARAMS = "public List<{doName}> getListByParams({queryObjectName} query) {\r\nreturn {funcBody}\r\n}";

	public static void main(String[] args) {
		String doName = "PriorityCodeDO";
		Class<?> clazz = PriorityCodeQuery.class;
		String codeText = genMybatisPlusLambdaQueryStr(doName, clazz);
		System.out.println(genGetListByParams(doName, StrUtil.upperFirst(clazz.getSimpleName()) ,codeText));
	}

	@Data
	public class PriorityCodeQuery implements Serializable {

		private static final long serialVersionUID = -5103332287225856678L;

		/**
		 * 1:
		 * 0: 默认值
		 */
		private Integer hot;

		/**
		 * 保险归属 0国际 1国内
		 */
		private Integer hostLocation;

	}

	public static String genGetListByParams(String doName, String queryObjectName, String funcBody) {
		String rstText = DAO_FUNC_GET_LIST_BY_PARAMS.replace("{doName}", doName);
		rstText = rstText.replace("{queryObjectName}", queryObjectName);
		rstText = rstText.replace("{funcBody}", funcBody);
		return rstText;
	}

	/**
	 * this.lambdaQuery()
	 * .eq(Objects.nonNull(query.getHot()), PriorityCodeDO::getHot, query.getHot())
	 * .eq(Objects.nonNull(query.getHostLocation()), PriorityCodeDO::getHostLocation, query.getHostLocation())
	 * .list()
	 *
	 * @param doName
	 * @param queryClazz
	 * @return
	 */
	public static String genMybatisPlusLambdaQueryStr(String doName, Class<?> queryClazz) {
		Field[] fields = queryClazz.getDeclaredFields();


		List<String> codeStrList = Lists.newArrayList();
		String codeText = "this.lambdaQuery()\r\n";
		codeStrList.add(codeText);
		for (Field field : fields) {
			String fieldName = field.getName();
			// System.out.println(fieldName);
			if (!StringUtils.equalsIgnoreCase("serialVersionUID", fieldName)
					&& !StringUtils.equalsIgnoreCase("this$0", fieldName)
			) {


				String getterName = StringUtils.join("get", StrUtil.upperFirst(fieldName));

				String eqTpl = "eq(Objects.nonNull(query.{getterName}()), {doName}::{getterName}, query.{getterName}())\r\n";
				eqTpl = eqTpl.replace("{getterName}", getterName);
				eqTpl = eqTpl.replace("{doName}", doName);

				codeStrList.add(eqTpl);
			}
		}
		codeStrList.add("list();");
		return StringUtils.join(codeStrList, ".");
	}
}
