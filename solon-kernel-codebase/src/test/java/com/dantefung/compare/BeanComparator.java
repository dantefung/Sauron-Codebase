package com.dantefung.compare;

import com.dantefung.compare.model.TestCompareDTO;
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
 * @author fenghaolin
 * @date 2024/09/04 13/42
 * @since JDK1.8
 */
public class BeanComparator {


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
     * 生成差异报告
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
     * 生成差异报告
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
     * 生成差异报告
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
     * 生成差异报告
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
                oldItem = createEmptyObject(id, emptyObjectSupplier.get(), idExtractor);
            }

            if (newItem == null) {
                // 新列表中没有这个id，创建一个空的新对象
                newItem = createEmptyObject(id, emptyObjectSupplier.get(), idExtractor);
            }

            List<DiffReport> itemReports = compareFields(oldItem, newItem, interestFields, excludeFields);
            reports.addAll(itemReports);
        }

        return reports;
    }

    private static <T, ID> T createEmptyObject(ID id, T emptyObject, Function<T, ID> idExtractor) {
        try {
            idExtractor.apply(emptyObject);
            return emptyObject;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an empty object", e);
        }
    }

    @SneakyThrows
    public static List<DiffReport> compareFields(Object oldItem, Object newItem) {
        // 没有指定字段, 则默认比较全部
        // 没有排除字段, 则比较全部
        return compareFields(oldItem, newItem, null, null);
    }

    @SneakyThrows
    public static List<DiffReport> compareFields(Object oldItem, Object newItem, List<String> interestFields, List<String> excludeFields) {
        List<DiffReport> reports = new ArrayList<>();

        Class<?> clazz = oldItem.getClass();

        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();

        Field idField = clazz.getDeclaredField("id");

        Object id = null;
        if (Objects.nonNull(idField)) {
            idField.setAccessible(true);
            id = idField.get(newItem);
            if (Objects.isNull(id)) {
                id = idField.get(newItem);
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

    public static List<DiffReport> compareFields(TestCompareDTO oldItem, TestCompareDTO newItem) {
        List<DiffReport> reports = new ArrayList<>();

        if (!Objects.equals(oldItem.getId(), newItem.getId())) {
            reports.add(new DiffReport(newItem.getId(), "id", oldItem.getId(), newItem.getId()));
        }

        if (!Objects.equals(oldItem.getCompanyId(), newItem.getCompanyId())) {
            reports.add(new DiffReport(newItem.getCompanyId(), "companyId", oldItem.getCompanyId(), newItem.getCompanyId()));
        }

        if (!Objects.equals(oldItem.getStoreId(), newItem.getStoreId())) {
            reports.add(new DiffReport(newItem.getStoreId(), "storeId", oldItem.getStoreId(), newItem.getStoreId()));
        }

        if (!Objects.equals(oldItem.getVehGroupId(), newItem.getVehGroupId())) {
            reports.add(new DiffReport(newItem.getId(), "vehGroupId", oldItem.getVehGroupId(), newItem.getVehGroupId()));
        }

        if (!Objects.equals(oldItem.getSaleFlag(), newItem.getSaleFlag())) {
            reports.add(new DiffReport(newItem.getId(), "saleFlag", oldItem.getSaleFlag(), newItem.getSaleFlag()));
        }

        if (!Objects.equals(oldItem.getNoDeposit(), newItem.getNoDeposit())) {
            reports.add(new DiffReport(newItem.getId(), "noDeposit", oldItem.getNoDeposit(), newItem.getNoDeposit()));
        }

        if (!Objects.equals(oldItem.getDeposit(), newItem.getDeposit())) {
            reports.add(new DiffReport(newItem.getId(), "deposit", oldItem.getDeposit(), newItem.getDeposit()));
        }

        if (!Objects.equals(oldItem.getViolationDeposit(), newItem.getViolationDeposit())) {
            reports.add(new DiffReport(newItem.getId(), "violationDeposit", oldItem.getViolationDeposit(), newItem.getViolationDeposit()));
        }

        if (!Objects.equals(oldItem.getMileageLimit(), newItem.getMileageLimit())) {
            reports.add(new DiffReport(newItem.getId(), "mileageLimit", oldItem.getMileageLimit(), newItem.getMileageLimit()));
        }

        if (!Objects.equals(oldItem.getLimitFlag(), newItem.getLimitFlag())) {
            reports.add(new DiffReport(newItem.getId(), "limitFlag", oldItem.getLimitFlag(), newItem.getLimitFlag()));
        }

        if (!Objects.equals(oldItem.getExtraMileagePrice(), newItem.getExtraMileagePrice())) {
            reports.add(new DiffReport(newItem.getId(), "extraMileagePrice", oldItem.getExtraMileagePrice(), newItem.getExtraMileagePrice()));
        }

        if (!Objects.equals(oldItem.getPricePerDay(), newItem.getPricePerDay())) {
            reports.add(new DiffReport(newItem.getId(), "pricePerDay", oldItem.getPricePerDay(), newItem.getPricePerDay()));
        }

        if (!Objects.equals(oldItem.getUsePerDayFlag(), newItem.getUsePerDayFlag())) {
            reports.add(new DiffReport(newItem.getId(), "usePerDayFlag", oldItem.getUsePerDayFlag(), newItem.getUsePerDayFlag()));
        }

        if (!Objects.equals(oldItem.getAddTime(), newItem.getAddTime())) {
            reports.add(new DiffReport(newItem.getId(), "addTime", oldItem.getAddTime(), newItem.getAddTime()));
        }

        if (!Objects.equals(oldItem.getListOrder(), newItem.getListOrder())) {
            reports.add(new DiffReport(newItem.getId(), "listOrder", oldItem.getListOrder(), newItem.getListOrder()));
        }

        if (!Objects.equals(oldItem.getPriceWeekend(), newItem.getPriceWeekend())) {
            reports.add(new DiffReport(newItem.getId(), "priceWeekend", oldItem.getPriceWeekend(), newItem.getPriceWeekend()));
        }

        if (!Objects.equals(oldItem.getPriceHour(), newItem.getPriceHour())) {
            reports.add(new DiffReport(newItem.getId(), "priceHour", oldItem.getPriceHour(), newItem.getPriceHour()));
        }

        if (!Objects.equals(oldItem.getZhimaFlag(), newItem.getZhimaFlag())) {
            reports.add(new DiffReport(newItem.getId(), "zhimaFlag", oldItem.getZhimaFlag(), newItem.getZhimaFlag()));
        }

        if (!Objects.equals(oldItem.getServiceType(), newItem.getServiceType())) {
            reports.add(new DiffReport(newItem.getId(), "serviceType", oldItem.getServiceType(), newItem.getServiceType()));
        }

        return reports;
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

        public DiffReport(Object id, String fieldName, Object oldValue, Object newValue) {
            this.clazz = clazz;
            this.id = id;
            this.fieldName = fieldName;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

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

}
