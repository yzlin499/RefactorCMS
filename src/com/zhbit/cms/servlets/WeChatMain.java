package com.zhbit.cms.servlets;

import com.zhbit.cms.infobeans.wechat.SignInfo;
import com.zhbit.cms.tools.Tools;
import com.zhbit.cms.wechat.WeChatEventPool;
import com.zhbit.cms.wechat.WeChatIO;
import com.zhbit.cms.wechat.WeChatJSUtil;
import com.zhbit.cms.wechat.event.WeChatEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/wechat")
public class WeChatMain{
    private static WeChatEventPool weChatEventPool;
    static{
        weChatEventPool=WeChatEventPool.getInstance();
        ApplicationContext ac=new FileSystemXmlApplicationContext("classpath:com/zhbit/cms/wechat/WeChatFunction.xml");
        for (String a:ac.getBeanNamesForType(WeChatEvent.class)) {
            weChatEventPool.addWeChatEvent((WeChatEvent) ac.getBean(a));
        }
    }

    @RequestMapping("/main")
    public void doWeChatMain(HttpServletRequest req, HttpServletResponse resp){
        weChatEventPool.newMsg(new WeChatIO(req,resp));
    }


    private WeChatJSUtil weChatJSUtil=WeChatJSUtil.getInstance();


    @RequestMapping(value = "/jssign",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody SignInfo jsUtilSign(@RequestParam("url") String req) {
        return weChatJSUtil.sign(req);
    }
}
