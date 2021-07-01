/*
 * Copyright (C), 2015-2020
 * FileName: BitMap
 * Author:   DANTE FUNG
 * Date:     2021/7/1 下午9:59
 * Description: bitmap
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/1 下午9:59   V1.0.0
 */
package com.dantefung.algorithm.bitmap;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: BitMap
 * @Description: bitmap
 *
 * 在java中，一个int类型占32个比特，我们用一个int数组来表示时未new int[32],总计占用内存32*32bit,现假如我们用int字节码的每一位表示一个数字的话，那么32个数字只需要一个int类型所占内存空间大小就够了，这样在大数据量的情况下会节省很多内存。
 *
 * 具体思路：
 *
 * 		1个int占4字节即4*8=32位，那么我们只需要申请一个int数组长度为 int tmp[1+N/32]即可存储完这些数据，其中N代表要进行查找的总数，tmp中的每个元素在内存在占32位可以对应表示十进制数0~31,所以可得到BitMap表:
 *
 * 			tmp[0]:可表示0~31
 *
 * 			tmp[1]:可表示32~63
 *
 * 			tmp[2]可表示64~95
 *
 * 			.......
 *
 * 		那么接下来就看看十进制数如何转换为对应的bit位：
 *
 * 		假设这40亿int数据为：6,3,8,32,36,......，那么具体的BitMap表示为：
 *
 *
 * 	如何判断int数字在tmp数组的哪个下标，这个其实可以通过直接除以32取整数部分，例如：整数8除以32取整等于0，那么8就在tmp[0]上。
 *
 * 	另外，我们如何知道了8在tmp[0]中的32个位中的哪个位，这种情况直接mod上32就ok，又如整数8，在tmp[0]中的第8 mod上32等于8，那么整数8就在tmp[0]中的第八个bit位（从右边数起）。
 *
 *
 * @author DANTE FUNG
 * @date 2021/07/01 21/59
 * @since JDK1.8
 */
public class BitMap {

	private static final int[] BIT_VALUE = {0x00000001, 0x00000002, 0x00000004, 0x00000008, 0x00000010, 0x00000020,
			0x00000040, 0x00000080, 0x00000100, 0x00000200, 0x00000400, 0x00000800, 0x00001000, 0x00002000, 0x00004000,
			0x00008000, 0x00010000, 0x00020000, 0x00040000, 0x00080000, 0x00100000, 0x00200000, 0x00400000, 0x00800000,
			0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000};

