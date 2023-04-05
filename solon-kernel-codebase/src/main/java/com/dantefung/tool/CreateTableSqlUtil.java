package com.dantefung.tool;
 
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 这是一个自动生成 SQL 语句的类，只需要修改变量 CLS 的参数为对应类（如User.class）<br>
 * 然后执行main方法，就可以得到该类的建表SQL语句，以及对应的插入模拟SQL。
 * 
 * @author DANTE FUNG
 * @since 2022年8月7日19:24:48
 */
public class CreateTableSqlUtil {
 
    private static final Class<?> CLS = null;// 填写Bean的class
    private static final String LINE = "\r\n";
    private static Map<String, String> map = new LinkedHashMap<String, String>();
 
    public static void main(String[] args) throws Exception {
        // 获取建表 SQL
        createSQL();
        // 获取（参数条）测试数据 SQL
        createInsertData(2);
    }
 
    /**
     * 获取建表SQL（执行后控制台会自动输出建表语句SQL）
     * 
     * @throws Exception
     */
    private static void createSQL() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `").append(getSqlAttrName(CLS.getSimpleName())).append("` (").append(LINE);
        sb.append("  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',").append(LINE);
        sb.append("  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',").append(LINE);
        sb.append("  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',").append(LINE);
		sb.append("  `extra` longtext DEFAULT NULL COMMENT '额外信息',").append(LINE);
		sb.append("  `deleted` smallint DEFAULT 0 COMMENT '是否已删除：0-未删除，1-已删除',").append(LINE);
        sb.append("  `version` smallint DEFAULT 0 COMMENT '版本号',").append(LINE);
        // 获取其他参数
        Field[] fields = CLS.getDeclaredFields();
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            String key = getSqlAttrName(field.getName());
            String comment = getComment(field, false);
            switch (type) {
            case "String":
                sb.append("  `").append(key).append("` varchar(255) DEFAULT NULL COMMENT ").append(comment).append(",")
                        .append(LINE);
                break;
            case "int":
            case "Integer":
                comment = getComment(field, true);
                if (isStatusAndType(field.getName())) {
                    sb.append("  `").append(key).append("` smallint(2) DEFAULT 0 COMMENT ").append(comment).append(",")
                            .append(LINE);
                } else {
                    sb.append("  `").append(key).append("` int(11) DEFAULT 0 COMMENT ").append(comment).append(",")
                            .append(LINE);
                }
                break;
            case "long":
            case "Long":
                comment = getComment(field, true);
                sb.append("  `").append(key).append("` bigint(20) DEFAULT 0 COMMENT ").append(comment).append(",")
                        .append(LINE);
                break;
            case "Date":
                sb.append("  `").append(key).append("` datetime DEFAULT NULL COMMENT ").append(comment).append(",")
                        .append(LINE);
                break;
			case "BigDecimal":
				sb.append("  `").append(key).append("` decimal(10,2) DEFAULT 0 COMMENT ").append(comment).append(",")
						.append(LINE);
				break;
            default:
                throw new Exception("未知属性类型：" + type);
            }
        }
        sb.append("  PRIMARY KEY (`id`)").append(LINE);
        sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT'").append(getApiModelName())
                .append("表';").append(LINE);
        System.out.println("-- 创建表SQL");
        System.out.println(sb.toString());
    }
 
    /**
     * 创建测试数据 SQL（控制台会自动打印SQL）
     * 
     * @param size 需要生成多少条测试数据
     */
    private static void createInsertData(int size) {
        if (size <= 0) {
            return;
        }
        System.out.println("-- 测试数据 " + size + " 条");
        long id = 1;
        String date = "'" + DateUtil.formatDateTime(new Date()) + "'";
        for (int i = 0; i < size; i++) {
            String attrs = "";
            String params = "";
            for (String attr : map.keySet()) {
                attrs += ", " + attr;
                params += ", " + map.get(attr);
            }
            String sql = String.format(
                    "INSERT INTO " + getSqlAttrName(CLS.getSimpleName())
                            + " (id, create_time, update_time %s) VALUES (%s, %s, %s %s);",
                    attrs, id, date, date, params);
            id++;
            System.out.println(sql);
        }
    }
 
    private static String getSqlAttrName(String attr) {
        char[] array = attr.toCharArray();
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (char c : array) {
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else {
                if (idx > 0) {
                    sb.append("_");
                }
                sb.append(String.valueOf(c).toLowerCase());
            }
            idx++;
        }
        return sb.toString();
    }
 
    private static String getApiModelName() {
        ApiModel model = CLS.getAnnotation(ApiModel.class);
        if (Objects.isNull(model)) {
            return "";
        }
        return StringUtils.defaultString(model.value());
    }
 
    private static String getComment(Field field, boolean isNumber) {
        String sqlFiledName = getSqlAttrName(field.getName());
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (Objects.isNull(property)) {
            if (isNumber) {
                map.put(sqlFiledName, "0");
            } else {
                map.put(sqlFiledName, "NULL");
            }
            return "''";
        }
        // 是否有默认值
        String expmale = StringUtils.defaultString(property.example());
        if (isNumber) {
            map.put(sqlFiledName, expmale);
        } else {
            map.put(sqlFiledName, "'" + expmale + "'");
        }
        return "'" + StringUtils.defaultString(property.value()) + "'";
    }
 
    private static boolean isStatusAndType(String s) {
        s = s.toLowerCase();
        return s.endsWith("status") || s.endsWith("type");
    }
 
}