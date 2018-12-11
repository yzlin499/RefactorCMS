package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.frameclass.*;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.tools.ClassTools;
import com.zhbit.cms.tools.Tools;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class Dispose{
    static {
        initFunction();
    }
    private static HashMap<String,HashMap<String,DisposeBean>> operateMaps;
    private static final SqlSessionManagement sqls=SqlSessionManagement.getInstance();

//    @RequestMapping(value = "/{kind}",method = RequestMethod.GET)
//    public @ResponseBody JSONObject a(){
//
//    }

    @RequestMapping(value = "/dispose/{kind}/{operate:new|modify|delete}")
    public @ResponseBody JSONObject doDispose(@PathVariable("kind")String kind,
                                              @PathVariable("operate")String operate,
                                              @RequestBody JSONArray param){
        HashMap<String,DisposeBean> om=operateMaps.get(operate);
        return om.containsKey(kind)?
                dealData(param,om.get(kind),"new".equals(kind)) :
                Tools.quickJSON(StatusCode.NO_FIND,"不支持的功能");
    }

    private JSONObject dealData(JSONArray data,DisposeBean tempDispose,boolean isNew)  {
        JSONObject resultJSON=new JSONObject();
        JSONArray successJSON=new JSONArray();
        JSONArray errorJSON=new JSONArray();
        JSONArray newID=isNew?new JSONArray():null;

        try {
            sqls.customSqlSession(sqlSession -> {
                for (int i = 0; i < data.size(); i++) {
                    try {
                        Object o=data.get(i);
                        if(o instanceof JSONObject){
                            o=((JSONObject) o).toJavaObject(tempDispose.getParamClass());
                        }
                        int resultID=tempDispose.runOperate(o, sqlSession);
                        if (resultID>=0) {
                            successJSON.add(i);
                            if(isNew){
                                newID.add(resultID);
                            }
                        } else {
                            Logger.getLogger(this.getClass()).error("false结果");
                        }
                    } catch (CMSException e) {
                        JSONObject jo = new JSONObject();
                        jo.put(StatusCode.MESSAGE, e.getMessage());
                        jo.put("index", i);
                        errorJSON.add(jo);
                    }
                }
                return null;
            });
            resultJSON.put(StatusCode.STATUS, StatusCode.COMPLETE);
            resultJSON.put(StatusCode.SUCCESS,successJSON);
            resultJSON.put(StatusCode.ERROR,errorJSON);
            if(isNew){
                resultJSON.put("newID",newID);
            }
        }catch(ClassCastException|NumberFormatException e){
            resultJSON.put(StatusCode.STATUS, StatusCode.PARAM_LACK);
            resultJSON.put(StatusCode.MESSAGE,"参数转换错误:"+e.getMessage());
        }catch (PersistenceException e){
            resultJSON.put(StatusCode.STATUS, StatusCode.DB_ERROR);
            resultJSON.put(StatusCode.MESSAGE,"来自数据库的崩坏:"+e.getMessage());
        }
        return resultJSON;
    }



    private static void initFunction(){
        operateMaps=new HashMap<>();
        operateMaps.put("new",new HashMap<>());
        operateMaps.put("modify",new HashMap<>());
        operateMaps.put("delete",new HashMap<>());
        ClassTools.getClasses("com.zhbit.cms.control.dispose").stream()
                .filter(Operate.class::isAssignableFrom)
                .forEach(d->{
                    try {
                        String name=d.getSimpleName().toLowerCase();
                        Operate o=(Operate)d.newInstance();
                        if(o instanceof NewOperate){
                            operateMaps.get("new").put(name,new DisposeBean(o,NewOperate.class));
                        }
                        if(o instanceof ModifyOperate){
                            operateMaps.get("modify").put(name,new DisposeBean(o,ModifyOperate.class));
                        }
                        if(o instanceof DeleteOperate){
                            operateMaps.get("delete").put(name,new DisposeBean(o,DeleteOperate.class));
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

}
