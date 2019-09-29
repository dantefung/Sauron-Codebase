package com.dantefung.annotation.apt.shape.factorycompiler.exception;


import com.dantefung.annotation.apt.shape.factorycompiler.model.FactoryAnnotatedClass;

public class IdAlreadyUsedException extends RuntimeException {

    public IdAlreadyUsedException(FactoryAnnotatedClass factoryAnnotatedClass) {

    }
}