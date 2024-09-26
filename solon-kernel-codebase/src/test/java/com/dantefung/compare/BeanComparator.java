package com.dantefung.compare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 对象比较器, 生成差异报告
 * 使用方式见本类的main方法
 *
 * @author fenghaolin
 * @date 2024/09/04 13/42
 * @since JDK1.8
 */
public class BeanComparator {


    /**
     * 生成差异报告, 调用此方法要自行对齐数据并且处理好列表的排序, 慎用
     *
     * @param oldList 旧列表
     * @param newList 新建列表
     * @return {@link List }<{@link DiffReport }>
     */
    @SneakyThrows
    public static List<DiffReport> generateDiffReport(List<Object> oldList, List<Object> newList) {
        List<DiffReport> reports = new ArrayList<>();

        // 假设两个列表长度相同且元素一一对应
        for (int i = 0; i < oldList.size(); i++) {
            List<DiffReport> itemReports = compareFields(oldList.get(i), newList.get(i));
            reports.addAll(itemReports);
        }

        return reports;
    }

    /**
     * 生成差异报告, 本方法会自动对齐数据
     * 所以需要指定: id获取方法(标记这个待比较对象的唯一性)
     * 为了对齐数据你也必须指定: 空对象的构造逻辑
     *
     * @param oldList             旧列表
     * @param newList             新建列表
     * @param idExtractor         ID 提取器
     * @param emptyObjectSupplier 空对象提供器
     * @return {@link List }<{@link DiffReport }>
     */
    public static <T,ID> List<DiffReport> generateDiffReport(List<T> oldList,
            List<T> newList,
            Function<T, ID> idExtractor,
            Supplier<T> emptyObjectSupplier) {

        return generateDiffReport(oldList, newList, idExtractor, emptyObjectSupplier, null, null);
    }

    /**
     * 生成差异报告, 本方法会自动对齐数据
     * 所以需要指定: id获取方法(标记这个待比较对象的唯一性)
     * 为了对齐数据你也必须指定: 空对象的构造逻辑
     *
     * @param oldList             旧列表
     * @param newList             新建列表
     * @param idExtractor         ID 提取器
     * @param emptyObjectSupplier 空对象提供器
     * @param excludeFields      希望跳过的字段
     * @return {@link List }<{@link DiffReport }>
     */
    public static <T,ID> List<DiffReport> generateDiffReportWithExclusions(List<T> oldList,
            List<T> newList,
            Function<T, ID> idExtractor,
            Supplier<T> emptyObjectSupplier,
            List<String> excludeFields
    ) {

        return generateDiffReport(oldList, newList, idExtractor, emptyObjectSupplier, null, excludeFields);
    }

    /**
     * 生成差异报告, 本方法会自动对齐数据
     * 所以需要指定: id获取方法(标记这个待比较对象的唯一性)
     * 为了对齐数据你也必须指定: 空对象的构造逻辑
     *
     * @param oldList             旧列表
     * @param newList             新建列表
     * @param idExtractor         ID 提取器
     * @param emptyObjectSupplier 空对象提供器
     * @param interestFields      感兴趣的字段
     * @return {@link List }<{@link DiffReport }>
     */
    public static <T,ID> List<DiffReport> generateDiffReportWithInclusions(List<T> oldList,
            List<T> newList,
            Function<T, ID> idExtractor,
            Supplier<T> emptyObjectSupplier,
            List<String> interestFields
    ) {

        return generateDiffReport(oldList, newList, idExtractor, emptyObjectSupplier, interestFields, null);
    }

