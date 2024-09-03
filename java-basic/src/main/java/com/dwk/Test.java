package com.dwk;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Test {

    public static void main(String[] args) {
//        dayMath();
        //finallyTest();
        System.out.println(isPhone("18877776262"));
    }

    /**
     * 获取某月的每一天
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthDays(int year, int month) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i <= maxDay; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            Date date = calendar.getTime();
            days.add(sdf.format(date));
        }
        return days;
    }

    // zip压缩包修复
    public static void repairCorruptedZip(String inputZipFile, String outputZipFile) {
        // 创建一个新的ZIP文件输出流
        try (ZipFile zipFile = new ZipFile(new File(inputZipFile));
             OutputStream out = new FileOutputStream(outputZipFile)) {

            // 遍历ZIP文件中的所有条目
            Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                try {
                    // 读取条目内容并写入新的ZIP文件
                    InputStream in = zipFile.getInputStream(entry);
                    out.write(IOUtils.toByteArray(in));
                } catch (IOException e) {
                    System.err.println("Error processing entry: " + entry.getName());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 解压缩zip文件
    public static void unzip(String zipFilePath, String destDirectory) {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                fos.write(bytesIn, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean isPhone(String str){
        boolean result = false;
        String rule = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        if (str.matches(rule)){
            result = true;
        }
        return result;
    }

    /**
     * 当前时间加减天数
     */
    public static void dayMath(){
        LocalDateTime now = LocalDateTime.now();
        String format = now.minusDays(180).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
    }

    /**
     * 不管try和catch里怎么执行   finally最后一定会被执行
     */
    static void finallyTest(){
        try{
            System.out.println("执行try");
            throw new RuntimeException("异常");
        }catch (Exception e){
            System.out.println("catch执行"+e.getMessage());
            return;
        }finally {
            System.out.println("finally 执行了");
        }
    }

    /**
     * String  final修饰  对象不可变 每次对String对象操作相当于操作一个新对象
     * StringBuffer  可变  线程安全   效率慢  所有的方法都用synchronized修饰
     * StringBuilder 可变  线程不安全  效率高
     */
    static void stringStringBufferStringBuilderTest(){
        String s = "";
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
    }

    /**
     * this 本质 指向本对象的指针
     * super  本质  关键字  super()必须写在子类构造方法第一行
     * 二者都指向对象
     */

    /**
     * 定义泛型方法
     */
    public <T> T getObjectByClassName(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        T t = clazz.newInstance();
        return t;
    }

    /**
     * try中打开资源  try执行完成或者catch后try中的资源会自动关闭
     */
    static void tryWithResource(){
        try(FileInputStream inputStream = new FileInputStream(new File(""));){

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
