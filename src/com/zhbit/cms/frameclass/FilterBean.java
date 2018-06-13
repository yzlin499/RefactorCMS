package com.zhbit.cms.frameclass;

import com.zhbit.cms.exceptions.CMSException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class FilterBean {
    private FilterOperate filterOperate;
    private Class paramClass;
    private Class returnClass;

    public FilterBean(FilterOperate filterOperate) {
        this.filterOperate = filterOperate;
        try {
            Type[] interfaces=filterOperate.getClass().getGenericInterfaces();
            for(Type t:interfaces){
                ParameterizedType p=(ParameterizedType)t;
                if(p.getRawType().equals(FilterOperate.class)){
                    Type[] temps= p.getActualTypeArguments();
                    paramClass= (Class)temps[0];
                    returnClass=(Class)temps[1];
                    break;
                }
            }
        }catch (ClassCastException e){
            System.out.println("多半是你的DisposeClass的子类没有定义泛型");
        }

    }

    public Class getParamClass() {
        return paramClass;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public List runOperate(Object srcData, SqlSession sqls) throws CMSException {
        return filterOperate.filterOperate(srcData,sqls);
    }
}