    /**
     * 生成差异报告, 本方法会自动对齐数据
     * 所以需要指定: id获取方法(标记这个待比较对象的唯一性)
     * 为了对齐数据你也必须指定: 空对象的构造逻辑
     *
     * @param oldList             旧列表
     * @param newList             新建列表
     * @param idExtractor         ID 提取器
     * @param emptyObjectSupplier 空对象提供器
     * @param interestFields      感兴趣的字段
     * @param excludeFields      希望跳过的字段
     * @return {@link List }<{@link DiffReport }>
     */
    public static <T,ID> List<DiffReport> generateDiffReport(List<T> oldList,
            List<T> newList,
            Function<T, ID> idExtractor,
            Supplier<T> emptyObjectSupplier,
            List<String> interestFields,
            List<String> excludeFields
    ) {
        List<DiffReport> reports = new ArrayList<>();

        // 将旧列表和新列表按id存入Map
        Map<ID, T> oldMap = new HashMap<>();
        Map<ID, T> newMap = new HashMap<>();

        for (T item : oldList) {
            ID id = idExtractor.apply(item);
            oldMap.put(id, item);
        }

        for (T item : newList) {
            ID id = idExtractor.apply(item);
            newMap.put(id, item);
        }

        // 合并两个Map的key集合
        Set<ID> allIds = new HashSet<>(oldMap.keySet());
        allIds.addAll(newMap.keySet());

        // 遍历所有id，生成差异报告
        for (ID id : allIds) {
            T oldItem = oldMap.get(id);
            T newItem = newMap.get(id);

            if (oldItem == null) {
                // 旧列表中没有这个id，创建一个空的旧对象
                oldItem = createEmptyObject(emptyObjectSupplier.get());
            }

            if (newItem == null) {
                // 新列表中没有这个id，创建一个空的新对象
                newItem = createEmptyObject(emptyObjectSupplier.get());
            }

            List<DiffReport> itemReports = compareFields(id, oldItem, newItem, interestFields, excludeFields);
            reports.addAll(itemReports);
        }

        return reports;
    }

    private static <T> T createEmptyObject(T emptyObject) {
        try {
            return emptyObject;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an empty object", e);
        }
    }

    @SneakyThrows
    public static List<DiffReport> compareFields(Object oldItem, Object newItem) {
        // 没有指定字段, 则默认比较全部
        // 没有排除字段, 则比较全部
        return compareFields(null, oldItem, newItem, null, null);
    }

    @SneakyThrows
    public static <T, ID> List<DiffReport> compareFields(ID uniqueId, T oldItem, T newItem, List<String> interestFields, List<String> excludeFields) {
        List<DiffReport> reports = new ArrayList<>();

        // 最外层已经做了数据对齐
        Class<?> clazz = oldItem.getClass();

        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();

        Object id = null;
        if (Objects.nonNull(uniqueId)) {

            id = uniqueId;

        } else {

            Field idField = clazz.getDeclaredField("id");

            if (Objects.nonNull(idField)) {
                idField.setAccessible(true);
                id = idField.get(newItem);
                if (Objects.isNull(id)) {
                    id = idField.get(oldItem);
                }
            }
        }

        if (Objects.isNull(id)) {
            id = UUID.randomUUID().toString();
        }

        for (Field field : fields) {

            // 如果在用户指定排除的字段列表内, 则跳过
            if (CollectionUtils.isNotEmpty(excludeFields) && excludeFields.contains(field.getName())) {
                continue;
            }

            // 如果有指定比较字段列表, 则只比较指定的字段
            if (CollectionUtils.isNotEmpty(interestFields) && !interestFields.contains(field.getName())) {
                continue;
            }

            // 设置访问权限
            field.setAccessible(true);

            // 获取旧值和新值
            Object oldValue = field.get(oldItem);
            Object newValue = field.get(newItem);

            // 比较字段值
            if (isNumericType(field.getType())) {
                if (!compareNumericValues(oldValue, newValue)) {
                    DiffReport report = createDiffReport(clazz, field, oldValue, newValue, id);
                    reports.add(report);
                }
            } else {
                if (!Objects.equals(oldValue, newValue)) {
                    DiffReport report = createDiffReport(clazz, field, oldValue, newValue, id);
                    reports.add(report);
                }
            }
        }

        return reports;
    }

    private static boolean isNumericType(Class<?> type) {
        return type == int.class || type == Integer.class ||
                type == long.class || type == Long.class ||
                type == double.class || type == Double.class ||
                type == float.class || type == Float.class ||
                type == BigDecimal.class;
    }

    private static boolean compareNumericValues(Object oldValue, Object newValue) {
        if (oldValue instanceof BigDecimal && newValue instanceof BigDecimal) {
            return ((BigDecimal) oldValue).compareTo((BigDecimal) newValue) == 0;
        } else if (oldValue instanceof Number && newValue instanceof Number) {
            return ((Number) oldValue).doubleValue() == ((Number) newValue).doubleValue();
        }
        return false;
    }

