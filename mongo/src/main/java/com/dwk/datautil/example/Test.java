package com.dwk.datautil.example;

import com.dwk.datautil.util.MongoDocUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/1 14:07
 */
public class Test {

    public static void main(String[] args) {
        MongoDocUtil mongoDocUtil = new MongoDocUtil();

        //key：数据库集合名   value：单独拥有的索引集合
        Map<String, List<String>> aloneIndex = mongoDocUtil.findAloneIndex();

        //查找相同的索引
        List<String> sameIndex = mongoDocUtil.findSameIndex();
        Iterator<String> iterator = sameIndex.iterator();
        int i = 0;
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            i++;
        }
        System.out.println("有"+i+"条相同索引的数据");

        //通过字符串比对方式查找索引相同内容不同的数据
        List<String> diffDocByStr = mongoDocUtil.getDiffDocByStr();
        Iterator<String> iteratorByStr = diffDocByStr.iterator();
        int j = 0;
        while (iteratorByStr.hasNext()){
            System.out.println(iteratorByStr.next());
            j++;
        }
        System.out.println("共有"+j+"条相同索引不同内容的数据");

        //通过对象属性比对方式查找索引相同内同不同的数据
        List<String> diffDocByObj = mongoDocUtil.getDiffDocByObj();
        Iterator<String> iteratorByObj = diffDocByObj.iterator();
        int k = 0;
        while (iteratorByObj.hasNext()){
            System.out.println(iteratorByObj.next());
            k++;
        }
        System.out.println("共有"+k+"条相同索引不同内容的数据");

    }

}
