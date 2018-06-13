import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.infobeans.TermInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

public class TestFunction {
    public static class a{
        private String asd;

        public String getAsd() {
            return asd;
        }

        public void setAsd(String asd) {
            this.asd = asd;
        }

        @Override
        public String toString() {
            return "a{" +
                    "asd='" + asd + '\'' +
                    '}';
        }
    }

    public static void main(String[] args){
//        String[] aasd=null;
//        Arrays.fill(aasd,null);
//        try {
//            aasd = new String[1];
//            aasd[0]="qwe";
//            aasd[1] = "123";
//        }catch (ArrayIndexOutOfBoundsException e){
//            System.out.println(aasd[0]);
//        }
    }
}