	private static final String[] BIT_VALUES = {"0x00000001", "0x00000002", "0x00000004", "0x00000008", "0x00000010",
			"0x00000020", "0x00000040", "0x00000080", "0x00000100", "0x00000200", "0x00000400", "0x00000800",
			"0x00001000", "0x00002000", "0x00004000", "0x00008000", "0x00010000", "0x00020000", "0x00040000",
			"0x00080000", "0x00100000", "0x00200000", "0x00400000", "0x00800000", "0x01000000", "0x02000000",
			"0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};

	private static final String[] BIT_BINARY_VALUES = {"00000000000000000000000000000001",
			"00000000000000000000000000000010", "00000000000000000000000000000100", "00000000000000000000000000001000",
			"00000000000000000000000000010000", "00000000000000000000000000100000", "00000000000000000000000001000000",
			"00000000000000000000000010000000", "00000000000000000000000100000000", "00000000000000000000001000000000",
			"00000000000000000000010000000000", "00000000000000000000100000000000", "00000000000000000001000000000000",
			"00000000000000000010000000000000", "00000000000000000100000000000000", "00000000000000001000000000000000",
			"00000000000000010000000000000000", "00000000000000100000000000000000", "00000000000001000000000000000000",
			"00000000000010000000000000000000", "00000000000100000000000000000000", "00000000001000000000000000000000",
			"00000000010000000000000000000000", "00000000100000000000000000000000", "00000001000000000000000000000000",
			"00000010000000000000000000000000", "00000100000000000000000000000000", "00001000000000000000000000000000",
			"00010000000000000000000000000000", "00100000000000000000000000000000", "01000000000000000000000000000000",
			"10000000000000000000000000000000"};
	private static final String[] BIT_DECIMAL_VALUES = {"1", "2", "4", "8", "16", "32", "64", "128", "256", "512",
			"1024", "2048", "4096", "8192", "16384", "32768", "65536", "131072", "262144", "524288", "1048576",
			"2097152", "4194304", "8388608", "16777216", "33554432", "67108864", "134217728", "268435456", "536870912",
			"1073741824", "2147483648"};
	private static final String[] BIT_POWER_VALUES = {"2^0", "2^1", "2^2", "2^3", "2^4", "2^5", "2^6", "2^7", "2^8",
			"2^9", "2^10", "2^11", "2^12", "2^13", "2^14", "2^15", "2^16", "2^17", "2^18", "2^19", "2^20", "2^21",
			"2^22", "2^23", "2^24", "2^25", "2^26", "2^27", "2^28", "2^29", "2^30", "2^31"};

	/** 数据总数**/
	private long length;

	private int[] bitsMap;

	public BitMap(long length) {
		this.length = length;
		/**
		 * 根据长度算出，所需数组大小
		 * 当 length%32=0 时大小等于
		 * = length/32
		 * 当 length%32>0 时大小等于
		 * = length/32+l
		 */
		bitsMap = new int[(int) (length >> 5) + ((length & 31) > 0 ? 1 : 0)];
	}

	/**
	 * @param n 要被设置的值为n
	 */
	public void setN(long n) {
		if (n < 0 || n > length) {
			throw new IllegalArgumentException("length value " + n + " is  illegal!");
		}
		// 求出该n所在bitMap的下标,等价于"n/2^5"
		int index = (int) n >> 5;
		// 求出该值的偏移量(求余),等价于"n%32", n=3 ,11111 & 00011 = 00011
		// y=2^x(x是自然数)   n%y=n & (y-1)  取低x位.
		// 32 = 2^5   n/(32-1)  取低5位
		int offset = (int) n & 31;
		/**
		 * 等价于
		 * int bits = bitsMap[index];
		 * bitsMap[index]=bits| BIT_VALUE[offset];
		 * 例如,n=3时,设置byte第4个位置为1 （从0开始计数，bitsMap[0]可代表的数为：0~31，从左到右每一个bit位表示一位数）
		 * bitsMap[0]=00000000 00000000 00000000 00000000  |  00000000 00000000 00000000 00001000=00000000 00000000 00000000 00000000 00001000
		 * 即: bitsMap[0]= 0 | 0x00000008 = 3
		 *
		 * 例如,n=4时,设置byte第5个位置为1
		 * bitsMap[0]=00000000 00000000 00000000 00001000  |  00000000 00000000 00000000 00010000=00000000 00000000 00000000 00000000 00011000
		 * 即: bitsMap[0]=3 | 0x00000010 = 12
		 */
		bitsMap[index] |= BIT_VALUE[offset];

	}

	/**
	 * 获取值N是否存在
	 * @return 1：存在，0：不存在
	 */
	public int isExist(long n) {
		if (n < 0 || n > length) {
			throw new IllegalArgumentException("length value illegal!");
		}
		int index = (int) n >> 5;
		int offset = (int) n & 31;
		int bits = (int) bitsMap[index];
		System.out.println(
				"n=" + n + ",index=" + index + ",offset=" + offset + ",bits=" + Integer.toBinaryString(bitsMap[index]));
		return ((bits & BIT_VALUE[offset])) >>> offset;
	}

	public void clear(long n) {
		if (n < 0 || n > length) {
			throw new IllegalArgumentException("length value illegal!");
		}
		int index = (int) n >> 5;
		int offset = (int) n & 31;
		int bits = (int) bitsMap[index];
		//bitsMap[index] = bits & (~BIT_VALUE[offset]);  <=> bitsMap[index] = bits & (~(1 << offset))
		bitsMap[index] = bits & (~(1 << offset));
		System.out.println(
				"n=" + n + ",index=" + index + ",offset=" + offset + ",bits=" + Integer.toBinaryString(bitsMap[index])
						+ ",clear out result:" + Integer.toBinaryString(bitsMap[index]));
	}

	public void printBitIdx() {
		for (int i = 0; i < bitsMap.length; i++) {
			int b = 31;
			for (int offset = 0; offset < 32; offset++) {
				int r = (bitsMap[i] & (1<<offset)) >>> offset;
				if(r == 1) {
					System.out.println(b*i+offset);
				}
			}
		}
	}

	public List getValidBitOffsets() {
		List result = new ArrayList();
		/**
		 *  由同余定理可知
		 *  (a-b)/m   a≡b(mod m)
		 *  a = m*k + b
		 */
		for (int i = 0; i < bitsMap.length; i++) {
			int m = 31; // 模31
			for (int offset = 0; offset < 32; offset++) {
				int r = (bitsMap[i] & (1<<offset)) >>> offset;
				if(r == 1) {
					System.out.println(m*i+offset);
					result.add(m * i + offset);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// 假设有65个数
		int length = 65;
		BitMap bitMap = new BitMap(65);
		bitMap.setN(3);
		bitMap.setN(5);
		bitMap.isExist(3);
		bitMap.clear(3);

		// 用于排序, 设有[4,7,2,5,3]
		int[] arr = new int[]{4, 7, 2, 5, 3};// 这些数均认为bit的offset
		BitMap bitMapToSort = new BitMap(8);
		for (int i = 0; i < arr.length; i++) {
			bitMapToSort.setN(arr[i]);
			bitMap.isExist(arr[i]);
		}
		bitMapToSort.printBitIdx();
		System.out.println(bitMapToSort.getValidBitOffsets());


		//		String[] binaryStrArr = new String[BIT_VALUE.length];
		//		for (int j = 0; j < BIT_VALUE.length; j++) {
		//			int hex = BIT_VALUE[j];
		//			String binaryString = Integer.toBinaryString(hex);
		//			System.out.println(StringUtils.leftPad(binaryString, 32, "0"));
		//			binaryStrArr[j] = String.format("\"%s\"", StringUtils.leftPad(binaryString, 32, "0"));
		//		}
		//		System.out.println(Arrays.toString(binaryStrArr));
		/*String[] result = new String[BIT_VALUES.length];
		for (int i = 0; i < BIT_VALUES.length; i++) {
			String hex = BIT_VALUES[i];
			System.out.println(hex);
			Integer x = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
			System.out.println(x);
			result[i] = String.format("\"%s\"", x);
		}
		System.out.println(Arrays.toString(result));*/
		//		String[] strArr = new String[BIT_VALUE.length];
		//		for (int k = 0; k < BIT_VALUE.length; k++) {
		//			strArr[k] = String.format("\"2^%s\"", k);
		//		}
		//		System.out.println(Arrays.toString(strArr));
	}
}
