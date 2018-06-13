package com.zhbit.cms.infobeans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.zhbit.cms.infobeans.beaninterface.BasicInterface;
import com.zhbit.cms.infobeans.beaninterface.MapKey;

import java.util.Date;

public class TermInfo implements BasicInterface {

    private int TermID=-1;

    private String TermName;

    private Date StartDate;

    public TermInfo(){}

    @Override
    public boolean isNotNull(){
        return TermID!=-1 && TermName!=null && StartDate!=null;
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        TermID = termID;
    }

    public String getTermName() {
        return TermName;
    }

    @JSONField(name = "term_name")
    public void setTermName(String termName) {
        TermName = termName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

}
