package com.dwk.datautil.util;

import com.dwk.datautil.MongoDataUtilApp;
import com.dwk.datautil.service.CollectionServiceImpl;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/12 15:37
 */
public class MongoDocUtil {

    static {
        SpringApplication.run(MongoDataUtilApp.class);
    }

    private CollectionServiceImpl collectionService = SpringContxtUtil.getBeanByClass(CollectionServiceImpl.class);

    /**
     * 测试findSameIndex方法（共有索引）
     */
    public List<String> findSameIndex(){
        return collectionService.findSameIndex();
    }


    /**
     * 测试findAloneIndex方法（单独索引）
     */
    public Map<String, List<String>> findAloneIndex(){
        return collectionService.findAloneIndex();
//        Iterator<List<String>> iterator = aloneIndex.values().iterator();
//        while (iterator.hasNext()){
//            int i = 0;
//            List<String> indexList = iterator.next();
//            Iterator<String> indexListIterator = indexList.iterator();
//            while (indexListIterator.hasNext()){
//                i++;
//                System.out.println(indexListIterator.next());
//            }
//            System.out.println("****************************单独拥有"+i+"*****************************");
//        }
    }


    /**
     * 连接mongo测试
     */
    public void connectTest(String ip, Integer port){
        try{
            MongoClient mongoClient = new MongoClient( ip , port );
            MongoDatabase mongoDatabase = mongoClient.getDatabase("datatrust");
            System.out.println("Connect to database successfully");
        }catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**高亮文本对比工具类测试*/
    public void highLightTextCompare(String s1, String s2){
        DiffMatchPatch df = new DiffMatchPatch();
        String htmlDiffString = df.getHtmlDiffString(s1, s2);
        System.out.println(htmlDiffString);
    }


    /**
     * 对比mongo文档异同（通过字符串）
     */
    public List<String> getDiffDocByStr(){
        return collectionService.getDiffDocByString();
    }


    /**
     * 对比mongo文档异同（通过反射的对象属性）
     */
    public List<String> getDiffDocByObj(){
        return collectionService.getDiffDocByProperty();
    }

}
