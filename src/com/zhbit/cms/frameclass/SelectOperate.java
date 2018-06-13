package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface SelectOperate {
    boolean filterWord(String column);

    List<Map<String,?>> selectOperate(String column, SqlSession sqls) throws CMSException;
}
