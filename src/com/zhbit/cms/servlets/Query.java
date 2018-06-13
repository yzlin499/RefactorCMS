package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.infobeans.QueryRoomParam;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zhbit.cms.sqltools.SqlKey.QUERY_ROOM;

@Controller
public class Query {
    private SqlSessionManagement sqls= SqlSessionManagement.getInstance();


    @RequestMapping("/query/room")
    public @ResponseBody JSONObject queryRoom(@RequestBody QueryRoomParam data){
        JSONObject responseJSON = new JSONObject();
        if(data.isNotNull()){
            responseJSON.put("state", StatusCode.COMPLETE);
            responseJSON.put("message","查询成功");

            JSONArray dataNode=new JSONArray();
            try {
                dataNode.addAll(
                        sqls.customSqlSession(sqlSession ->sqlSession.selectList(QUERY_ROOM, data))
                                .stream()
                                .map(JSONObject::toJSON).collect(Collectors.toList())
                );
            }catch (PersistenceException e){
                responseJSON.put("state", StatusCode.DB_ERROR);
                responseJSON.put("message",e.getCause().getMessage());
            }
            responseJSON.put("data",dataNode);
        }else{
            responseJSON.put("state", StatusCode.PARAM_LACK);
            responseJSON.put("message","参数错误");
        }
        return responseJSON;


    }
}
