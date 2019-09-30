package com.dantefung.proccessor.factorycompiler.processor;

import com.dantefung.annotation.Factory;
import com.dantefung.proccessor.factorycompiler.exception.ProcessingException;
import com.dantefung.proccessor.factorycompiler.model.FactoryAnnotatedClass;
import com.dantefung.proccessor.factorycompiler.model.FactoryGroupedClasses;
import com.dantefung.proccessor.factorycompiler.validator.FactoryAnnotationValidator;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * http://www.cjsdn.net/Doc/JDK60/javax/annotation/processing/AbstractProcessor.html
 * 不使用@AutoService也可以自己在META-INF/services下配置SPI要求的配置文件
 */
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<String, FactoryGroupedClasses>();

    /**
     * 这个方法用于初始化处理器，方法中有一个ProcessingEnvironment类型的参数，ProcessingEnvironment是一个注解处理工具的集合。
     * 它包含了众多工具类。例如：
     * Filer可以用来编写新文件；
     * Messager可以用来打印错误信息；
     * Elements是一个可以处理Element的工具类。
     * @param processingEnvironment
     */
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


    /**
     * 这个方法的返回值是一个Set集合，集合中指要处理的注解类型的名称(这里必须是完整的包名+类名，例如com.example.annotation.Factory)。由于在本例中只需要处理@Factory注解，因此Set集合中只需要添加@Factory的名称即可。
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    /**
     * 在Java语言中，Element是一个接口，表示一个程序元素，它可以指代包、类、方法或者一个变量。Element已知的子接口有如下几种：
     *
     * PackageElement 表示一个包程序元素。提供对有关包及其成员的信息的访问。
     * ExecutableElement 表示某个类或接口的方法、构造方法或初始化程序（静态或实例），包括注释类型元素。
     * TypeElement 表示一个类或接口程序元素。提供对有关类型及其成员的信息的访问。注意，枚举类型是一种类，而注解类型是一种接口。
     * VariableElement 表示一个字段、enum 常量、方法或构造方法参数、局部变量或异常参数。
     *
     * 把Java类看作是一个结构化的文件,抽象如下:
     *
     * package com.zhpan.mannotation.factory;  //    PackageElement
     *
     * public class Circle {  //  TypeElement
     *
     *     private int i; //   VariableElement
     *     private Triangle triangle;  //  VariableElement
     *
     *     public Circle() {} //    ExecuteableElement
     *
     *     public void draw(   //  ExecuteableElement
     *                         String s)   //  VariableElement
     *     {
     *         System.out.println(s);
     *     }
     *
     *     @Override
     *     public void draw() {    //  ExecuteableElement
     *         System.out.println("Draw a circle");
     *     }
     * }
     * /


     /**
     *
     * 这个方法的返回值，是一个boolean类型，返回值表示注解是否由当前Processor 处理。
     * 如果返回 true，则这些注解由此注解来处理，后续其它的 Processor 无需再处理它们；如果返回 false，
     * 则这些注解未在此Processor中处理并，那么后续 Processor 可以继续处理它们。
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println();
        System.out.println("+====================================process START==================================================+");
        System.out.println(">>>>>>>>>>>>>>>> process ...");
        try {
            //	扫描所有被@Factory注解的元素
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Factory.class)) {
                /**
                 * 正常情况下，这个集合中应该包含的是所有被Factory注解的Shape类的元素，也就是一个TypeElement。
                 * 但在编写程序代码时可能有新来的同事不太了解@Factory的用途而误把@Factory用在接口或者抽象类上，这是不符合我们的标准的。
                 * 因此，需要在process方法中判断被@Factory注解的元素是否是一个类，如果不是一个类元素，那么就抛出异常，终止编译。
                 */
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s",
                            Factory.class.getSimpleName());
                }
                TypeElement typeElement = (TypeElement) annotatedElement;
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

                // 校验
                FactoryAnnotationValidator factoryAnnotationValidator = new FactoryAnnotationValidator(elementUtils, typeUtils);
                factoryAnnotationValidator.checkValidClass(annotatedClass);

                // Everything is fine, so try to add
                FactoryGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedFactoryGroupName());
                if (factoryClass == null) {
                    String qualifiedGroupName = annotatedClass.getQualifiedFactoryGroupName();
                    factoryClass = new FactoryGroupedClasses(qualifiedGroupName);
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // Checks if id is conflicting with another @Factory annotated class with the same id
                factoryClass.add(annotatedClass);

            }

            // Generate code
            for (FactoryGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(elementUtils, filer);
            }
            factoryClasses.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("+====================================process END==================================================+");

        return true;
    }

    /**
     * 这个方法非常简单，只有一个返回值，用来指定当前正在使用的Java版本，通常return SourceVersion.latestSupported()即可。
     * 在这个方法的方法体中，我们可以校验被注解的对象是否合法、可以编写处理注解的代码，以及自动生成需要的java文件等。因此说这个方法是AbstractProcessor 中的最重要的一个方法。
     * 我们要处理的大部分逻辑都是在这个方法中完成。
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
