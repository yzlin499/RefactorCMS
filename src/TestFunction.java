import com.zhbit.cms.sqltools.SqlSessionManagement;

public class TestFunction {

    public static void main(String[] args) {
        System.out.println(SqlSessionManagement.getInstance().getTableField("TermInfo"));
    }
}
