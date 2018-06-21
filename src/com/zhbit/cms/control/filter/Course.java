package com.zhbit.cms.control.filter;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.frameclass.FilterOperate;
import com.zhbit.cms.infobeans.CourseInfo;
import com.zhbit.cms.sqltools.S;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class Course implements FilterOperate<CourseInfo,CourseInfo> {
    @Override
    public List<CourseInfo> filterOperate(CourseInfo srcData, SqlSession sqls) throws CMSException {
        try {
            return sqls.selectList(S.COURSE.FILTER,srcData);
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
