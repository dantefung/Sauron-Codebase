/**
 * Leetcode - two_sum
 */
package com.dantefung.leetcode.two_sum;
import java.util.*;
import com.ciaoshen.leetcode.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface Solution {

    // use this Object to print the log (call from slf4j facade)
    static Logger log = LoggerFactory.getLogger(Solution.class);
	int[] twoSum(int[] nums, int target);
}
