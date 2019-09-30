package com.dantefung.proccessor.factorycompiler.exception;


import com.dantefung.proccessor.factorycompiler.model.FactoryAnnotatedClass;

public class IdAlreadyUsedException extends RuntimeException {

    public IdAlreadyUsedException(FactoryAnnotatedClass factoryAnnotatedClass) {

    }
}