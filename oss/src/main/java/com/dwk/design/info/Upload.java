package com.dwk.design.info;

import com.dwk.design.result.Result;

public interface Upload {

    /**上传图片*/
    Result uploadImg(String fileName, String path);

    /**上传文本*/
    Result uploadText(String fileName,String path);

    /**上传视频*/
    Result uploadVideo(String fileName,String path);

    /**上传音频*/
    Result uploadVoice(String fileName,String path);

}
