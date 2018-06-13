package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface FilterOperate<T,R> {
    List<R> filterOperate(T srcData, SqlSession sqls) throws CMSException;
}
