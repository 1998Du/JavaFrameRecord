package com.dwk.datautil.service;

import com.alibaba.fastjson.JSONObject;
import com.dwk.datautil.info.CollectionService;
import com.dwk.datautil.util.CompareObjectUtil;
import com.dwk.datautil.util.ReflectUtil;
import com.dwk.datautil.config.MongoConfig;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/1 13:58
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    private static final String QUERYNAME = "queryName";
    private static final String VERSION = "version";

    @Autowired
    private MongoConfig mongoConfig;


    /**
     * 获取所有collection
     * @return  List<MongoCollection>
     */
    public List<MongoCollection> getCollection(){
        List<MongoCollection> collections = new ArrayList<>();
        Map<String, MongoDatabase> dbMap = mongoConfig.databaseMap();
        List<String> collectionList = mongoConfig.getCollection();
        List<String> dataBaseList = mongoConfig.getDataBase();

        for (int i = 0 ;i < dataBaseList.size() ; i++) {
            MongoDatabase database = dbMap.get(dataBaseList.get(i));
            String collectionName = collectionList.get(i);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collections.add(collection);
        }
        return collections;
    }


    /**
     * 存储文档中索引  queryName:version
     * @return map{key = collection.getNamespace,value = List<index>}
     */
    public Map<String,List<String>> getAllIndex(){
        Map<String,List<String>> indexMap = new HashMap<>();
        List<MongoCollection> collections = getCollection();
        for (MongoCollection collection : collections){
            List<String> indexs = new ArrayList<>();
            FindIterable<Document> documents = collection.find();
            MongoCursor<Document> iterator = documents.iterator();
            while (iterator.hasNext()){
                Document next = iterator.next();
                JSONObject docJson = JSONObject.parseObject(next.toJson());
                String index,queryName,version;
                //index = queryName:version
                if(docJson.containsKey(QUERYNAME)){
                    queryName = docJson.get(QUERYNAME).toString();
                }else{
                    queryName = "null";
                }
                if (docJson.containsKey(VERSION)){
                    version = docJson.get(VERSION).toString();
                }else{
                    version = "null";
                }
                index = queryName+":"+version;
                indexs.add(index);
            }
            indexMap.put(collection.getNamespace().toString(),indexs);
        }
        return indexMap;
    }


    /**
     * 将所有索引去重
     * @param map
     * @return  set
     */
    public Set<String> getAllIndexSet(Map<String,List<String>> map){
        Set<String> indexSet = new HashSet<>();
        for (List<String> indexs : map.values()){
            for (String index : indexs){
                indexSet.add(index);
            }
        }
        return indexSet;
    }


    @Override
    public Map<String,List<String>> findAloneIndex() {
        Map<String,List<String>> aloneDocMap =  new HashMap<>();
        Map<String, List<String>> allIndexMap = getAllIndex();
        Set<String> indexSet = getAllIndexSet(allIndexMap);

        int mapIndex = 0;

        for (List<String> indexs : allIndexMap.values()){
            Set<String> indexSetClone = (Set<String>) ((HashSet<String>) indexSet).clone();
            Map<String, List<String>> mapClone = new HashMap<>();
            mapClone.putAll(allIndexMap);
            String collectionName = (String) allIndexMap.keySet().toArray()[mapIndex];
            //除了indexs外移除其他全部
            mapClone.values().remove(indexs);
            Iterator<List<String>> mapCloneValuesIterator = mapClone.values().iterator();
            while (mapCloneValuesIterator.hasNext()){
                indexSetClone.removeAll(mapCloneValuesIterator.next());
            }
            Iterator<String> indexSetCloneIterator = indexSetClone.iterator();
            List<String> indexList = new ArrayList<>();
            while (indexSetCloneIterator.hasNext()){
                indexList.add(indexSetCloneIterator.next());
            }
            aloneDocMap.put(collectionName, indexList);
            System.out.println(collectionName+"单独拥有的索引条数："+indexSetClone.size());
            mapIndex++;
        }

        return aloneDocMap;
    }

    @Override
    public List<String> findSameIndex() {
        List<String> sameIndexList = new ArrayList<>();
        Map<String, List<String>> allIndexMap = getAllIndex();
        Set<String> allIndexSet = getAllIndexSet(allIndexMap);
        Map<String, List<String>> aloneIndexMap = findAloneIndex();
        Iterator<List<String>> aloneIndexMapValuesIterator = aloneIndexMap.values().iterator();

        //set集合去除每个collection中独有的索引
        while (aloneIndexMapValuesIterator.hasNext()){
            List<String> indexList = aloneIndexMapValuesIterator.next();
            allIndexSet.removeAll(indexList);
        }

        Iterator<String> allIndexSetIterator = allIndexSet.iterator();
        Iterator<List<String>> allIndexMapIterator = allIndexMap.values().iterator();
        boolean same = true;

        while (allIndexSetIterator.hasNext()){
            String index = allIndexSetIterator.next();
            while (allIndexMapIterator.hasNext()){
                List<String> next = allIndexMapIterator.next();
                boolean b = next.contains(index);
                if (b == false){
                    same = false;
                }
                continue;
            }
            if (same){
                sameIndexList.add(index);
            }
        }
        return sameIndexList;
    }

    @Override
    public List<String> getDiffDocByString(){
        List<String> diffDocList = new ArrayList<>();
        List<String> sameIndexs = findSameIndex();
        List<MongoCollection> collections = getCollection();
        List<String> docList = new ArrayList<>();
        Document query;

        for (String index : sameIndexs){
            docList.removeAll(docList);
            String[] split = index.split(":");
            query = new Document("queryName", split[0]).append("version", split[1]);
//            System.out.print("判断索引为"+index+"的文档===========>");
            for (MongoCollection collection : collections){
                collection.find(query).forEach(
                        (Block<Document>)document->{
                            JSONObject docJson = JSONObject.parseObject(document.toJson());
                            if (docJson.containsKey("_id")){
                                docJson.remove("_id");
                                docList.add(docJson.toJSONString());
                            }
                        }
                );
            }
            //判断查询到的文档是否相同
            if (docList.size() > 1){
                boolean isSame = true;
                String s = docList.get(0);
                docList.remove(0);
                for (String doc : docList){
                    if (!s.equals(doc)){
                        s = doc;
                        isSame = false;
                    }
                }
                if (isSame){
//                    System.out.println("SAME\n");
                }else{
//                    System.out.println("不相同\n");
                    diffDocList.add(index);
                }
            }
        }
        return diffDocList;
    }

    @Override
    public List<String> getDiffDocByProperty(){
        List<String> diffDocList = new ArrayList<>();
        List<String> sameIndexs = findSameIndex();
        List<MongoCollection> collections = getCollection();
        List<Object> docObjectList = new ArrayList<>();
        ReflectUtil reflectUtil = new ReflectUtil();
        Document query;

        for (String index : sameIndexs) {
            docObjectList.removeAll(docObjectList);
            String[] split = index.split(":");
            query = new Document("queryName", split[0]).append("version", split[1]);
//            System.out.println("判断索引为" + index + "的文档是否相同....");
            for (MongoCollection collection : collections) {
                collection.find(query).forEach(
                        (Block<Document>)document->{
                            JSONObject docJson = JSONObject.parseObject(document.toJson());
                            if (docJson.containsKey("_class")){
                                String className = docJson.get("_class").toString();
                                try {
                                    Class<?> docClass = Class.forName(className);
                                    //反射初始化对象
                                    Object docObject = reflectUtil.initObject(docClass, document.toJson());
                                    docObjectList.add(docObject);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
            //比较根据文档反射得到的对象的所有属性
            String diffIndex = compareDocObject(index, docObjectList);
            if (diffIndex !=null){
                diffDocList.add(diffIndex);
            }
        }
        return diffDocList;
    }


    /**
     * 比较根据文档反射得到的对象的异同
     */
    private String compareDocObject(String index, List<Object> docObjectList){
        Object oldObj = docObjectList.get(0);
        docObjectList.remove(0);
        CompareObjectUtil compareObjectUtil = new CompareObjectUtil();
        for (Object docObject : docObjectList){
            //比对对象
            if (compareObjectUtil.isSame(oldObj,docObject)){
                oldObj = docObject;
                System.out.println("🛸共存⇠⇠⇠⇠⇠内容一致："+index);
            }else{
                return "🏍共存⇢⇢⇢⇢⇢内容不一致："+index;
            }
        }
        return null;
    }

}
