package com.zhbit.cms.infobeans.beaninterface;

import com.alibaba.fastjson.annotation.JSONField;

public interface CheckNullPoint {
    @JSONField(deserialize = false)
    default boolean isNotNull(){return true;}
}
