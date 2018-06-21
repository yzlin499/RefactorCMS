package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.infobeans.BindingBIDAndBuuid;
import com.zhbit.cms.sqltools.S;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.tools.Tools;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SetBuuid {
    private SqlSessionManagement sqlSM=SqlSessionManagement.getInstance();

    @RequestMapping
    public @ResponseBody JSONObject APIFunction(@RequestBody BindingBIDAndBuuid bindingBIDAndBuuid){
        try {
            if (!bindingBIDAndBuuid.isNotNull()) {
                throw new ParamLackException("缺少参数");
            }
            try {
                sqlSM.customSqlSession(s -> s.update(S.BINDING_BUUID, bindingBIDAndBuuid));
            } catch (PersistenceException e) {
                throw new DBException(e.getCause().getMessage());
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 200);
            return jsonObject;
        }catch (CMSException e){
            return Tools.quickJSON(e.getStatus(),e.getMessage());
        }
    }
}
