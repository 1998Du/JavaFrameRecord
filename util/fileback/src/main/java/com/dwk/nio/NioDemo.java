package com.dwk.nio;

import com.dwk.properties.PathConfigProperties;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.List;

/**
 * nio 案例实现
 */
@Logger
@Service
public class NioDemo {

    private static final String SOURCE_IS_NULL = "源路径不能为空！";
    private static final String TARGET_IS_NULL = "目标路径不能为空！";
    private static final String CORN_IS_NULL = "定时单位不能为空！";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NioDemo.class);

    @Autowired
    private PathConfigProperties properties;

    @Scheduled(cron = "${filepath.cron}")
    public void fileChannelWriteAndReadTest() {
        if (properties.isOpen()) {
            logger.error(LocalDateTime.now() + "开始备份...");
            //判断配置是否配置
            List<String> sourcePaths = properties.getSourcePath();
            String targetPath = properties.getTargetPath();
            String corn = properties.getCron();
            if (sourcePaths.size() == 0 || sourcePaths == null) {
                throw new RuntimeException(SOURCE_IS_NULL);
            }
            Assert.notNull(targetPath, TARGET_IS_NULL);
            Assert.notNull(corn, CORN_IS_NULL);
            //开始备份
            sourcePaths.stream().forEach(sourcePath -> {
                logger.info("【" + sourcePath + "】==开始备份到==" + "【" + properties.getTargetPath() + "】");
                if (copy(sourcePath)) {
                    logger.info("文件：" + sourcePath + "备份成功！");
                } else {
                    logger.info("文件：" + sourcePath + "备份失败！等待下次备份");
                }
            });
        }
    }

    /**
     * NIO方式备份
     */
    public boolean copy(String sourcePath) {
        boolean returnBoolean = false;
        //判断要备份的是文件还是文件夹
        boolean isFile = new File(sourcePath).isFile();
        if (isFile) {
            //文件
            returnBoolean = fileBack(sourcePath,properties.getTargetPath());
        } else {
            //目录
            File targetDir = new File(properties.getTargetPath(), new File(sourcePath).getName());
            boolean targetDirExists = targetDir.exists();
            if (targetDirExists){
                targetDir.delete();
            }
            returnBoolean = dirBack(sourcePath,properties.getTargetPath());
        }
        return returnBoolean;
    }

    /**
     * 文件备份
     */
    public boolean fileBack(String sourcePath,String targetPath) {
        boolean returnBoolean = false;
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        ByteBuffer byteBuffer = null;
        //文件
        try {
            //组织备份文件名
            String sourceFileName = new File(sourcePath).getName();
            String targetFileName;
            if (sourcePath.contains("\\")) {
                String sourceFileType = sourcePath.substring(sourcePath.lastIndexOf("."));
                targetFileName = targetPath + "\\" + sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + sourceFileType;
            } else {
                String sourceFileType = sourcePath.substring(sourcePath.lastIndexOf("."));
                targetFileName = targetPath + sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + sourceFileType;
            }
            Assert.notNull(targetFileName, "备份文件名生成失败!");
            File fileClone = new File(targetFileName);
            if (fileClone.exists()) {
                fileClone.delete();
                fileChannelWriteAndReadTest();
            }
            File file = new File(sourcePath);
            inputStream = new FileInputStream(file);
            //文件流读通道
            FileChannel inChannel = inputStream.getChannel();
            //文件流写通道
            outputStream = new FileOutputStream(fileClone);
            FileChannel outChannel = outputStream.getChannel();
            //通道中的数据流写入到缓冲区
            byteBuffer = ByteBuffer.allocate(1024);
            while (true) {
                //不确定文件大小，每次读取清空缓冲区 否则可能造成数据错误
                byteBuffer.clear();
                int read = inChannel.read(byteBuffer);
                if (read == -1) {
                    break;
                }
                byteBuffer.flip();
                //将缓冲区中的数据流写入到另一个通道
                outChannel.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnBoolean;
    }

    /**
     * 文件夹备份
     */
    public boolean dirBack(String sourcePath,String targetPath) {
        boolean returnBoolean = false;
        File[] files = new File(sourcePath).listFiles();
        String newTargetDirName = null;
        for (File file : files) {
            if (file.isDirectory()) {
                //目标路径下创建该文件夹
                newTargetDirName = targetPath + "\\" + file.getName();
                new File(newTargetDirName).mkdir();
                dirBack(file.getPath(),newTargetDirName);
            } else {
                String path = file.getPath();
                if (fileBack(path,targetPath)){
                    returnBoolean = true;
                }
            }
        }
        return returnBoolean;
    }

}
