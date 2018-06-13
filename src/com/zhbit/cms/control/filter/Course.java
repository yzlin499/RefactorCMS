package com.zhbit.cms.control.filter;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.exceptions.DBException;
import com.zhbit.cms.frameclass.FilterOperate;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.zhbit.cms.sqltools.SqlKey.FILTER_COURSE;

public class Course implements FilterOperate<Course,Course> {
    @Override
    public List<Course> filterOperate(Course srcData, SqlSession sqls) throws CMSException {
        try {
            return sqls.selectList(FILTER_COURSE,srcData);
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
