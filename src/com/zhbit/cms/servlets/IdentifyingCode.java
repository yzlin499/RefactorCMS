package com.zhbit.cms.servlets;

import com.zhbit.cms.tools.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
public class IdentifyingCode {

    @RequestMapping("/identifyingcode")
    public void sendIdentifyingCode(HttpSession httpSession, PrintWriter out){
        String temp[]= Tools.createIdentifyingCode();
        httpSession.setAttribute("identifying_code",Tools.MD5(temp[0].toLowerCase()));
        System.out.println(temp[1]);
        out.print(temp[1]);
    }

}
