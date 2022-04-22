package com.dwk.design.service;

import com.dwk.design.info.Download;
import com.dwk.design.config.AliConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *              下载文件
 * @author: mushi
 * @Date: 2021/2/2 9:54
 */
@Service
public class DownloadService implements Download {

    @Autowired
    private AliConfig config;

    /**判断要下载的文件的类型，调用不同方法进行下载，下载位置由用户决定*/

    @Override
    public String downloadImg(String url ,String path) {
        return null;
    }

    @Override
    public String downloadText(String url ,String path) {
        return null;
    }

    @Override
    public String downloadVideo(String url ,String path) {
        return null;
    }

    @Override
    public String downloadVoice(String url ,String path) {
        return null;
    }
}
