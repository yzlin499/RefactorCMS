package com.zhbit.cms.servlets;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestServlet {


    @RequestMapping(value = "/test")
    public @ResponseBody String h() {
        return "asdasdsads";
    }

}
