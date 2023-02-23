/*
 * Copyright (C), 2015-2020
 * FileName: CollectionOpTest
 * Author:   fenghaolin@zuzuche.com
 * Date:     2023/1/6 14:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2023/1/6 14:16   V1.0.0
 */
package com.dantefung.collection;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Title: CollectionOpTest
 * @Description:
 * @author fenghaolin
 * @date 2023/01/06 14/16
 * @since JDK1.8
 */
public class CollectionOpTest {

	public static void main(String[] args) {
		// 测试交集
//		intersectionTest();
		// 比较两个集合是否相等
//		equalCollectionTest();

		equalCollectionTest2();

	}

	private static void equalCollectionTest2() {
		List<User> users = Lists.newArrayList(User.of("james", "1234", "男"), User.of("ben", "123", "男"));
		List<User> users1 = Lists.newArrayList(User.of("james1", "1234", "男"), User.of("ben1", "123", "男"));
		List<User> users2 = Lists.newArrayList(User.of("james", "1234", "男"), User.of("ben", "123", "男"));
		System.out.println(ListUtils.isEqualList(users, users1));
		System.out.println(ListUtils.isEqualList(users, users2));
	}

	private static void equalCollectionTest() {
		ArrayList<String> listA = new ArrayList<String>() {{
			add("a");
			add("b");
			add("c");
		}};
		ArrayList<String> listB = new ArrayList<String>() {{
			add("b");
			add("c");
			add("a");
		}};
		System.out.println(listA.equals(listB));// false,缺点：需要先对集合进行排序

		System.out.println(listA.containsAll(listB) && listB.containsAll(listA));// true,交叉包含判断，缺点：无法判断集合包含相同元素的情况[a,b,c]和[a,a,b,c]

		/**
		 * iff the collections contain the same elements with the same cardinalities.
		 * 1. 集合大小是否相同
		 * 2. 比较元素的个数
		 * 3. 比较元素出现的频次
		 * 总体来说这种方式比使用原生的相等性判断要方便，且算法复杂度较低。
		 */
		System.out.println(CollectionUtils.isEqualCollection(listA, listB));// true，推荐，使用简单入参非空即可，算法复杂度低，不用排序

		System.out.println(ListUtils.isEqualList(listA, listB));

		Collections.sort(listA);
		Collections.sort(listB);
		System.out.println(listA.equals(listB));// true，用于佐证集合排序后可以使用该API判断相等
	}

	private static void intersectionTest() {
		User user = User.of("女");
		User user1 = User.of("james", "1234", "男");
		User user2 = User.of("ben", "123", "男");
		User user3 = User.of("Tim", "124", "男");
		List<User> list1 = Lists.newArrayList(user1, user2, user3);
		List<User> list2 = Lists.newArrayList(user, user2, user3);
		Collection<User> intersection = CollectionUtils.intersection(list1, list2);
		System.out.println(intersection);
	}


	@Accessors(chain = true)
	@Data
	@AllArgsConstructor(staticName = "of")
	@RequiredArgsConstructor(staticName = "of")
	static class User {

		private String username;

		private String password;

		@NonNull
		private String gender;

	}
}
