package com.zhbit.cms.servlets;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.frameclass.FilterBean;
import com.zhbit.cms.frameclass.FilterOperate;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.infobeans.beaninterface.ToJSONObject;
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

@Controller
public class Filter {
    static {
        initFunction();
    }
    private static HashMap<String,FilterBean> operateMaps;
    private static final SqlSessionManagement sqls=SqlSessionManagement.getInstance();

    @RequestMapping("/filter/{kind}")
    public @ResponseBody JSONObject doFilter(@PathVariable("kind")String kind,
                                             @RequestBody JSONObject param){
        return operateMaps.containsKey(kind) ?
                dealData(param,operateMaps.get(kind)) :
                Tools.quickJSON(StatusCode.NO_FIND,"不支持的功能");
    }


    private JSONObject dealData(JSONObject data,FilterBean tempFilter) {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            List a = sqls.customSqlSession(s -> {
                try {
                    return tempFilter.runOperate(data.toJavaObject(tempFilter.getParamClass()), s);
                } catch (CMSException e) {
                    e.printStackTrace();
                }
                return Collections.emptyList();
            });
            jsonObject=Tools.quickJSON(StatusCode.COMPLETE,StatusCode.SUCCESS);
            if (Number.class.isAssignableFrom(tempFilter.getReturnClass()) ||
                    CharSequence.class.isAssignableFrom(tempFilter.getReturnClass()) ) {
                for(Object o:a){
                    jsonArray.add(o.toString());
                }
            }else if(ToJSONObject.class.isAssignableFrom(tempFilter.getReturnClass())){
                for(Object o:a){
                    jsonArray.add(((ToJSONObject)o).toJSON());
                }
            }else{
                jsonArray.addAll(a);
            }
            jsonObject.put("data",jsonArray);
        }catch (ClassCastException|NumberFormatException e){
            jsonObject.put(StatusCode.STATUS, StatusCode.PARAM_LACK);
            jsonObject.put(StatusCode.MESSAGE,"参数转换错误:"+e.getMessage());
        }
        return jsonObject;
    }


    private static void initFunction(){
        operateMaps=new HashMap<>();
        ClassTools.getClasses("com.zhbit.cms.control.filter").stream()
                .filter(FilterOperate.class::isAssignableFrom)
                .forEach(d->{
                    try {
                        String name=d.getSimpleName().toLowerCase();
                        FilterOperate o=(FilterOperate)d.newInstance();
                        operateMaps.put(name,new FilterBean(o));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}
