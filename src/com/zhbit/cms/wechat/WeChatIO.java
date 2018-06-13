package com.zhbit.cms.wechat;

import com.zhbit.cms.infobeans.wechat.WCInfo;
import com.zhbit.cms.tools.ClassTools;
import com.zhbit.cms.tools.Tools;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

public class WeChatIO<T extends WCInfo> {
    private static final SAXReader saxReader = new SAXReader();
    private static final Logger logger= Logger.getLogger(WeChatIO.class);
    private static final WeChatFromIDPool idPool=WeChatFromIDPool.getInstance();
    private WCInfo wcInfo;
    private PrintWriter printWriter;
    private boolean isNotClose =true;

    public WeChatIO(HttpServletRequest request, HttpServletResponse response){
        try {
            init(Tools.getResultString(request));
            response.setCharacterEncoding("UTF-8");
            printWriter=response.getWriter();
        } catch (IOException e) {
            logger.error(null,e);
        }
    }

    public WeChatIO(String inputXml,PrintWriter printWriter){
        init(inputXml);
        this.printWriter=printWriter;
    }

    private void init(String inputXml){
        try {
//            logger.warn(inputXml);
            Element element = saxReader.read(new ByteArrayInputStream(inputXml.getBytes("UTF-8"))).getRootElement();

            Map<String,String> map=element.elements().stream().collect(Collectors.toMap(Element::getName, Element::getText));
            String type=map.get("MsgType");
            type="WC"+(char)(type.charAt(0)&0xDF)+type.substring(1);
            Class wcI=Class.forName("com.zhbit.cms.infobeans.wechat."+type);
            wcInfo=(WCInfo) ClassTools.instanceByMap(wcI,map);
        } catch (DocumentException |UnsupportedEncodingException e) {
            logger.error(null,e);
        } catch (ClassNotFoundException e) {
            logger.error("未知的类型"+e.getMessage());
        }
    }

    public T getInfo(){
        return (T) wcInfo;
    }

    public void replyNull(){
        reply("");
    }

    public void replyText(String text){
        reply((String.format("<xml><ToUserName><![CDATA[%s]]></ToUserName>" +
                            "<FromUserName><![CDATA[%s]]></FromUserName>" +
                            "<CreateTime>%d</CreateTime>" +
                            "<MsgType><![CDATA[text]]></MsgType>" +
                            "<Content><![CDATA[%s]]></Content></xml>",
                    wcInfo.getFromUserName(), wcInfo.getToUserName(), System.currentTimeMillis() / 1000, text)));
    }

    private void reply(String repXml){
        if(isNotClose) {
            printWriter.print(repXml);
            isNotClose=false;
        }
    }

    public void close(){
        if(isNotClose){
            replyNull();
        }
    }

    public Object setAttribute(Object key,Object value){
        return idPool.setAttribute(wcInfo.getFromUserName(),key,value);
    }

    public Object getAttribute(Object key){
        return idPool.getAttribute(wcInfo.getFromUserName(),key);
    }

    public Map<Object,Object> getAttributeMap(){
        return idPool.getAttributeMap(wcInfo.getFromUserName());
    }

    public String getID(){
        return wcInfo.getFromUserName();
    }

    public Object removeAttribute(Object key){
        return idPool.removeAttribute(wcInfo.getFromUserName(),key);
    }

    public void removeID(){
        idPool.removeID(wcInfo.getFromUserName());
    }
}
