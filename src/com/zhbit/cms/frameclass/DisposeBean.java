package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DisposeBean {
    private Operate operate;
    private Class paramClass;
    private Class operateClass;

    public DisposeBean(Operate operate,Class<? extends Operate> operateClass) {
        this.operate = operate;
        this.operateClass=operateClass;
        try {
            Type[] interfaces=operate.getClass().getGenericInterfaces();
            for(Type t:interfaces){
                ParameterizedType p=(ParameterizedType)t;
                if(p.getRawType().equals(operateClass)){
                    paramClass= (Class) p.getActualTypeArguments()[0];
                    break;
                }
            }
        }catch (ClassCastException e){
            System.out.println("多半是你的DisposeClass的子类没有定义泛型");
        }

    }

    public int runOperate(Object srcData, SqlSession sqls) throws CMSException {
        if(operateClass.equals(NewOperate.class)){
            return ((NewOperate) operate).newOperate(srcData,sqls);
        }else if(operateClass.equals(ModifyOperate.class)){
            return ((ModifyOperate) operate).modifyOperate(srcData,sqls);
        }else if(operateClass.equals(DeleteOperate.class)){
            return ((DeleteOperate) operate).deleteOperate(srcData,sqls);
        }
        return -1;
    }

    public Class getParamClass() {
        return paramClass;
    }
}
