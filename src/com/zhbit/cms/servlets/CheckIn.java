package com.zhbit.cms.servlets;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ForbiddenException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.infobeans.BindingBIDAndBuuid;
import com.zhbit.cms.sqltools.S;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.tools.Tools;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


@Controller
@RequestMapping("/check")
public class CheckIn {
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder base64Decoder=new BASE64Decoder();
    private static SqlSessionManagement sqlSM=SqlSessionManagement.getInstance();

    @RequestMapping("/setbuuid")
    public @ResponseBody JSONObject setBuuid(@RequestBody BindingBIDAndBuuid bindingBIDAndBuuid){
        try {
            if (!bindingBIDAndBuuid.isNotNull()) {
                throw new ParamLackException("缺少参数");
            }
            try {
                sqlSM.customSqlSession(s -> s.update(S.CHECK.BINDING_BUUID, bindingBIDAndBuuid));
            } catch (PersistenceException e) {
                throw new DBException(e.getCause().getMessage());
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(StatusCode.STATUS, StatusCode.COMPLETE);
            return jsonObject;
        }catch (CMSException e){
            return Tools.quickJSON(e.getStatus(),e.getMessage());
        }
    }

    @RequestMapping("/getcheckinoutverifycode")
    public @ResponseBody JSONObject getCheckInOutVerifyCode(@RequestBody JSONObject req){
        try {
            String wechatID = Objects.toString(req.getString("wechat_id"), "");
            if ("".equals(wechatID)) {
                throw new ParamLackException("缺少参数");
            }
            try {
                int a = sqlSM.customSqlSession(s -> s.selectOne(S.CHECK.GENERATE_VERIFY_CODE, wechatID));
                return Tools.quickJSON(StatusCode.COMPLETE,StatusCode.SUCCESS).fluentPut("data",a);
            } catch (PersistenceException e) {
                throw new DBException(e.getCause().getMessage());
            }
        }catch (CMSException e){
            return Tools.quickJSON(e.getStatus(),e.getMessage());
        }
    }

    @RequestMapping("/verifycheckinout")
    public @ResponseBody JSONObject verifyCheckInOut(@RequestBody JSONObject req){
        try{
            if(req.containsKey("verify_code") && req.containsKey("building_flag")){
                try {
                    String[] decode=decode(req.getString("building_flag"));
                    if(System.currentTimeMillis()-Long.parseLong(decode[1])>1000*60*2){
                        throw new ForbiddenException("过期的buildingFlag");
                    }
                    HashMap<String,String> map=new HashMap<>();
                    map.put("verifyCode",req.getString("verify_code"));
                    map.put("buildingFlag",decode[0]);
                    return Tools.quickJSON(StatusCode.COMPLETE,StatusCode.SUCCESS).fluentPut("data",
                            sqlSM.customSqlSession(s -> s.selectOne(S.CHECK.CHECK_IN, req)));
                } catch (PersistenceException e) {
                    throw new DBException(e.getCause().getMessage());
                }
            }else{
                throw new ParamLackException("缺少参数");
            }
        }catch  (CMSException e){
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
