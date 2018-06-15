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
        private Date date;

        public String getAsd() {
            return asd;
        }

        public void setAsd(String asd) {
            this.asd = asd;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "a{" +
                    "asd='" + asd + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        a asd=new a();
        asd.setDate(new Date());
        System.out.println(JSONObject.toJSONString(asd));
    }
}
