package com.zhbit.cms.control.select;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.frameclass.SelectOperate;
import com.zhbit.cms.sqltools.SqlKey;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class Term implements SelectOperate {
    private Set<String> wordSet=new HashSet<>(Arrays.asList(
            "TID","TName","TStartDate"
    ));

    @Override
    public boolean filterWord(String column) {
        return wordSet.contains(column);
    }

    @Override
    public List<Map<String, ?>> selectOperate(String column, SqlSession sqls) throws CMSException {
        return sqls.selectList(SqlKey.SELECT_TERM,column);
    }
}
