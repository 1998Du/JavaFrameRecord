#  Reader reader = Resources.getResourceAsReader("mybatis.xml");
1.Reader是一个用来读取字符流的抽象类

2.通过Resources.getResourceAsReader()方法将配置文件mybatis.xml加载到一个Reader对象中

3.Resources类中创建了类加载器装载器（ClassLoaderWrapper）的全局静态变量。

ClassLoaderWrapper中封装了五个类加载器，分别有：
一个可定义的ClassLoader；
默认的类加载器ClassLoader；
Thread.currentThread().getContextClassLoader()；
getClass().getClassLoader()；
ClassLoader.getSystemClassLoader()
==源码：==
```
ClassLoader defaultClassLoader;
ClassLoader systemClassLoader;

ClassLoaderWrapper() {
    try {
      systemClassLoader = ClassLoader.getSystemClassLoader();
    } catch (SecurityException ignored) {
      // AccessControlException on Google App Engine
    }
  }
...省略部分代码...
ClassLoader[] getClassLoaders(ClassLoader classLoader) {
    return new ClassLoader[]{
        classLoader,
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        systemClassLoader};
  }
```
4、getResourcesAsReader()方法：
==源码：==

```java
public static Reader getResourceAsReader(String resource) throws IOException {
    Reader reader;

    if (charset == null) {
      reader = new InputStreamReader(getResourceAsStream(resource));
    } else {
      reader = new InputStreamReader(getResourceAsStream(resource), charset);
    }
    return reader;
  }
```
代码中的charset是一个静态的全局变量，当它为null时表示使用系统默认的编码；此处使用InputStreamReader来加载资源并用Reader对象接收。
5、getResourceAsStream(resource)方法：
==源码：==

```java
public static InputStream getResourceAsStream(String resource) throws IOException {
    return getResourceAsStream(null, resource);
  }
```
继续getResourceAsStream(null,resource)源码

```java
public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
    InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
    if (in == null) {
      throw new IOException("Could not find resource " + resource);
    }
    return in;
  }
```
调用此方法时，传入的classLoader为null，也就是说不向类加载器装载器中添加一个自定义的类加载器。方法中，调用classLoaderWrapper的getResourceAsStream来加载资源文件，并用InputStream来接收
6、ClassLoaderWrapper.getResourceAsStream()方法：
==源码：==

```java
public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
    return getResourceAsStream(resource, getClassLoaders(classLoader));
  }
```
先看getClassLoaders(classLoader)源码：

```java
 ClassLoader[] getClassLoaders(ClassLoader classLoader) {
    return new ClassLoader[]{
        classLoader,
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        systemClassLoader};
  }
```
**这就是ClassLoaderWrapper类加载器装载器的庐山真面目了**

再继续看getResourceAsStream(resource, getClassLoaders(classLoader))源码

```java
InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
    for (ClassLoader cl : classLoader) {
      if (null != cl) {

        // try to find the resource as passed
        InputStream returnValue = cl.getResourceAsStream(resource);

        // now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
        if (null == returnValue) {
          returnValue = cl.getResourceAsStream("/" + resource);
        }

        if (null != returnValue) {
          return returnValue;
        }
      }
    }
    return null;
  }
```
这个方法中，对类加载器装载器进行遍历，如果类加载器不为null就使用类加载器中的getResourceAsStream方法去读取资源并放到InputStream中，又因为有的类加载器需要资源前面带有 "/" 才将其认定为资源进行加载，所以第二个if判断中给resource前加了一个 "/"，最后如果所有类加载器都遍历完成还是没有找到资源的话返回null，否则返回装载了资源的InputStream流。


==总结==：本质就是使用一个类加载器数组去请求资源，最后以流的方式返回，然后使用Reader接收。
