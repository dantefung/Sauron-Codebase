package com.dantefung.algorithm.sort;


public class Bubble {
	
	// This class should not be instantiated.
	private Bubble() {}
	
	/**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        
        for (int i = N; i > 0; i--) {
            for (int j = 1; j < i; j++) {
            	if (SortHelper.less(a[j], a[j - 1]))
            	SortHelper.exch(a, j, j-1);
            }
            assert SortHelper.isSorted(a, 0, i);
        }
        assert SortHelper.isSorted(a);
    }

    /**
     * Rearranges the subarray a[lo..hi] in ascending order, using the natural order.
     * @param a the array to be sorted
     * @param lo left endpoint
     * @param hi right endpoint
     */
    public static void sort(Comparable[] a, int lo, int hi) {
        for (int i = hi + 1; i > lo; i--) {
            for (int j = lo + 1; j < i; j++) {
            	if (SortHelper.less(a[j], a[j - 1]))
            	SortHelper.exch(a, j, j-1);
            }
        }
        assert SortHelper.isSorted(a, lo, hi);
    }

}
