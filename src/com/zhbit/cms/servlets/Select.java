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

import javax.servlet.annotation.WebServlet;
import java.util.*;

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
                        @RequestBody JSONObject param){
        return operateMaps.containsKey(kind) ?
                dealData(Objects.toString(param.getString("select_column"),"All"),operateMaps.get(kind)) :
                Tools.quickJSON(StatusCode.NO_FIND,"不支持的功能");
    }

    private JSONObject dealData(String param,SelectOperate selectOperate){
        if(selectOperate.filterWord(param)) {
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
        }else{
            return Tools.quickJSON(StatusCode.PARAM_LACK, "参数错误");
        }
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
