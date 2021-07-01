/**
 * Leetcode - two_sum
 */
package com.dantefung.leetcode.two_sum;
import java.util.*;
import com.ciaoshen.leetcode.util.*;

/** 
 * log instance is defined in Solution interface
 * this is how slf4j will work in this class:
 * =============================================
 *     if (log.isDebugEnabled()) {
 *         log.debug("a + b = {}", sum);
 *     }
 * =============================================
 */
class Solution2 implements Solution {

	public int[] twoSumLogN(int[] nums, int target) {
		if (nums.length < 2) {
			throw new IllegalArgumentException();
		}
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int diff = target - nums[i];
			if (map.containsKey(diff)) {
				return new int[] {map.get(diff),i};
			}
			map.put(nums[i],i);
		}
		return new int[] {0,0};
	}

	@Override
	public int[] twoSum(int[] nums, int target) {
		return twoSumLogN(nums, target);
	}
}
