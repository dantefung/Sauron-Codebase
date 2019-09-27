package com.dantefung.algorithm.sort;


public class Shell {

	// This class should not be instantiated.
    private Shell() { }
    
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        while (h < N/3) h = 3*h + 1; 

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && SortHelper.less(a[j], a[j-h]); j -= h) {
                    SortHelper.exch(a, j, j-h);
                }
            }
            assert SortHelper.isHsorted(a, h); 
            h /= 3;
        }
        assert SortHelper.isSorted(a);
    }

}
