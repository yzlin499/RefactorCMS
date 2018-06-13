package com.zhbit.cms.control.dispose;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.frameclass.DeleteOperate;
import com.zhbit.cms.frameclass.ModifyOperate;
import com.zhbit.cms.frameclass.NewOperate;
import com.zhbit.cms.infobeans.CourseInfo;
import com.zhbit.cms.sqltools.SqlKey;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;

/**
 * 控制课程
 */
public class Course implements NewOperate<CourseInfo>,ModifyOperate<CourseInfo>,DeleteOperate<Integer> {

    @Override
    public boolean newOperate(CourseInfo srcData, SqlSession sqls) throws CMSException {
        try {
            sqls.selectOne(SqlKey.CREATE_COURSE, srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean modifyOperate(CourseInfo srcData, SqlSession sqls) throws CMSException {
        try{
            if(!srcData.isNotNull()){
                throw new ParamLackException("数据不完整");
            }
            sqls.selectOne(SqlKey.UPDATE_COURSE, srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public boolean deleteOperate(Integer srcData, SqlSession sqls) throws CMSException {
        try{
            sqls.delete(SqlKey.DELETE_COURSE,srcData);
            return true;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
