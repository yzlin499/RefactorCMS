package com.zhbit.cms.infobeans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class UserInfo {
    private String UserName;
    private String PassWord;
    private String Name;
    private String EMail;
    private String ClassName;
    private String TelPhone;
    private Boolean sex;
    private int PersonID;
    private int PersonCtrlLevel;
    private int PersonGroup;

    public static void main(String [] args){
        UserInfo userInfo=new UserInfo();
        userInfo.setPersonCtrlLevel(10);
        System.out.println(JSONObject.toJSON(userInfo));
    }

    @JSONField(name="person_id")
    public int getPersonID() {
        return PersonID;
    }

    @JSONField(name="person_id")
    public void setPersonID(int personID) {
        PersonID = personID;
    }

    @JSONField(name = "person_ctrl_level",deserialize = false)
    public int getPersonCtrlLevel() {
        return PersonCtrlLevel;
    }

    @JSONField(name = "person_ctrl_level")
    public void setPersonCtrlLevel(int personCtrlLevel) {
        PersonCtrlLevel = personCtrlLevel;
    }

    @JSONField(name = "sex")
    public Boolean isSex() {
        return sex;
    }

    @JSONField(name = "sex")
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @JSONField(name = "user_name")
    public String getUserName() {
        return UserName;
    }

    @JSONField(name = "user_name")
    public void setUserName(String userName) {
        UserName = userName;
    }

    @JSONField(name = "password")
    public String getPassWord() {
        return PassWord;
    }

    @JSONField(name = "password")
    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    @JSONField(name = "name")
    public String getName() {
        return Name;
    }

    @JSONField(name = "name")
    public void setName(String name) {
        Name = name;
    }

    @JSONField(name = "email")
    public String getEMail() {
        return EMail;
    }

    @JSONField(name = "email")
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    @JSONField(name = "class_name")
    public String getClassName() {
        return ClassName;
    }

    @JSONField(name = "class_name")
    public void setClassName(String className) {
        ClassName = className;
    }

    @JSONField(name = "tel_phone")
    public String getTelPhone() {
        return TelPhone;
    }

    @JSONField(name = "tel_phone")
    public void setTelPhone(String telPhone) {
        TelPhone = telPhone;
    }

    @JSONField(name = "person_group")
    public int getPersonGroup() {
        return PersonGroup;
    }

    @JSONField(name = "person_group")
    public void setPersonGroup(int personGroup) {
        PersonGroup = personGroup;
    }
}
