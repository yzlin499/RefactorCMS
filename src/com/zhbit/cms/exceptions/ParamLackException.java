package com.zhbit.cms.exceptions;

import com.zhbit.cms.frameclass.StatusCode;

public class ParamLackException extends CMSException {
    public ParamLackException(String message){
        super(StatusCode.PARAM_LACK,message);
    }
}
