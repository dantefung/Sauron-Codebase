package com.dantefung.concurrent.mcs;

public interface MyLock {
    void lock();
    void unlock();
    void remove();
}