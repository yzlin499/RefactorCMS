package com.zhbit.cms.control.dispose;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.frameclass.DeleteOperate;
import com.zhbit.cms.frameclass.ModifyOperate;
import com.zhbit.cms.frameclass.NewOperate;
import com.zhbit.cms.infobeans.RoomInfo;
import com.zhbit.cms.sqltools.SqlKey;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class Room implements NewOperate<RoomInfo>,ModifyOperate<RoomInfo>,DeleteOperate<Integer>{

    int a=0;
    public void test(){
        System.out.println(a++);
    }


    @Override
    public boolean deleteOperate(Integer srcData, SqlSession sqls) throws CMSException {
        try{
            sqls.delete(SqlKey.DELETE_ROOM,srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean modifyOperate(RoomInfo srcData, SqlSession sqls) throws CMSException {
        try{
            if(!srcData.isNotNull()){
                throw new ParamLackException("数据不完整");
            }
            sqls.selectOne(SqlKey.UPDATE_ROOM,srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean newOperate(RoomInfo srcData, SqlSession sqls) throws CMSException {
        try {
            if(srcData.getRoomName()==null || "".equals(srcData.getRoomName())){
                throw new ParamLackException("RoomName不能为空");
            }else {
                sqls.selectOne(SqlKey.CREATE_ROOM, srcData);
                return true;
            }
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
