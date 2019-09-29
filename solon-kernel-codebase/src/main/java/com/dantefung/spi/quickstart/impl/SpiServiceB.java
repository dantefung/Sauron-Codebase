package com.dantefung.spi.quickstart.impl;


import com.dantefung.spi.quickstart.SpiService;

public class SpiServiceB implements SpiService {

    @Override
    public void hello() {
        System.out.println("SpiServiceB.hello");
    }
    
}