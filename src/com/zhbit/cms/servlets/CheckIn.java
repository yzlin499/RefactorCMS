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
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;


@Controller
public class CheckIn {
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder base64Decoder=new BASE64Decoder();
    private SqlSessionManagement sqlSM=SqlSessionManagement.getInstance();

    public @ResponseBody JSONObject setBuuid(@RequestBody BindingBIDAndBuuid bindingBIDAndBuuid){
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







    /**
     * 加密算法
     * @param buuid buuid
     * @param time 时间戳
     * @return 加密串
     */
    private static String encode(String buuid,String time){
        byte[] bytes=buuid.getBytes();
        byte[] timeBytes= time.getBytes();
        byte[] T=new byte[bytes.length+timeBytes.length];
        for (int i=0;i<bytes.length;i++){
            T[i]=(byte)((Character.getNumericValue(bytes[i])+timeBytes[i%13]-0x30)&0xf);
            T[i]=(byte)(T[i]>10?T[i]+87:T[i]+0x30);
        }
        System.arraycopy(timeBytes,0,T,bytes.length,timeBytes.length);
        return encoder.encode(T);
    }

    /**
     * 解密算法
     * @param code
     * @return string[0]为buuid
     *         string[1]为time
     */
    private static String[] decode(String code){
        try {
            byte[] data =base64Decoder.decodeBuffer(code);
            byte[] time =new byte[13];
            byte[] buuid=new byte[data.length-time.length];
            System.arraycopy(data,0,buuid,0,buuid.length);
            System.arraycopy(data,buuid.length,time,0,time.length);
            for (int i=0;i<buuid.length;i++){
                buuid[i]=(byte)(Character.getNumericValue(buuid[i])-time[i%13]+0x30);
                if(buuid[i]<0){
                    buuid[i]+=0x10;
                }
                buuid[i]=(byte)(buuid[i]>10?buuid[i]+87:buuid[i]+0x30);
            }
            return new String[]{new String(buuid),new String(time)};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
