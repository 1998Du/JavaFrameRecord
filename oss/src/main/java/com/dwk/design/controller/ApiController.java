package com.dwk.design.controller;

import com.dwk.design.bean.FileBean;
import com.dwk.design.result.Result;
import com.dwk.design.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Description:
 *          提供上传下载文件的api
 * @author: mushi
 * @Date: 2021/2/2 10:34
 */
@Controller
public class ApiController {

    @Autowired
    private UploadService uploadService;

    /**
     * 本地上传
     * @param fileBean
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Result uploadFile(@RequestBody FileBean fileBean){
        //不同类型的文件上传到不同给的Bucket
        String fileName = fileBean.getFileName();
        String path = fileBean.getPath();
        String type = fileBean.getType();
        switch (type){
            case "img":
                return uploadService.uploadImg(fileName,path);
            case "text":
                return uploadService.uploadText(fileName,path);
            case "video":
                return uploadService.uploadVideo(fileName,path);
            case "voice":
                return uploadService.uploadVoice(fileName,path);
        }
        return Result.success();
    }

    @RequestMapping("/downloadFile")
    public String downloadFile(){

        return "";
    }



}
