package com.dantefung.algorithm.sort;

import java.util.Comparator;

public class Selection {
	
	// This class should not be instantiated.
    private Selection() { }

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i+1; j < N; j++) {
                if (SortHelper.less(a[j], a[min])) min = j;
            }
            SortHelper.exch(a, i, min);
            assert SortHelper.isSorted(a, 0, i);
        }
        assert SortHelper.isSorted(a);
    }
    
    public static void sort(Object[] a, Comparator c) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i+1; j < N; j++) {
                if (SortHelper.less(c, a[j], a[min])) min = j;
            }
            SortHelper.exch(a, i, min);
            assert SortHelper.isSorted(a, c, 0, i);
        }
        assert SortHelper.isSorted(a, c);
    }
}
