package com.zhbit.cms.infobeans;

import com.alibaba.fastjson.annotation.JSONField;

public class BindingBIDAndBuuid {
    private String BID;
    private String Buuid;

    @JSONField(serialize = false)
    public boolean isNotNull() {
        return BID!=null && Buuid!=null;
    }

    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
    }

    public String getBuuid() {
        return Buuid;
    }

    public void setBuuid(String buuid) {
        Buuid = buuid;
    }
}
