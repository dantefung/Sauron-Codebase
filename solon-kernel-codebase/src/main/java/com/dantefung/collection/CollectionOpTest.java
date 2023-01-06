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

import java.util.ArrayList;
import java.util.Collection;
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
		intersectionTest();
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
