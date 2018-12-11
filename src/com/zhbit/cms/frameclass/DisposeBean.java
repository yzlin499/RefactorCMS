package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DisposeBean {
    private Class paramClass;
    private Function function;
    private interface Function{
        int newOperate(Object srcData, SqlSession sqls) throws CMSException;
    }


    public DisposeBean(Operate operate,Class<? extends Operate> operateClass) {
        try {
            Type[] interfaces=operate.getClass().getGenericInterfaces();
            for(Type t:interfaces){
                ParameterizedType p=(ParameterizedType)t;
                if(p.getRawType().equals(operateClass)){
                    paramClass= (Class) p.getActualTypeArguments()[0];
                    break;
                }
            }
            if(operateClass.equals(NewOperate.class)){
                function= ((NewOperate) operate)::newOperate;
            }else if(operateClass.equals(ModifyOperate.class)){
                function= ((ModifyOperate) operate)::modifyOperate;
            }else if(operateClass.equals(DeleteOperate.class)){
                function= ((DeleteOperate) operate)::deleteOperate;
            }
        }catch (ClassCastException e){
            System.out.println("多半是你的DisposeClass的子类没有定义泛型");
        }

    }

    public int runOperate(Object srcData, SqlSession sqls) throws CMSException {
        return function == null ? -1 :function.newOperate(srcData,sqls);
    }

    public Class getParamClass() {
        return paramClass;
    }
}
