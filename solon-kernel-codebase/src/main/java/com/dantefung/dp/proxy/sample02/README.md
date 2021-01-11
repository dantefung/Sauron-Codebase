## Cglib
```
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Student.class);
		enhancer.setCallback(new MyMethodInterceptor());
		Student student = (Student) enhancer.create();
		student.sayHello();
		student.speak();
		student.walk();
		student.study();
```
---- 
AbstractClassGenerator的子类
- Enhancer
- Generator

Generator的内部类:
- Generator

AbstractClassGenerator的内部类
- ClassLoaderData


Enhancer.create()

--> Enhancer.createHelper();

-->  Object result = super.create(key);
AbstractClassGenerator.create

-->
 ```
    public Object get(AbstractClassGenerator gen, boolean useCache) {
                if (!useCache) {
                  return gen.generate(ClassLoaderData.this);
                } else {
                  Object cachedValue = generatedClasses.get(gen);
                  return gen.unwrapCachedValue(cachedValue);
                }
            }
```
--> gen.generate(ClassLoaderData.this);// 使用子类的生成方法.

Enhancer.generate(ClassLoadData data)

--> super.generate()
```
 byte[] b = strategy.generate(this);
```
--> Enhancer.generateClass

----

KeyFactory.Generator().create()

-->super.create(keyInterface.getName())

-->Object obj = data.get(this, getUseCache());

-->gen.generate(ClassLoaderData.this);

-->AbstractClassGenerator.generate()  // Generator没有重写generate()方法

--> byte[] b = strategy.generate(this); // 使用默认的生成策略



### KEY_FACTORY 前置了解
追踪源码可以看到，KEY_FACTORY在Enhancer的初始化即会创建一个final的静态变量。
生成了Enhancer.EnhancerKey的代理类，也就是我们需要的代理类标识类  用来标识被代理的类，这个代理类主要用来作为被代理类的标识，在进行缓存时作为判断相等的依据。可以看到 cglib代理主要也是利用我们传入的被代理类信息来生成对应的代理类字节码，然后用类加载器加载到内存中。
```
private static final EnhancerKey KEY_FACTORY =
      (EnhancerKey)KeyFactory.create(EnhancerKey.class, KeyFactory.HASH_ASM_TYPE, null);
```
Keyfactory.create方法
```
public static KeyFactory create(ClassLoader loader, Class keyInterface, KeyFactoryCustomizer customizer,
                                    List<KeyFactoryCustomizer> next) {
        //创建一个最简易的代理类生成器 即只会生成HashCode equals toString newInstance方法
        Generator gen = new Generator();
        //设置接口为enhancerKey类型
        gen.setInterface(keyInterface);

        if (customizer != null) {
        //添加定制器
            gen.addCustomizer(customizer);
        }
        if (next != null && !next.isEmpty()) {
            for (KeyFactoryCustomizer keyFactoryCustomizer : next) {
                    //添加定制器
                gen.addCustomizer(keyFactoryCustomizer);
            }
        }
        //设置生成器的类加载器
        gen.setClassLoader(loader);
        //生成enhancerKey的代理类
        return gen.create();
    }
```
Generator的create方法
```
public KeyFactory create() {
    //设置了该生成器生成代理类的名字前缀，即我们的接口名Enhancer.enhancerKey
    setNamePrefix(keyInterface.getName());
    return (KeyFactory)super.create(keyInterface.getName());
}
```
---- 

