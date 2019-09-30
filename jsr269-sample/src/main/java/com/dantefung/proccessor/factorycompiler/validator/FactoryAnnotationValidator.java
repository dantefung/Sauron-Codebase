package com.dantefung.proccessor.factorycompiler.validator;


import com.dantefung.annotation.Factory;
import com.dantefung.proccessor.factorycompiler.exception.ProcessingException;
import com.dantefung.proccessor.factorycompiler.model.FactoryAnnotatedClass;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 *
 * 1.只有类才能被@Factory注解。因为在ShapeFactory中我们需要实例化Shape对象，虽然@Factory注解声明了Target为ElementType.TYPE，但接口和枚举并不符合我们的要求。
 * 2.被@Factory注解的类中需要有public的构造方法，这样才能实例化对象。
 * 3.被注解的类必须是type指定的类的子类
 * 4.id需要为String类型，并且需要在相同type组中唯一
 * 5.具有相同type的注解类会被生成在同一个工厂类中
 *
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/29 17:19
 */
public class FactoryAnnotationValidator {

    private Types typeUtils;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    public FactoryAnnotationValidator() {

    }

    public FactoryAnnotationValidator(Elements elementUtils,Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    /**
     * Checks if the annotated element observes our rules
     */
    public void checkValidClass(FactoryAnnotatedClass item) throws ProcessingException {

        // Cast to TypeElement, has more type specific methods
        TypeElement classElement = item.getTypeElement();

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new ProcessingException(classElement, "The class %s is not public.",
                    classElement.getQualifiedName().toString());
        }

        // Check if it's an abstract class
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ProcessingException(classElement,
                    "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
        }

        // Check inheritance: Class must be child class as specified in @Factory.type();
        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedFactoryGroupName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            // Check interface implemented
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
            }
        } else {
            // Check subclassing
            TypeElement currentClass = classElement;
            while (true) {
                /**
                 * getSuperclass()
                 * Returns the direct superclass of this type element.
                 * If this type element represents an interface or the class java.lang.Object,
                 * then a NoType with kind NONE is returned.
                 */
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {
                    // Basis class (java.lang.Object) reached, so exit
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                }

                if (superClassType.toString().equals(item.getQualifiedFactoryGroupName())) {
                    // Required super class found
                    break;
                }

                // Moving up in inheritance tree
                currentClass = (TypeElement) typeUtils.asElement(superClassType);
            }
        }

        // Check if an empty public constructor is given
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 &&
                        constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    // Found an empty constructor
                    return;
                }
            }
        }

        // No empty constructor found
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());
    }
}
