package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.exceptions.UnknownFailException;
import com.zhbit.cms.frameclass.LoginUsers;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.infobeans.RoomInfo;
import com.zhbit.cms.infobeans.UserInfo;
import com.zhbit.cms.sqltools.SqlKey;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.tools.Tools;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/user")
public class UserDispose {
    private LoginUsers loginUsers=LoginUsers.getInstance();
    private SqlSessionManagement sqls=SqlSessionManagement.getInstance();

    /**
     *
     * @param data
     * @param httpSession
     * @return
     */
    @RequestMapping("/login")
    public @ResponseBody JSONObject userLogin(@RequestBody JSONObject data,HttpSession httpSession){
        JSONObject resultJSON;
        String UserName= Objects.toString(data.getString("UserName"),"");
        String Password=Objects.toString(data.getString("Password"),"");
        String loginWay=Objects.toString(data.getString("LoginWay"),"WEB");
        String sign=Objects.toString(data.getString("sign"),"");
        int personGroup=data.getIntValue("PersonGroup");
        try {
            if ("".equals(UserName) || "".equals(Password)) {
                throw new ParamLackException("没有密码或者登录名");
            } else if (!checkSign(UserName, Password,personGroup, sign)) {
                throw new ParamLackException("签名失败");
            }else{
                UserInfo userInfo=new UserInfo();
                userInfo.setUserName(UserName);
                userInfo.setPassWord(Tools.MD5(Password.toLowerCase()));
                UserInfo ui = sqls.customSqlSession(sqls ->sqls.selectOne(SqlKey.LOGIN_USER, userInfo));
                if (ui!=null){
                    ui.setUserName(UserName);
                    ui.setPersonGroup(personGroup);
                    resultJSON=Tools.quickJSON(StatusCode.COMPLETE,"登录成功");
                    resultJSON.put("data", JSONObject.toJSON(ui));
                    httpSession.setAttribute("userInfo",ui);
                    loginUsers.login(UserName,httpSession,loginWay);
                }else {
                    throw new UnknownFailException("登录炸了");
                }
            }
        }catch (CMSException e){
            resultJSON=Tools.quickJSON(e.getStatus(),e.getMessage());
        }catch (PersistenceException e){
            resultJSON=Tools.quickJSON(StatusCode.DB_ERROR,e.getMessage());
        }
        return resultJSON;
    }

    @RequestMapping("/logout")
    public @ResponseBody JSONObject userLogout(HttpSession httpSession){
        loginUsers.logout(httpSession);
        return Tools.quickJSON(StatusCode.COMPLETE,"已登出");
    }

    @RequestMapping("/register")
    public JSONObject register(HttpSession httpSession,@RequestBody JSONObject data){
        UserInfo userInfo=data.toJavaObject(UserInfo.class);
        try{
            if(!Objects.toString(userInfo.getUserName(),"").matches("^[a-zA-Z0-9]{3,16}$")){
                throw new ParamLackException("非法用户名");
            }else if(!Objects.toString(userInfo.getEMail(),"").matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")){
                throw new ParamLackException("非法邮箱");
            }else if(!Objects.toString(userInfo.getPassWord(),"").matches("^[a-z0-9]{32}$")){
                throw new ParamLackException("非法密码");
            }else if(!Objects.toString(userInfo.getName(),"").matches("^[\u4E00-\u9FA5]{2,5}")){
                throw new ParamLackException("非法名字");
            }else if(!Objects.toString(userInfo.getTelPhone(),"").matches("^1[34578]\\d{9}$")) {
                throw new ParamLackException("非法手机");
            }else if(!data.containsKey("IdentifyingCode")) {
                throw new ParamLackException("验证码不存在");
            }else if(!Objects.toString(data.getString("IdentifyingCode"),"").equals(httpSession.getAttribute("IdentifyingCode"))){
                throw new ParamLackException("验证码错误");
            }else if(userInfo.getClassName()==null){
                throw new ParamLackException("非法班级");
            }else{
                try {
                    userInfo.setPassWord(Tools.MD5(userInfo.getPassWord()));
                    sqls.customSqlSession(sqls->sqls.selectOne(SqlKey.REGISTER, userInfo));
                }catch (PersistenceException e){
                    throw new DBException(e.getCause().getMessage());
                }
            }
        }catch (CMSException e){
            return Tools.quickJSON(e.getStatus(),e.getMessage());
        }
        return Tools.quickJSON(StatusCode.COMPLETE,StatusCode.SUCCESS);
    }


    /**
     * String param="UserName="+UserName+"&" +
     *"Password="+Password+"&" +
     "PersonGroup="+PersonGroup+"&" +
     "p=cms";
     param= Tools.MD5(param).substring(12,20)
     * @param UserName
     * @param Password
     * @param PersonGroup
     * @param sign
     * @return
     */
    private boolean checkSign(String UserName,String Password,int PersonGroup,String sign) {
        if("".equals(sign)){
            return false;
        }
        String param="UserName="+UserName+"&"+
                "Password="+Password+"&"+
                "PersonGroup="+PersonGroup+"&"+
                "p=cms";
        param= Tools.MD5(param).substring(12,20);
        return param.equals(sign);
    }

}
