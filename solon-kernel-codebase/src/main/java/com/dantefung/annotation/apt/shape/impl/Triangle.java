package com.dantefung.annotation.apt.shape.impl;

import com.dantefung.annotation.apt.shape.Factory;
import com.dantefung.annotation.apt.shape.IShape;

@Factory(id = "Triangle", type = IShape.class)
public class Triangle implements IShape {
	@Override
	public void draw() {
		System.out.println("Draw a Triangle");
	}
}
