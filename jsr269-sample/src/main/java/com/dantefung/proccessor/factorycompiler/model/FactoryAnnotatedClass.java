package com.dantefung.proccessor.factorycompiler.model;


import com.dantefung.annotation.Factory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import java.text.MessageFormat;

public class FactoryAnnotatedClass {

    /**
     *表示一个类或接口程序元素。提供对有关类型及其成员的信息的访问。注意，枚举类型是一种类，而注解类型是一种接口。
     */
    private TypeElement mAnnotatedClassElement;
    /** **/
    private String mQualifiedSuperClassName;
    /**  **/
    private String mSimpleTypeName;
    /** 被标注@Factory(id="")的类的id的值 **/
    private String mId;

    public FactoryAnnotatedClass(TypeElement classElement) {
        this.mAnnotatedClassElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        mId = annotation.id();
        if (mId.length() == 0) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }
        
        // Get the full QualifiedTypeName
        try {  // 该类已经被编译
            Class<?> clazz = annotation.type();
            mQualifiedSuperClassName = clazz.getCanonicalName();
            mSimpleTypeName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {// 该类未被编译
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            mQualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            mSimpleTypeName = classTypeElement.getSimpleName().toString();
        }
        System.out.println("+---------------------------------------------------------------------------------------------+");
        System.out.println(String.format("mId:%s\r\nmAnnotatedClassElement:%s\r\nmQualifiedSuperClassName:%s\r\nmSimpleTypeName:%s"
                ,mId
                ,mAnnotatedClassElement
                ,mQualifiedSuperClassName
                ,mSimpleTypeName));
        System.out.println("+---------------------------------------------------------------------------------------------+");
    }

    public String getId() {
        return mId;
    }

    public String getQualifiedFactoryGroupName() {
        return mQualifiedSuperClassName;
    }


    public String getSimpleFactoryGroupName() {
        return mSimpleTypeName;
    }

    public TypeElement getTypeElement() {
        return mAnnotatedClassElement;
    }

}