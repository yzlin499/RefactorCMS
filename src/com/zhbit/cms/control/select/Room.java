package com.zhbit.cms.control.select;

import com.zhbit.cms.exceptions.CMSException;
import com.zhbit.cms.frameclass.SelectOperate;
import com.zhbit.cms.sqltools.SqlKey;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class Room implements SelectOperate{
    private Set<String> wordSet;

    public Room(){
        wordSet=new HashSet<>(Arrays.asList(
                "RID","RName","BID","RMaxCount","RUseFor","All"
        ));
    }

    @Override
    public boolean filterWord(String column) {
        return wordSet.contains(column);
    }

    @Override
    public List<Map<String, ?>> selectOperate(String column, SqlSession sqls) throws CMSException {
        column=column.equals("All")?"*":column;
        List l=sqls.selectList(SqlKey.SELECT_ROOM,column);
        return l;

    }
}