　setCallbacks()，即设置回调，我们创建出代理类后调用方法则是使用的这个回调接口，类似于jdk动态代理中的InvocationHandler
```
 /**
     * Set the class which the generated class will extend. As a convenience,
     * if the supplied superclass is actually an interface, <code>setInterfaces</code>
     * will be called with the appropriate argument instead.
     * A non-interface argument must not be declared as final, and must have an
     * accessible constructor.
     * @param superclass class to extend or interface to implement
     * @see #setInterfaces(Class[])
     */
    public void setSuperclass(Class superclass) {
        if (superclass != null && superclass.isInterface()) {
            // 设置代理接口
            setInterfaces(new Class[]{ superclass });
        } else if (superclass != null && superclass.equals(Object.class)) {
            // affects choice of ClassLoader
            this.superclass = null;
        } else {
             // 设置代理类
            this.superclass = superclass;
        }
    }

```
setCallbacks()，即设置回调，我们创建出代理类后调用方法则是使用的这个回调接口，类似于jdk动态代理中的InvocationHandler
```
    /**
     * Set the array of callbacks to use.
     * Ignored if you use {@link #createClass}.
     * You must use a {@link CallbackFilter} to specify the index into this
     * array for each method in the proxied class.
     * @param callbacks the callback array
     * @see #setCallbackFilter
     * @see #setCallback
     */
    public void setCallbacks(Callback[] callbacks) {
        if (callbacks != null && callbacks.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        this.callbacks = callbacks;
    }
```
**enhancer.create()**
```
    /**
     * Generate a new class if necessary and uses the specified
     * callbacks (if any) to create a new object instance.
     * Uses the no-arg constructor of the superclass.
     * @return a new instance
     */
    public Object create() {
        //不作代理类限制
        classOnly = false;
        //没有构造参数类型
        argumentTypes = null;
        //执行创建
        return createHelper();
    }
```
Enhancer.createHelper()方法
```
private Object createHelper() {
        //进行验证 并确定CallBack类型 本方法是用的MethodInterceptor
        preValidate();
         //获取当前代理类的标识类Enhancer.EnhancerKey的代理 
        // 生成了Enhancer.EnhancerKey的代理类，也就是我们需要的代理类标识类  用来标识被代理的类，
        // 这个代理类主要用来作为被代理类的标识，在进行缓存时作为判断相等的依据。
        // 可以看到 cglib代理主要也是利用我们传入的被代理类信息来生成对应的代理类字节码(到此已经在class中写入了成员变量  写入实现了newInstance方法  写入无参构造  写入了有参构造)，
        // 然后用类加载器加载到内存中。
        Object key = KEY_FACTORY.newInstance((superclass != null) ? superclass.getName() : null,
                ReflectUtils.getNames(interfaces),
                filter == ALL_ZERO ? null : new WeakCacheKey<CallbackFilter>(filter),
                callbackTypes,
                useFactory,
                interceptDuringConstruction,
                serialVersionUID);
        //设置当前enhancer的代理类的key标识
        this.currentKey = key;
        //调用父类即 AbstractClassGenerator的创建代理类
        Object result = super.create(key);
        return result;
    }
```
preValidate();进行验证 并确定CallBack类型 本方法是用的MethodInterceptor
```
private void preValidate() {
    if (callbackTypes == null) {
        //确定传入的callback类型
        callbackTypes = CallbackInfo.determineTypes(callbacks, false);
        validateCallbackTypes = true;
    }
    if (filter == null) {
        if (callbackTypes.length > 1) {
            throw new IllegalStateException("Multiple callback types possible but no filter specified");
        }
        filter = ALL_ZERO;
    }
}

    //最后是遍历这个数组来确定  本方法是用的MethodInterceptor
    private static final CallbackInfo[] CALLBACKS = {
        new CallbackInfo(NoOp.class, NoOpGenerator.INSTANCE),
        new CallbackInfo(MethodInterceptor.class, MethodInterceptorGenerator.INSTANCE),
        new CallbackInfo(InvocationHandler.class, InvocationHandlerGenerator.INSTANCE),
        new CallbackInfo(LazyLoader.class, LazyLoaderGenerator.INSTANCE),
        new CallbackInfo(Dispatcher.class, DispatcherGenerator.INSTANCE),
        new CallbackInfo(FixedValue.class, FixedValueGenerator.INSTANCE),
        new CallbackInfo(ProxyRefDispatcher.class, DispatcherGenerator.PROXY_REF_INSTANCE),
    };

```
 **super(即AbstractClassGenerator).create(key)**
```
protected Object create(Object key) {
        try {
             //获取到当前生成器的类加载器
            ClassLoader loader = getClassLoader();
            //当前类加载器对应的缓存  缓存key为类加载器，缓存的value为ClassLoaderData  这个类后面会再讲
            Map<ClassLoader, ClassLoaderData> cache = CACHE;
             //先从缓存中获取下当前类加载器所有加载过的类
            ClassLoaderData data = cache.get(loader);
            if (data == null) {
                synchronized (AbstractClassGenerator.class) {
                    cache = CACHE;
                    data = cache.get(loader);
                    //经典的防止并发修改 二次判断
                    if (data == null) {
                        //新建一个缓存Cache  并将之前的缓存Cache的数据添加进来 并将已经被gc回收的数据给清除掉
                        Map<ClassLoader, ClassLoaderData> newCache = new WeakHashMap<ClassLoader, ClassLoaderData>(cache);
                        //新建一个当前加载器对应的ClassLoaderData 并加到缓存中  但ClassLoaderData中此时还没有数据
                        data = new ClassLoaderData(loader);
                        newCache.put(loader, data);
                        //刷新全局缓存
                        CACHE = newCache;
                    }
                }
            }
             //设置一个全局key net.sf.cglib.proxy.Enhancer$EnhancerKey
            this.key = key;
              //在刚创建的data(ClassLoaderData)中调用get方法 并将当前生成器，
             //以及是否使用缓存的标识传进去 系统参数 System.getProperty("cglib.useCache", "true")  
             //返回的是生成好的代理类的class信息
            Object obj = data.get(this, getUseCache());//net.sf.cglib.proxy.Enhancer$EnhancerKey$$KeyFactoryByCGLIB$$7fb24d72
            /**
            public Object get(AbstractClassGenerator gen, boolean useCache) {
                        //如果不用缓存  (默认使用)
                        if (!useCache) {
                            //则直接调用生成器的命令
                          return gen.generate(ClassLoaderData.this);
                        } else {
                          //从缓存中获取值
                          Object cachedValue = generatedClasses.get(gen);
                          //解包装并返回
                          return gen.unwrapCachedValue(cachedValue);
                        }
                    }
             **/
            //如果为class则实例化class并返回  就是我们需要的代理类
            if (obj instanceof Class) {
                return firstInstance((Class) obj);
            }
            //如果不是则说明是实体  则直接执行另一个方法返回实体
            return nextInstance(obj);
        } catch (RuntimeException e) {
            throw e;
        } catch (Error e) {
            throw e;
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }
```


