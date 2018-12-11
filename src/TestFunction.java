import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.tools.NetTools;

public class TestFunction {

    public static void main(String[] args) {
        String data=NetTools.sendGet("http://h5.snh48.com/resource/jsonp/members.php","gid=30&callback=get_members_success");
        data=data.substring(data.indexOf('(')+1,data.length()-2);

        JSONArray ja=JSONObject.parseObject(data).getJSONArray("rows");

        for (Object o : ja) {
            JSONObject jo=(JSONObject)o;
            String birth_place=jo.getString("birth_place");
            if(birth_place.contains("广东")){
                System.out.println(jo.getString("sname"));
            }
            
        }
        System.out.println();
//        JSONObject.parseObject(.substring());
    }
}
