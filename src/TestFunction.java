import com.zhbit.cms.sqltools.SqlSessionManagement;

import java.util.HashMap;

public class TestFunction {

    public static void main(String[] args) {
        SqlSessionManagement.getInstance().customSqlSession(s->{
            HashMap<String,String> hashMap=new HashMap<>();
//            hashMap.put("name","PersonInfo");
            System.out.println(s.selectList("com.zhbit.cms.sqltools.other.test",hashMap));


            return null;
        });

    }
}
