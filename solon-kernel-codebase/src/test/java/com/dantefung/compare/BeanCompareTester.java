package com.dantefung.compare;

import com.dantefung.compare.BeanComparator.DiffReport;
import com.dantefung.compare.model.TestCompareDTO;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.*;

public class BeanCompareTester {

    public static List<DiffReport> generateDiffReport(List<TestCompareDTO> oldList, List<TestCompareDTO> newList) {
        List<DiffReport> reports = new ArrayList<>();

        // 假设两个列表长度相同且元素一一对应
        /*for (int i = 0; i < oldList.size(); i++) {
            List<DiffReport> itemReports = BeanComparator.compareFields(oldList.get(i), newList.get(i));
            reports.addAll(itemReports);
        }*/

        // 将旧列表和新列表按id存入Map
        Map<Long, TestCompareDTO> oldMap = new HashMap<>();
        Map<Long, TestCompareDTO> newMap = new HashMap<>();

        for (TestCompareDTO item : oldList) {
            oldMap.put(item.getId(), item);
        }

        for (TestCompareDTO item : newList) {
            newMap.put(item.getId(), item);
        }

        // 合并两个Map的key集合
        Set<Long> allIds = new HashSet<>(oldMap.keySet());
        allIds.addAll(newMap.keySet());

        // 遍历所有id，生成差异报告
        for (Long id : allIds) {
            TestCompareDTO oldItem = oldMap.get(id);
            TestCompareDTO newItem = newMap.get(id);

            if (oldItem == null) {
                // 旧列表中没有这个id，创建一个空的旧对象
                oldItem = new TestCompareDTO();
                oldItem.setId(id);
            }

            if (newItem == null) {
                // 新列表中没有这个id，创建一个空的新对象
                newItem = new TestCompareDTO();
                newItem.setId(id);
            }

            List<DiffReport> itemReports = BeanComparator.compareFields(oldItem, newItem);
            reports.addAll(itemReports);
        }

        return reports;
    }

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

//        List<DiffReport> diffReports = generateDiffReport(oldList, newList);
//        List<DiffReport> diffReports = BeanComparator.generateDiffReportWithInclusions(oldList,
//                newList,
//                TestCompareDTO::getId,
//                () -> new TestCompareDTO(),
//                Lists.newArrayList("id", "companyId", "vehGroupId", "storeId", "deposit", "priceHour")
//                );
//        List<DiffReport> diffReports = BeanComparator.generateDiffReportWithExclusions(oldList,
//                newList,
//                TestCompareDTO::getId,
//                () -> new TestCompareDTO(),
//                Lists.newArrayList("id", "companyId", "vehGroupId", "storeId", "deposit", "priceHour")
//                );

        List<DiffReport> diffReports = BeanComparator.generateDiffReport(oldList,
                newList,
                TestCompareDTO::getId,
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
                System.out.println("########################");
            }

            System.out.println(report.toDefaultReport());
        }
    }
}