    private static DiffReport createDiffReport(Class<?> clazz, Field field, Object oldValue, Object newValue, Object uniqueId) throws IllegalAccessException {
        DiffReport report = new DiffReport();
        report.setClazz(clazz);
        report.setId(uniqueId);
        report.setFieldName(field.getName());
        report.setOldValue(oldValue);
        report.setNewValue(newValue);
        return report;
    }

    @Data
    @ToString(doNotUseGetters = true)
    public static class DiffReport implements Serializable {

        private static final long serialVersionUID = -6097599861579972420L;

        private Object id;
        private Class<?> clazz;
        private String fieldName;
        private Object oldValue;
        private Object newValue;

        public DiffReport() {}

        public DiffReport(Class<?> clazz, Object id, String fieldName, Object oldValue, Object newValue) {
            this.clazz = clazz;
            this.id = id;
            this.fieldName = fieldName;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public String toDefaultReport() {
            StringBuffer sb = new StringBuffer();
            // append DiffReport所有字段
            sb.append("--------------" + fieldName + "-------------------").append("\r\n");
            sb.append("id: ").append(id).append("\r\n");
            sb.append("class: ").append(clazz).append("\r\n");
            sb.append("fieldName: ").append(fieldName).append("\r\n");
            sb.append("oldValue: ").append(oldValue).append("\r\n");
            sb.append("newValue: ").append(newValue).append("\r\n");
            sb.append("--------------------------------------------");
            return sb.toString();
        }

        public String toHtmlReport() {
            return "<div>" +
                    "<h3>Field Name: " + fieldName + "</h3>" +
                    "<p><strong>Old Value:</strong> " + oldValue + "</p>" +
                    "<p><strong>New Value:</strong> " + newValue + "</p>" +
                    "</div>";
        }

    }


    /* ---------------- 以下是测试代码 -------------- */

