package com.zhbit.cms.wechat;

import java.util.HashMap;
import java.util.Map;

public class WeChatFromIDPool{
    private static class InstanceClass {
        private static final WeChatFromIDPool instance=new WeChatFromIDPool();
    }
    private WeChatFromIDPool(){}
    public static WeChatFromIDPool getInstance(){
        return InstanceClass.instance;
    }

    private HashMap<String,HashMap<Object,Object>> userMap=new HashMap<>();



    public Map<Object,Object> getAttributeMap(String userID){
        return getUserMap(userID);
    }

    public Object setAttribute(String userID,Object key,Object value){
        return getUserMap(userID).put(key,value);
    }

    public Object getAttribute(String userID,Object key){
        return getUserMap(userID).get(key);
    }

    public Object removeAttribute(String userID,Object key){
        return getUserMap(userID).remove(key);
    }

    public void removeID(String userID){
        userMap.remove(userID);
    }

    private Map getUserMap(String id){
        if(!userMap.containsKey(id)){
            userMap.put(id,new HashMap<>());
        }
        return userMap.get(id);
    }
}
