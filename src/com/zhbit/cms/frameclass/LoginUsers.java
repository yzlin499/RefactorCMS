package com.zhbit.cms.frameclass;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginUsers {
    private static final String LOGIN_STATUS="loginStatus";
    private static final String USER_NAME="userName";
    private static final String LOGIN_TYPE_MAP="loginTypeMap";

    private static class InstanceClass {
        private static final LoginUsers instance=new LoginUsers();
    }
    private LoginUsers(){}
    public static LoginUsers getInstance(){
        return InstanceClass.instance;
    }

    private Map<String,HttpSession> webLoginMap=new HashMap<>();
    private Map<String,HttpSession> phoneLoginMap=new HashMap<>();
    private Map<String,HttpSession> clientLoginMap=new HashMap<>();

    public void login(String user,HttpSession httpSession,String type){
        switch (type.toUpperCase()){
            case "CLIENT":clientLogin(user,httpSession);break;
            case "PHONE":phoneLogin(user,httpSession);break;
            default: webLogin(user,httpSession);break;
        }
    }

    public void webLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,webLoginMap);
    }

    public void phoneLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,phoneLoginMap);
    }

    public void clientLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,clientLoginMap);
    }

    private void newLogin(String user,HttpSession httpSession,Map<String,HttpSession> loginMap){
        httpSession.setMaxInactiveInterval(36000);
        httpSession.setAttribute(LOGIN_STATUS, true);
        httpSession.setAttribute(USER_NAME,user);
        httpSession.setAttribute(LOGIN_TYPE_MAP,loginMap);

        HttpSession httpSession2=loginMap.put(user,httpSession);
        if(httpSession2!=null && (!httpSession2.equals(httpSession))){
            try {
                httpSession2.setAttribute(LOGIN_STATUS, false);
                httpSession2.invalidate();
            }catch (IllegalStateException e){
                Logger.getLogger(LoginUsers.class).error(e);
            }
        }
    }

    public boolean isLogin(HttpSession httpSession){
        Boolean b=(Boolean) httpSession.getAttribute(LOGIN_STATUS);
        return b==null?false:b;
    }

    public void logout(HttpSession httpSession){
        httpSession.setAttribute("loginState",false);
        ((Map)httpSession.getAttribute(LOGIN_TYPE_MAP)).remove(httpSession.getAttribute(USER_NAME));
        httpSession.invalidate();
    }
}