package com.dantefung.algorithm.sort;


public class Insertion {
	// This class should not be instantiated.
    private Insertion() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && SortHelper.less(a[j], a[j-1]); j--) {
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
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && SortHelper.less(a[j], a[j-1]); j--) {
            	SortHelper.exch(a, j, j-1);
            }
        }
        assert SortHelper.isSorted(a, lo, hi);
    }
}
