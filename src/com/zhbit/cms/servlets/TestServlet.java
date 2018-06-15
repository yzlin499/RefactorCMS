package com.zhbit.cms.servlets;


import com.zhbit.cms.infobeans.TermInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class TestServlet {

    @RequestMapping(value = "/test")
    public @ResponseBody String h(@RequestBody List args) {

        return args.toString();
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
