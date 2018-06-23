package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

public interface ModifyOperate<T> extends Operate{
    /**
    * 修改操作
    * @param srcData 操作数据
    * @param sqls SQLsession
    * @return 是否修改成功
    * @throws CMSException 错误
    */
    int modifyOperate(T srcData, SqlSession sqls) throws CMSException;
}
