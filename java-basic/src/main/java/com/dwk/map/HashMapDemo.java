package com.dwk.map;

import java.lang.reflect.Field;
import java.util.*;

/**
 * hashmap源码
 */
public class HashMapDemo {

    /**生成相同hashcode的字符串*/
    public void createSameHashCodeStr(){
        while (true) {
            CheckCode checkCode = new CheckCode();
            String checkCode1 = checkCode.getCheckCode(4);
            //指定hashcode的值
            if (checkCode1.hashCode() == 3631410) {
                System.out.println(checkCode1);
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        HashMapDemo hashMapSource = new HashMapDemo();
        hashMapSource.testResize();
        //hashMapSource.testRedBlackTree();
    }


    // 测试红黑树
    public void testRedBlackTree() {
        HashMap<String, String> map = new HashMap<>();
        redBlackTree(map);
        // 插入红黑树
        map.put("vusF", "test");
        // 从红黑树删除
        map.remove("vusF");
    }

    // 构建红黑树
    public void redBlackTree(HashMap map) {
        // hashCode都为 3631410
        int i = 1;
        String[] sameHash = {"x7sF","x95F","wVre","wWSe","vvSe","wVsF","x7re","vw4e","wX5F","wWTF","x8Se","x94e","vvTF"};
        for (String s : Arrays.asList(sameHash)){
            System.out.println("第"+i+"次插入===="+s);
            map.put(s, "test");
            i++;
        }
    }

    // 测试扩容
    public void testResize() throws IllegalAccessException {
        HashMap<Object, Object> map = new HashMap<>();
        System.out.format("\33[32;4m================  创建HashMap时相关全局变量的值  ================%n");
        getThreshold(map);
        map.put(1, "1");
        System.out.format("\33[32;4m##################  执行第1次put后相关全局变量的值  ##############%n");
        getThreshold(map);
        for (int i = 2; i <= 12; i++) {
            map.put(i, "1");
        }
        System.out.format("\33[32;4m##################  执行第12次put后相关全局变量的值  ##############%n");
        getThreshold(map);
        map.put("13", "1");
        System.out.format("\33[32;4m##################  执行第13次put后相关全局变量的值  ##############%n");
        getThreshold(map);
        for (int i = 14; i <= 24; i++) {
            map.put(i, "1");
        }
        map.put("25", "1");
        System.out.format("\33[32;4m##################  执行第25次put后相关全局变量的值  ##############%n");
        getThreshold(map);
    }

    /**反射获取hashmap类的私有变量*/
    public static void getThreshold(HashMap hashMap) throws IllegalAccessException {
        Class<? extends HashMap> aClass = hashMap.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            Class<?> type = field.getType();
            Object o = field.get(hashMap);
            System.out.format("\33[31;4m变量名：\33[34;4m%s  \33[31;4m类型：\33[34;4m%s   \33[31;4m值：\33[34;4m%s%n",name,type,o);
        }
    }

}

/**
 * 用于生成hashcode相同的字符串
 */
class CheckCode {

    /**验证码*/
    private String check = "";
    List<Integer> reUse = new ArrayList<>();

    char[] charCode = {'1','2','3','4','5','6','7','8','9','0','a','b',
            'c','d','e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L',
            'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**num:验证码位数*/
    public String getCheckCode(int num){

        char[] result = new char[num];

        for (int k = 0;k<num;k++){
            int index = new Random().nextInt(charCode.length);
            char code = charCode[index];
            int randow = getRandow(num);
            result[randow] = code;
        }
        for (int i = 0;i<result.length;i++){
            check += String.valueOf(result[i]);
        }

        return check;
    }

    /**排位*/
    public int getRandow(int round){
        int checkNum = new Random().nextInt(round);
        if (!reUse.contains(checkNum)){
            reUse.add(checkNum);
        }else{
            return this.getRandow(round);
        }
        return checkNum;
    }

}

