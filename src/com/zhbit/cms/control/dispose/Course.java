package com.zhbit.cms.control.dispose;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.exceptions.ParamLackException;
import com.zhbit.cms.frameclass.DeleteOperate;
import com.zhbit.cms.frameclass.ModifyOperate;
import com.zhbit.cms.frameclass.NewOperate;
import com.zhbit.cms.infobeans.CourseInfo;
import com.zhbit.cms.sqltools.S;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

/**
 * 控制课程
 */
public class Course implements NewOperate<CourseInfo>,ModifyOperate<CourseInfo>,DeleteOperate<Integer> {

    @Override
    public int newOperate(CourseInfo srcData, SqlSession sqls) throws CMSException {
        try {
            return sqls.selectOne(S.COURSE.CREATE, srcData);
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public int modifyOperate(CourseInfo srcData, SqlSession sqls) throws CMSException {
        try{
            if(!srcData.isNotNull()){
                throw new ParamLackException("数据不完整");
            }
            sqls.selectOne(S.COURSE.UPDATE, srcData);
            return srcData.getCourseID();
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }

    @Override
    public int deleteOperate(Integer srcData, SqlSession sqls) throws CMSException {
        try{
            sqls.delete(S.COURSE.DELETE,srcData);
            return srcData;
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
