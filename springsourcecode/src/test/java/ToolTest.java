import com.dwk.bean.AnnotationBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

/**
 * @author duweikun
 * @date 2024/1/18  15:05
 * @description
 */
public class ToolTest {

    Logger logger = LoggerFactory.getLogger(ToolTest.class);

    public BeanDefinition getUserBean(){
        // 定义BeanDefinition
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        // 设置bean的类型
        beanDefinition.setBeanClassName("com.dwk.bean.User");
        // 设置bean的作用域 - prototype：每次请求都会创建一个新的bean
        beanDefinition.setScope("prototype");
        // 给bean赋值
        MutablePropertyValues values = new MutablePropertyValues();
        // add方法等同于addPropertyValue方法
        values.add("name","newbee");
        values.add("age",20);
        beanDefinition.setPropertyValues(values);
        logger.info("bean -- >{}",beanDefinition);
        return beanDefinition;
    }

    /**
     * BeanDefinition使用
     */
    @Test
    public void test1(){
        // 封装在方法内
        getUserBean();
    }

    /**
     * 定义了BeanDefinition后注册到bean注册器
     */
    @Test
    public void test2(){
        BeanDefinition beanDefinition = getUserBean();
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        registry.registerBeanDefinition("user",beanDefinition);
        logger.info("bean注册器：{}",registry);
    }

    /**
     * 父子关系bean构建
     */
    @Test
    public void test3(){
        // 定义注册器
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();

        // 定义父bean
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClassName("com.dwk.bean.Dog");
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("color","black");
        rootBeanDefinition.setPropertyValues(values);
        registry.registerBeanDefinition("parentDog",rootBeanDefinition);
        // 定义子bean,构建时添加父bean的名称
        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("parentDog");
        childBeanDefinition.setBeanClassName("com.dwk.bean.TuDog");
        MutablePropertyValues values1 = new MutablePropertyValues();
        values1.add("name","大黄");
        childBeanDefinition.setPropertyValues(values1);
        registry.registerBeanDefinition("childBean",childBeanDefinition);
        logger.info("registry - > {}",registry);

        // 父子bean最后合并的时候会统一转换成RootBeanDefinition进行操作
    }

    /**
     * 加载BeanDefinition
     */
    @Test
    public void test4(){
        // 1、xml方式   2、注解方式  略过xml，演示注解方式   3、类路径扫描
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
        reader.register(AnnotationBean.class);
        BeanDefinition annotationBean = reader.getRegistry().getBeanDefinition("annotationBean");
        logger.info("registry --> {}",registry);

        // 类路径扫描
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan("com.dwk");
        logger.info("registry --> {}",registry);
    }

}
