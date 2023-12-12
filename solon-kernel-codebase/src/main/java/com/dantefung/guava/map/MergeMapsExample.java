package com.dantefung.guava.map;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import lombok.ToString;
import org.apache.commons.exec.util.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeMapsExample {
    public static void main(String[] args) {
        // 假设有三个原始的Map
        Map<Long, List<FeeItemDTO>> map1 = new HashMap<>();
        Map<Long, List<FeeItemDTO>> map2 = new HashMap<>();
        Map<Long, List<FeeItemDTO>> map3 = new HashMap<>();

        // 将数据添加到map1
        List<FeeItemDTO> list1 = new ArrayList<>();
        list1.add(new FeeItemDTO(1L, "Item1"));
        map1.put(1L, list1);

        // 将数据添加到map2
        List<FeeItemDTO> list2 = new ArrayList<>();
        list2.add(new FeeItemDTO(2L, "Item2"));
        map2.put(2L, list2);

        // 将数据添加到map3
        List<FeeItemDTO> list3 = new ArrayList<>();
        list3.add(new FeeItemDTO(1L, "Item3"));
        map3.put(1L, list3);

        // 合并map
        Map<Long, List<FeeItemDTO>> mergedMap = mergeMaps(map1, map2, map3);

        // 输出合并后的map
        for (Map.Entry<Long, List<FeeItemDTO>> entry : mergedMap.entrySet()) {
            Long key = entry.getKey();
            List<FeeItemDTO> value = entry.getValue();
            System.out.println("Key: " + key);
            for (FeeItemDTO item : value) {
                System.out.println(" - Item ID: " + item.getId() + ", Name: " + item.getName());
            }
        }

        // 不可行, 会覆盖
		System.out.println(MapUtils.merge(map1, map3));
    }

    public static Map<Long, List<FeeItemDTO>> mergeMaps(Map<Long, List<FeeItemDTO>>... maps) {
        Map<Long, List<FeeItemDTO>> mergedMap = new HashMap<>();
        for (Map<Long, List<FeeItemDTO>> map : maps) {
            for (Map.Entry<Long, List<FeeItemDTO>> entry : map.entrySet()) {
                Long key = entry.getKey();
                List<FeeItemDTO> value = entry.getValue();
                if (mergedMap.containsKey(key)) {
                    mergedMap.get(key).addAll(value);
                } else {
                    mergedMap.put(key, new ArrayList<>(value));
                }
            }
        }
        return mergedMap;
    }


    @ToString
	public static class FeeItemDTO {
		private Long id;
		private String name;

		public FeeItemDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}
}

