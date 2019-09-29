package com.dantefung.annotation.apt.shape.factorycompiler.exception;

import javax.lang.model.element.Element;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/29 17:25
 */
public class ProcessingException extends Exception {

    Element element;

    public ProcessingException(Element element, String message, String ...args) {
        super(String.format(message, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
