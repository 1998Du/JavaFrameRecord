package com.dwk.design.service;

import com.dwk.design.info.Upload;
import com.dwk.design.config.AliConfig;
import com.dwk.design.result.ErrorType;
import com.dwk.design.result.Result;
import com.dwk.design.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

/**
 * Description:
 *              上传文件
 * @author: mushi
 * @Date: 2021/2/2 9:53
 */
@Service
public class UploadService implements Upload {

    /**oss存储空间*/
    private static final Integer IMG_BUCKET = 0;
    private static final Integer TXT_BUCKET = 1;
    private static final Integer VIDEO_BUCKET = 2;
    private static final Integer VOICE_BUCKET = 3;

    @Autowired
    private AliConfig aliConfig;

    private FileUtil fileUtil;

    private File file;

    /**上传图片，上传的文件名=用户文件名+uuid*/
    @Override
    public Result uploadImg(String fileName, String path){
        file = new File(path);
        if (!file.exists()){
            return Result.fail(ErrorType.FILE_NOT_EXIT);
        }
        fileName=fileName+"-"+UUID.randomUUID().toString();
        this.fileUtil = FileUtil.getFileUtil();
        return fileUtil.upLoad(aliConfig,fileName,file,IMG_BUCKET);
    }

    /**上传文本文件*/
    @Override
    public Result uploadText(String fileName, String path){

        file = new File(path);
        if (!file.exists()){
            return Result.fail(ErrorType.FILE_NOT_EXIT);
        }
        fileName=fileName+"-"+UUID.randomUUID().toString();
        this.fileUtil = FileUtil.getFileUtil();
        return fileUtil.upLoad(aliConfig,fileName,file,TXT_BUCKET);
    }

    /**上传视频*/
    @Override
    public Result uploadVideo(String fileName, String path){

        file = new File(path);
        if (!file.exists()){
            return Result.fail(ErrorType.FILE_NOT_EXIT);
        }
        fileName=fileName+"-"+UUID.randomUUID().toString();
        this.fileUtil = FileUtil.getFileUtil();
        return fileUtil.upLoad(aliConfig,fileName,file,VIDEO_BUCKET);
    }

    /**上传音频*/
    @Override
    public Result uploadVoice(String fileName, String path){

        file = new File(path);
        if (!file.exists()){
            return Result.fail(ErrorType.FILE_NOT_EXIT);
        }
        fileName=fileName+"-"+UUID.randomUUID().toString();
        this.fileUtil = FileUtil.getFileUtil();
        return fileUtil.upLoad(aliConfig,fileName,file,VOICE_BUCKET);
    }


}
