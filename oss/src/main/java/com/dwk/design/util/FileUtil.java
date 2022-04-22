package com.dwk.design.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.dwk.design.config.AliConfig;
import com.dwk.design.result.Result;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *          上传下载文件工具类
 * @author: mushi
 * @Date: 2021/2/2 13:30
 */
public class FileUtil {
    private static volatile FileUtil fileUtil = null;

    private String bucket;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private List<String> fileNameList;

    private FileUtil(){

    }

    public static FileUtil getFileUtil() {
        if (fileUtil == null){
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    /***
     * 上传文件
     */
    public Result upLoad(AliConfig aliConfig , String fileName , File file , int i){

        bucket = aliConfig.getBucketName().get(i);
        endpoint = aliConfig.getEndpoint();
        accessKeyId = aliConfig.getAccessKeyId();
        accessKeySecret = aliConfig.getAccessKeySecret();
        fileNameList = new ArrayList<>();

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (ossClient.doesBucketExist(bucket)){
            //上传文件
            ossClient.putObject(bucket,fileName,file);

            ObjectListing objectListing = ossClient.listObjects(bucket);
            List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();

            for (OSSObjectSummary obs:objectSummaries){
                fileNameList.add(obs.getKey());
            }

            if (fileNameList.contains(fileName)){
                //文件下载路径，存入数据库和缓存
                URL url = ossClient.generatePresignedUrl(bucket, fileName,new Date(System.currentTimeMillis()+3600L * 1000 * 24 * 365 * 1000));
                ossClient.shutdown();
                return Result.success("下载链接："+url);
            }else{
                return Result.fail("1000","上传失败");
            }

        }else{
            System.out.println("创建"+bucket+"Bucket");
            ossClient.createBucket(bucket);
            this.upLoad(aliConfig,fileName,file,i);
        }

        return Result.success();
    }


    /**
     * 下载文件
     */
    public Result downLoad(){

        return Result.success();
    }


}
