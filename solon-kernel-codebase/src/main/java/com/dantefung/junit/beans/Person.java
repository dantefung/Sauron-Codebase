package com.dantefung.junit.beans;

import lombok.Data;

/**
 * @Description: 普通bean
 */
@Data
public class Person {
    private String firstName;
    private String lastName;
    private Types type;
}