package com.zhbit.cms.control.dispose;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.frameclass.DeleteOperate;
import com.zhbit.cms.frameclass.ModifyOperate;
import com.zhbit.cms.frameclass.NewOperate;
import com.zhbit.cms.infobeans.TermInfo;
import com.zhbit.cms.sqltools.SqlKey;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class Term implements NewOperate<TermInfo>,ModifyOperate<TermInfo>,DeleteOperate<Integer> {
    @Override
    public boolean newOperate(TermInfo srcData, SqlSession sqls) throws CMSException {
        try {
            sqls.selectOne(SqlKey.CREATE_TERM, srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean modifyOperate(TermInfo srcData, SqlSession sqls) throws CMSException {
        try{
            if(!srcData.isNotNull()){
                throw new ParamLackException("数据不完整");
            }
            sqls.selectOne(SqlKey.UPDATE_TERM, srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean deleteOperate(Integer srcData, SqlSession sqls) throws CMSException {
        try{
            sqls.delete(SqlKey.DELETE_TERM,srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
