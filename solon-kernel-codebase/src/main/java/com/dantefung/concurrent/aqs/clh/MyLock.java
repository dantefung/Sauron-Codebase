package com.dantefung.concurrent.aqs.clh;

public interface MyLock {
    void lock();
    void unlock();
    void remove();
}