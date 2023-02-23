package com.dantefung.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PageUtil {

    public static <T> List<T> startPage(List<T> list,
                                        Integer pageNum,
                                        Integer pageSize) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (list.size() == 0) {
            return new ArrayList<>();
        }

        // 记录总数
        Integer count = list.size();
        // 页数
        int pageCount = 0;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        // 开始索引
        int fromIndex = 0;
        // 结束索引
        int toIndex = 0;

        if (!Objects.equals(pageNum, pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        if (fromIndex > count || toIndex > count) {
            return null;
        }

        return list.subList(fromIndex, toIndex);
    }

}