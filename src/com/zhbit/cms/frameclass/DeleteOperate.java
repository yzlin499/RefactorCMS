package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

public interface DeleteOperate<T> extends Operate{
    /**
     * 删除操作，一般是Int类型
     * @param srcData 要删除的数据
     * @param sqls sqlssion
     * @return 是否删除成功
     * @throws CMSException 错误
     */
    boolean deleteOperate(T srcData, SqlSession sqls) throws CMSException;
}
