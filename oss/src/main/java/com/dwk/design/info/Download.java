package com.dwk.design.info;


public interface Download {

    /**下载图片*/
    String downloadImg(String url ,String path);

    /**下载文本*/
    String downloadText(String url ,String path);

    /**下载视频*/
    String downloadVideo(String url ,String path);

    /**下载音频*/
    String downloadVoice(String url ,String path);
    
}
