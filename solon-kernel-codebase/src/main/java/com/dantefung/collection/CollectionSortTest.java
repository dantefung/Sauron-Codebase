package com.dantefung.collection;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2024/01/09 17/25
 * @since JDK1.8
 */
public class CollectionSortTest {

	public static void main(String[] args) {

		List<User> users = Lists
				.newArrayList(new User(1L, "a"), new User(1L, "b"), new User(2L, "c"), new User(2L, "c1"), new User(3L, "d"),
						new User(4L, "e"), new User(5L, "f"),  new User(7L, "g"),  new User(7L, "g1"));
		Map<Long, Integer> priorityMap = new HashMap<>();
		priorityMap.put(4L, 0);
		priorityMap.put(2L, 1);
		priorityMap.put(7L, 2);

		Collections.sort(users, (u1, u2) -> {
			int p1 = priorityMap.getOrDefault(u1.getType(), Integer.MAX_VALUE);
			int p2 = priorityMap.getOrDefault(u2.getType(), Integer.MAX_VALUE);

			if (p1 != p2) {
				return Integer.compare(p1, p2); // 按优先级排序
			} else {
				return u1.getType().compareTo(u2.getType()); // 同优先级按自然顺序排序
			}
		});
		System.out.println(users);
		users.forEach(user -> System.out.println(user.getType() + " " + user.getName()));
	}

	@AllArgsConstructor
	@Data
	public static class User {

		private Long type;

		private String name;
	}
}
