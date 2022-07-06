package com.dwk.sftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 连接远程服务器工具类
 */
public class SftpUtil {

    private static final Logger log = LoggerFactory.getLogger(SftpUtil.class);

    /**消息通道*/
    private static ChannelSftp sftp;
    /**连接客户端*/
    private static JSch jSch;
    /**会话*/
    private static Session session;
    /**ip*/
    private static String host;
    /**端口*/
    private static int port;
    /**用户名*/
    private static String username;
    /**密码*/
    private static String password;

    public static void setAttr(String host, int port, String username, String password){
        SftpUtil.host = host;
        SftpUtil.port = port;
        SftpUtil.username = username;
        SftpUtil.password = password;
        jSch = new JSch();
    }

    /**
     * 连接指定服务器
     */
    public static void login(){
        try {
            session = jSch.getSession(username,host,port);
            session.setPassword(password);
            //设置连接方式，用密码连接
            session.setConfig("PreferredAuthentications","password");
            session.setConfig("StrictHostKeyChecking","no");
            session.connect();
            //建立sftp通信通道
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("服务器:"+host+"连接成功");
        } catch (JSchException e) {
            System.out.println("服务器:"+host+"连接失败");
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public static void shutdown(){
        //关闭通信通道
        if (sftp != null){
            if(sftp.isConnected()){
                sftp.disconnect();
                System.out.println("sftp is close already");
            }
        }
        //关闭会话
        if (session != null){
            if(session.isConnected()){
                session.disconnect();
                System.out.println("session is close already");
            }
        }
    }

    /**
     * 执行命令
     */
    public static String runShell(String cmd){
        String returnStr = null;
        try {
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(cmd);
            channelExec.connect();

            StringBuilder resultLog = new StringBuilder("");
            BufferedReader resultReader = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            String resultLine = null;
            while ((resultLine = resultReader.readLine()) != null){
                resultLog.append(resultLine).append("\n");
            }
            returnStr = resultLog.toString();

            StringBuilder errLog = new StringBuilder("");
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(channelExec.getErrStream()));
            String errorLine = null;
            while ((errorLine = errorReader.readLine()) != null){
                errLog.append(errorLine).append("\n");
            }

        } catch (JSchException e) {
            log.error("shell命令执行错误");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return returnStr;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(String path, String fileName, InputStream inputStream){
        long start = System.currentTimeMillis();
        boolean returnBoolean = true;
        try {
            if (sftp.ls(path) == null){
                sftp.mkdir(path);
            }
            if (sftp.ls(path) != null){
                sftp.cd(path);
            }
            sftp.put(inputStream,fileName);
        } catch (SftpException e) {
            System.out.println("文件保存失败");
            e.printStackTrace();
            returnBoolean = false;
        }
        long end = System.currentTimeMillis();
        System.out.println("文件"+fileName+"保存成功,耗时"+(end-start));
        return returnBoolean;
    }

    /**
     * 删除文件
     */
    public static void deleteFile(){
        try {
            sftp.rm("");
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**上传文件*/
    public static void uploadFile(String sourcePath,String targetPath){}

    /**下载文件*/
    public static void downloadFile(String targetPath,String savePath){}

}
