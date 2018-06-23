package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

public interface NewOperate<T> extends Operate{
    /**
     * 新建操作
     * @param srcData 操作数据
     * @param sqls SQLsession
     * @return 是否创建成功
     * @throws CMSException 错误
     */
    int newOperate(T srcData, SqlSession sqls) throws CMSException;
}
