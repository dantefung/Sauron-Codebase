package com.dantefung.concurrent;

class SafeCalc01 {
  long value = 0L;
  synchronized long get() {
    return value;
  }
  synchronized void addOne() {
    value += 1;
  }
}