    public static void main(String[] args) {
        // 示例数据
        List<TestCompareDTO> oldList = new ArrayList<>();
        List<TestCompareDTO> newList = new ArrayList<>();

        // 添加示例数据
        TestCompareDTO oldItem1 = new TestCompareDTO();
        oldItem1.setId(1L);
        oldItem1.setCompanyId(100L);
        oldItem1.setStoreId(200L);
        oldItem1.setVehGroupId(4602087414779597824L);
        oldItem1.setSaleFlag(978067337);
        oldItem1.setNoDeposit(700089330);
        oldItem1.setDeposit(new BigDecimal("5.00"));
        oldItem1.setViolationDeposit(new BigDecimal("2.288473819542137E307"));
        oldItem1.setMileageLimit(2085573089);
        oldItem1.setLimitFlag(827696218);
        oldItem1.setExtraMileagePrice(new BigDecimal("7.144020761142295E307"));
        oldItem1.setPricePerDay(new BigDecimal("1.4649954457963203E308"));
        oldItem1.setUsePerDayFlag(268360484);
        oldItem1.setAddTime(6281948439481722880L);
        oldItem1.setListOrder(1518865989);
        oldItem1.setPriceWeekend(new BigDecimal("1.1888134908285962E308"));
        oldItem1.setPriceHour(new BigDecimal("9.264290058156109E307"));
        oldItem1.setZhimaFlag(802857683);
        oldItem1.setServiceType(1854805368);
        oldItem1.setId(1L);
        oldItem1.setCompanyId(100L);
        oldItem1.setStoreId(200L);

        TestCompareDTO newItem1 = new TestCompareDTO();
        newItem1.setId(1L);
        newItem1.setCompanyId(100L);
        newItem1.setStoreId(201L);
        newItem1.setVehGroupId(6613009895233600512L);
        newItem1.setSaleFlag(2077820662);
        newItem1.setNoDeposit(334003750);
        newItem1.setDeposit(new BigDecimal("5"));
        newItem1.setViolationDeposit(new BigDecimal("9.12638067100769E307"));
        newItem1.setMileageLimit(1542532689);
        newItem1.setLimitFlag(1534657856);
        newItem1.setExtraMileagePrice(new BigDecimal("1.5329338270357796E308"));
        newItem1.setPricePerDay(new BigDecimal("1.2698017897342898E308"));
        newItem1.setUsePerDayFlag(1436506608);
        newItem1.setAddTime(221960670301622272L);
        newItem1.setListOrder(415525335);
        newItem1.setPriceWeekend(new BigDecimal("7.94844461516454E307"));
        newItem1.setPriceHour(new BigDecimal("8.294313614201589E307"));
        newItem1.setZhimaFlag(434158066);
        newItem1.setServiceType(1171235182);

        TestCompareDTO newItem2 = new TestCompareDTO();
        newItem2.setId(2L);
        newItem2.setCompanyId(8530475344098403328L);
        newItem2.setStoreId(858772865712692224L);
        newItem2.setVehGroupId(7184195221100768256L);
        newItem2.setSaleFlag(123575731);
        newItem2.setNoDeposit(648953201);
        newItem2.setDeposit(new BigDecimal("7.659640925918213E307"));
        newItem2.setViolationDeposit(new BigDecimal("1.5440849202688437E308"));
        newItem2.setMileageLimit(511510072);
        newItem2.setLimitFlag(1417075513);
        newItem2.setExtraMileagePrice(new BigDecimal("8.561636817931055E307"));
        newItem2.setPricePerDay(new BigDecimal("7.933921087694872E307"));
        newItem2.setUsePerDayFlag(953846761);
        newItem2.setAddTime(6623637075898774528L);
        newItem2.setListOrder(1488885444);
        newItem2.setPriceWeekend(new BigDecimal("6.879085491756362E307"));
        newItem2.setPriceHour(new BigDecimal("1.0471186086531766E308"));
        newItem2.setZhimaFlag(1817854533);
        newItem2.setServiceType(849012017);

        oldList.add(oldItem1);
        newList.add(newItem1);
        newList.add(newItem2);

        // 示例1 指定感兴趣的字段
        //        List<DiffReport> diffReports = generateDiffReport(oldList, newList);
        //        List<DiffReport> diffReports = BeanComparator.generateDiffReportWithInclusions(oldList,
        //                newList,
        //                TestCompareDTO::getId,
        //                () -> new TestCompareDTO(),
        //                Lists.newArrayList("id", "companyId", "vehGroupId", "storeId", "deposit", "priceHour")
        //                );

        // 示例2 指定排除掉字段
        //        List<DiffReport> diffReports = BeanComparator.generateDiffReportWithExclusions(oldList,
        //                newList,
        //                TestCompareDTO::getId,
        //                () -> new TestCompareDTO(),
        //                Lists.newArrayList("id", "companyId", "vehGroupId", "storeId", "deposit", "priceHour")
        //                );

        // 示例3 指定 感兴趣的字段 和 希望排除掉字段
        List<DiffReport> diffReports = BeanComparator.generateDiffReport(oldList,
                newList,
                /*TestCompareDTO::getId,*/
                x-> {
                    if (Objects.isNull(x.getId()) || Objects.isNull(x.getCompanyId()) || Objects.isNull(x.getVehGroupId())) {
                        return null;
                    }
                    return String.format("%s-%s-%s", x.getId(), x.getCompanyId(), x.getVehGroupId());
                },
                () -> new TestCompareDTO(),
                Lists.newArrayList("id", "companyId", "vehGroupId", "storeId", "deposit", "priceHour"),
                Lists.newArrayList("priceHour")
        );

        List<Object> idCached = new ArrayList<>();
        for (DiffReport report : diffReports) {

            if (!idCached.contains(report.getId())) {
                idCached.add(report.getId());
                System.out.println("########################");
                System.out.println("#  id:  " + report.getId());
                System.out.println("#  class:  " + report.getClazz());
                System.out.println("########################");
            }

            System.out.println(report.toDefaultReport());
        }
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestCompareDTO implements Serializable {

        private static final long serialVersionUID = 8842117325531500028L;
        private Long id;

        private Long companyId;

        private Long storeId;

        private Long vehGroupId;

        private Integer saleFlag;

        private Integer noDeposit;

        private BigDecimal deposit;

        private BigDecimal violationDeposit;

        private Integer mileageLimit;

        private Integer limitFlag;

        private BigDecimal extraMileagePrice;

        private BigDecimal pricePerDay;

        private Integer usePerDayFlag;

        private Long addTime;

        private Integer listOrder;

        private BigDecimal priceWeekend;

        private BigDecimal priceHour;

        private Integer zhimaFlag;

        private Integer serviceType;

    }

}
