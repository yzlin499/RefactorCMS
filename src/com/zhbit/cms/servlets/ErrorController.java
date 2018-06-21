package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.tools.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/400")
    public @ResponseBody JSONObject error400(){
        return Tools.quickJSON(400,"错误的参数");
    }

    @RequestMapping("/404")
    public @ResponseBody JSONObject error404(){
        return Tools.quickJSON(404,"页面丢失");
    }

}
