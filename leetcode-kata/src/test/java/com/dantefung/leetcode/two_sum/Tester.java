/**
 * Leetcode - two_sum
 */
package com.dantefung.leetcode.two_sum;

import java.util.*;

import com.ciaoshen.leetcode.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class Tester {

	// use this Object to print the log (call from slf4j facade)
	private static final Logger LOGGER = LoggerFactory.getLogger(TesterRunner.class);
	/**=========================== static for every test cases ============================== */

	// Solution instance to test
	private static Solution solution;
	/**
	 * Parameters for each test (initialized in testcases() method)
	 * You can change the type of parameters
	 */
	private int[] nums;                       // parameter 1
	private int target;                       // parameter 2
	private int[] expected;                   // parameter 3 (expected answer)

	/**=========================== for each test case ============================== */

	/** This constructor must be provided to run parameterized test. */
	public Tester(int[] nums, int target, int[] expected) {
		// initialize test parameters
		this.nums = nums;
		this.target = target;
		this.expected = expected;
	}

	/** Execute once before any of the test methods in this class. */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/* uncomment to switch solutions */
		//        solution = new Solution1();
		solution = new Solution2();
	}

	/** Execute once after all of the test methods are executed in this class. */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/** Initialize test cases */
	@Parameters
	public static Collection<Object[]> testcases() {
		return Arrays.asList(new Object[][]{
				// {},     // test case 1 (init parameters below: {para1, para2, expected})
				// {},     // test case 2 (init parameters below: {para1, para2, expected})
				// {}      // test case 3 (init parameters below: {para1, para2, expected})
				{new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}}});
	}

	/** Execute before each test method in this class is executed. */
	@Before
	public void setUp() throws Exception {
	}

	/** Executed as a test case. */
	@Test
	public void test() {

		int[] actual = solution.twoSum(nums, target);

		boolean eq = actual.length == expected.length;
		for (int i = 0; i < actual.length; i++) {
			if (!(actual[i] == expected[i])) {
				eq = false;
				break;
			}
		}

		assertThat(eq, is(true));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("twoSum() pass unit test!");
		}
	}

	/** Execute after each test method in this class is executed. */
	@After
	public void tearDown() throws Exception {
	}

}
