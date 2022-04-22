package com.dwk.weixin.util;

import com.dwk.weixin.bean.*;
import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.weixinutil.AesException;
import com.dwk.weixin.weixinutil.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.*;


/**
 *
 * Description:
 *              消息处理工具类
 * @author: mushi
 * @Date: 2021/1/22 8:58
 */
public class MsgUtil {

    private static String appId;
    private static String token;
    private static String encodingAesKey;
    /**重定义xml格式*/
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**xml消息加密，消息加解密只针对xml格式的消息，即和粉丝交互的消息*/
    public static String encryption(String timestamp, String nonce, String msg, WeiXinConfig weiXinConfig) {
        appId = weiXinConfig.getAppId();
        token = weiXinConfig.getToken();
        encodingAesKey = weiXinConfig.getEncodingAesKey();
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            return pc.encryptMsg(msg,timestamp,nonce);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**xml消息解密*/
    public static String decryption(String msgSignature, String timestamp, String nonce, String originalXml, WeiXinConfig weiXinConfig){
        appId = weiXinConfig.getAppId();
        token = weiXinConfig.getToken();
        encodingAesKey = weiXinConfig.getEncodingAesKey();
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            return pc.decryptMsg(msgSignature,timestamp,nonce,originalXml);
        } catch (AesException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**xml格式数据转map*/
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String,String> map=new HashMap<>();
        SAXReader reader=new SAXReader();
        InputStream inputStream= null;
        try {
            inputStream = request.getInputStream();
            Document doc=reader.read(inputStream);
            //得到根节点
            Element root=doc.getRootElement();
            //根节点下的所有的节点
            List<Element> list=root.elements();
            for(Element e:list){
                map.put(e.getName(),e.getText());
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> xmlToMap(String xmlStr) {

        List<Map<String, String>> list = new ArrayList<>();
        List<Map<String, String>> resultList;
        Map<String, String> retMap = new HashMap<>();

        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            HashMap<String, String> map1 = new HashMap<>();
            //遍历节点
            for (Iterator iter = root.elementIterator(); iter.hasNext();) {
                Element element = (Element) iter.next();
                if (element == null){
                    continue;
                }
                // 获取属性和它的值
                for (Iterator attrs = element.attributeIterator(); attrs.hasNext();) {
                    Attribute attr = (Attribute) attrs.next();
                    System.out.println(attr);
                    if (attr == null){
                        continue;
                    }
                    String attrName = attr.getName();
                    System.out.println("attrname" + attrName);
                    String attrValue = attr.getValue();
                    map1.put(attrName, attrValue);
                }
                // 如果有PCDATA，则直接提出
                if (element.isTextOnly()) {
                    String innerName = element.getName();
                    String innerValue = element.getText();
                    map1.put(innerName, innerValue);
                    list.add(map1);
                }
            }
            resultList = list;
            for (int i = 0; i < resultList.size(); i++) {
                Map map = (Map) resultList.get(i);

                for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    retMap.put(key, (String) map.get(key));

                }
            }
            return retMap;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }



//   ###################################消息类型转xml格式################################################

    /**文本消息转xml*/
    public static String textMsgTurnXml(ReplyTextMsgBean replyTextMsgBean){
        xstream.alias("xml",replyTextMsgBean.getClass());
        return xstream.toXML(replyTextMsgBean);
    }

    /**图片消息转xml*/
    public static String imgMsgTurnXml(ReplyImgMsgBean replyImgMsgBean){
        xstream.alias("xml",replyImgMsgBean.getClass());
        return xstream.toXML(replyImgMsgBean);
    }

    /**语音消息转xml*/
    public static String voiceMsgTurnXml(ReplyVoiceMsgBean replyVoiceMsgBean){
        xstream.alias("xml",replyVoiceMsgBean.getClass());
        return xstream.toXML(replyVoiceMsgBean);
    }

    /**视频消息转xml*/
    public static String videoMsgTurnXml(ReplyVideoMsgBean replyVideoMsgBean){
        xstream.alias("xml",replyVideoMsgBean.getClass());
        return xstream.toXML(replyVideoMsgBean);
    }

    /**音乐消息转xml*/
    public static String musicMsgTurnXml(ReplyMusicMsgBean replyMusicMsgBean){
        xstream.alias("xml",replyMusicMsgBean.getClass());
        return xstream.toXML(replyMusicMsgBean);
    }

    /**图文消息转xml*/
    public static String newsMsgTurnXMl(ReplyNewsMsgBean replyNewsMsgBean){
        xstream.alias("xml",replyNewsMsgBean.getClass());
        return xstream.toXML(replyNewsMsgBean);
    }

}
