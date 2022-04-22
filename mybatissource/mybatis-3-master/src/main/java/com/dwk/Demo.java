package com.dwk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * 显式调用
 */
public class Demo {

  public void test(){
    try {
      // 遍历一个封装了多个类加载器的数组，用类加载器获取资源   Reader：字符流
      Reader reader = Resources.getResourceAsReader("mybatis.xml");
      // 解析配置文件 构造SqlSession工厂 ---  显式调用构建的是 DefaultSqlSessionFactory
      // 使用建造者模式
      SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
      // 生产一个 sqlSession  数据库会话
      SqlSession sqlSession = factory.openSession();


      //statement:要调用的mapper方法，  parameter:参数
      sqlSession.selectOne("com.dwk.Mapper.findById",1);


      //调用mapper
      Dao dao = sqlSession.getMapper(Dao.class);
      dao.findById("id");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Demo demo = new Demo();
    demo.test();
  }

}
