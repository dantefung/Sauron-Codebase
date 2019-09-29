package com.dantefung.spi.quickstart.impl;


import com.dantefung.spi.quickstart.SpiService;

public class SpiServiceA implements SpiService {

    public void hello() {
        System.out.println("SpiServiceA.Hello");
    }
    
}