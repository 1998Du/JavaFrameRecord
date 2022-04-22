package com.dwk.design.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *              请求url工具类
 * @author: mushi
 * @Date: 2021/1/28 11:21
 */
public class UrlUtil {

    /**GET请求url接收响应体*/
    public Object requestUrl(String urlstr){

        BufferedReader read = null;
        String result = "";
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "UTF-8");
            connection.connect();
            read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = read.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    /**POST请求携带json数据请求url*/
    public String requestUrlWithJson(Object json,String urlstr) throws IOException {

        //携带data请求urlstr
        URL url = new URL(urlstr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "UTF-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        PrintWriter printWriter;
        printWriter = new PrintWriter(connection.getOutputStream());
        printWriter.print(json);
        printWriter.flush();

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = "";
        List<String> list = new ArrayList<>();
        while ((str = bufferedReader.readLine()) != null){
            list.add(str);
        }
        inputStream.close();
        connection.disconnect();

        return "success";
    }


}
