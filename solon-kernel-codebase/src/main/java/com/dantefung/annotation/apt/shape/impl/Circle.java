package com.dantefung.annotation.apt.shape.impl;

import com.dantefung.annotation.Factory;
import com.dantefung.annotation.apt.shape.IShape;

@Factory(id = "Circle", type = IShape.class)
public class Circle implements IShape {
    @Override
    public void draw() {   
        System.out.println("Draw a circle");
    }
}