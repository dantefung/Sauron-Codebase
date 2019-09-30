package com.basic.sample;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Set;

@SupportedAnnotationTypes(value = {"com.basic.sample.Test"})//这个一定不能写错
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {

        System.out.println("+====================================init START==================================================+");

        System.out.println(">>>>>>>>>>>>>>>>>>>> FactoryProcessor init ...");
        super.init(processingEnvironment);

        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();


        System.out.println(String.format("typeUtils:%s\r\nmessager:%s\r\nfiler:%s\r\nelementUtils:%s"
                ,typeUtils
                ,messager
                ,filer
                ,elementUtils));
        System.out.println("+======================================init END=================================================+");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Log in AnnotationProcessor.process");
        for (TypeElement typeElement : annotations) {
            System.out.println(">>>>>>>>>>>>>>>>>annotation:" + typeElement);
            Name qualifiedName = typeElement.getQualifiedName();
            System.out.println(">>>>>>>>>>>>>>>>annotation qualifiedName:" + qualifiedName);
            Name simpleName = typeElement.getSimpleName();
            System.out.println(">>>>>>>>>>>>>>>>annotation simpleName:" + qualifiedName);
        }
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Test.class)) {
            if (annotatedElement.getKind() != ElementKind.METHOD) {
                throw new RuntimeException(String.format("Only methods can be annotated with @%s",
                        Test.class.getSimpleName()));
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>被@Test标注的元素:"+annotatedElement);
            ExecutableElement typeElement = (ExecutableElement) annotatedElement;
            System.out.println(">>>>>>>>>>>>>>>>>>>value:"+typeElement.getAnnotation(Test.class).value());
        }
        System.out.println(roundEnv);
        return true;
    }
}