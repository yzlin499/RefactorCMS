package com.zhbit.cms.sqltools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import static com.zhbit.cms.sqltools.SqlKey.REGISTER;

public final class SqlSessionManagement{
    private static class InstanceClass {
        private static final SqlSessionManagement instance=new SqlSessionManagement();
    }
    private SqlSessionManagement(){
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 获取单例
     * @return 单例
     */
    public static SqlSessionManagement getInstance(){
        return InstanceClass.instance;
    }



    /**
     * 不必关，我会关的
     * function你随意，我不管的
     * 会有一步sqlSession.commit();
     * @param function
     */
    public <T> T customSqlSession(Function<SqlSession,T> function) {
        SqlSession sqls=sqlSessionFactory.openSession();
        T temp=function.apply(sqls);
        sqls.commit();
        sqls.close();
        return temp;
    }


}
