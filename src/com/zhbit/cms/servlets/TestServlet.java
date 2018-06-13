package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.infobeans.TermInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class TestServlet {

    @RequestMapping(value = "/test/{id:new|modify|delete}")
    public @ResponseBody String h(@RequestBody JSONObject termInfo) {

        return "asdas";
//        for(Object a:termInfo){
//            System.out.println(a);
//        }
//
//        TermInfo termInfo1=new TermInfo();
//        termInfo1.setTermID(123);
//        termInfo1.setTermName("456456");
//        return (JSONObject) JSONObject.toJSON(termInfo1);
    }

}
