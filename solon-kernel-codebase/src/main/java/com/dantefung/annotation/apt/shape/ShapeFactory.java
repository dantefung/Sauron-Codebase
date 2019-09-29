package com.dantefung.annotation.apt.shape;

import com.dantefung.annotation.apt.shape.impl.Circle;
import com.dantefung.annotation.apt.shape.impl.Rectangle;
import com.dantefung.annotation.apt.shape.impl.Triangle;

/**
 * 简单工厂
 */
public class ShapeFactory {

    public IShape create(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null!");
        }
        if ("Circle".equals(id)) {
            return new Circle();
        }
        if ("Rectangle".equals(id)) {
            return new Rectangle();
        }
        if ("Triangle".equals(id)) {
            return new Triangle();
        }
        throw new IllegalArgumentException("Unknown id = " + id);
    }
}
