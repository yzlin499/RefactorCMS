package com.zhbit.cms.control.select;

import com.zhbit.cms.frameclass.SelectOperate;
import com.zhbit.cms.sqltools.S;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Term implements SelectOperate {
    private Set<String> wordSet= SqlSessionManagement.getInstance().getTableField("TermInfo");

    @Override
    public boolean filterWord(String column) {
        return wordSet.contains(column);
    }

    @Override
    public List<Map<String, ?>> selectOperate(String column, SqlSession sqls) {
        return sqls.selectList(S.TERM.SELECT,column);
    }
}
