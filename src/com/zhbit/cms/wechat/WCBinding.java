package com.zhbit.cms.wechat;

import com.zhbit.cms.infobeans.wechat.WCText;
import com.zhbit.cms.wechat.event.WCTextEvent;

public class WCBinding implements WCTextEvent {
    @Override
    public boolean filter(WeChatIO<WCText> wcInfo) {
        return false;
    }

    @Override
    public void disposeMag(WeChatIO<WCText> weChatIO) {

    }
}
