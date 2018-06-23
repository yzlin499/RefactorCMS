package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.tools.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admincontrol")
public class Admin {
    private String url;
    private Map<String,AdminOperate> operateMap=new HashMap<>();

    private interface AdminOperate{
        JSONObject operate();
    }

    public Admin(){
        url=Admin.class.getAnnotation(RequestMapping.class).value()[0];
//        operateMap.put("updatePermissions",this::updatePermissions);
    }


    @RequestMapping("/{operate}")
    public @ResponseBody JSONObject doAdmin(@RequestParam("token") String token,
                                            @PathVariable("operate") String operate){
        if(calculateToken(url+'/'+operate).equals(token)){
            return operateMap.containsKey(operate) ?
                    operateMap.get(operate).operate():
                    Tools.quickJSON(404,"没有这个接口");
        }else{
            return Tools.quickJSON(403,"token不对");
        }
    }

    public JSONObject updatePermissions(){
        return null;
    }



    private String calculateToken(String url){
        return Tools.MD5(url+(System.currentTimeMillis()/1000/30)+"p=cms").substring(5,25);
    }
}
