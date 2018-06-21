package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.frameclass.SelectOperate;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.tools.ClassTools;
import com.zhbit.cms.tools.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Select{
    static{
        initFunction();
    }
    private static HashMap<String,SelectOperate> operateMaps;
    private static final SqlSessionManagement sqls=SqlSessionManagement.getInstance();

    @RequestMapping("/select/{kind}")
    public @ResponseBody
    JSONObject doFilter(@PathVariable("kind")String kind,
                        @RequestBody String[] param){
        if(param.length==0){
            return operateMaps.containsKey(kind) ?
                    dealData("*",operateMaps.get(kind)) :
                    Tools.quickJSON(StatusCode.NO_FIND,"不支持的功能");
        }else{
            SelectOperate selectOperate=operateMaps.get(kind);
            for(String p:param){
                if(!selectOperate.filterWord(p)){
                    return Tools.quickJSON(StatusCode.PARAM_LACK, "参数错误");
                }
            }
            return operateMaps.containsKey(kind) ?
                    dealData(String.join(",",param),selectOperate) :
                    Tools.quickJSON(StatusCode.NO_FIND,"不支持的功能");
        }
    }

    private JSONObject dealData(String param,SelectOperate selectOperate){
        List<Map<String, ?>> l = sqls.customSqlSession(s -> {
            try {
                return selectOperate.selectOperate(param, s);
            } catch (CMSException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        });
        return Tools.quickJSON(StatusCode.COMPLETE, StatusCode.SUCCESS)
                .fluentPut("data", JSON.toJSON(l));
    }

    private static void initFunction(){
        operateMaps=new HashMap<>();
        ClassTools.getClasses("com.zhbit.cms.control.select").stream()
                .filter(SelectOperate.class::isAssignableFrom)
                .forEach(d->{
                    try {
                        String name=d.getSimpleName().toLowerCase();
                        operateMaps.put(name,((SelectOperate)d.newInstance()));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}
