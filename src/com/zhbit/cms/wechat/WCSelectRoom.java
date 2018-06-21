package com.zhbit.cms.wechat;

import com.zhbit.cms.infobeans.QueryRoomParam;
import com.zhbit.cms.infobeans.wechat.WCText;
import com.zhbit.cms.sqltools.S;
import com.zhbit.cms.sqltools.SqlSessionManagement;
import com.zhbit.cms.wechat.event.WCTextEvent;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.*;


public class WCSelectRoom implements WCTextEvent{
    private static String match;
    private static List<Character> dayList= Arrays.asList('前','昨','今','明','后');
    private static Long startDay;
    private static SqlSessionManagement sqls;

    static{
        sqls=SqlSessionManagement.getInstance();
        String builds[]={"知行","明德","弘毅","天佑","弘毅","艺悦","精工"};
        StringJoiner asd=new StringJoiner(")|(","查询((前)|(昨)|(今)|(明)|(后))天((全部)|(","))楼((占用)|(空闲))课室");
        for(String str:builds){
            asd.add(str);
        }
        match=asd.toString();
//        startDay=sqls.customSqlSession(sqlSession -> sqlSession.selectOne(CURRENT_TERM)).getTime();
    }

    @Override
    public boolean filter(WeChatIO<WCText> wcInfo) {
        return wcInfo.getInfo().getContent().matches(match);
    }

    @Override
    public void disposeMag(WeChatIO<WCText> weChatIO) {
        String text=weChatIO.getInfo().getContent();
        String build=text.substring(4,7);
        build="全部楼".equals(build)?null:build;
        int day=dayList.indexOf(text.charAt(2))-2;
        QueryRoomParam queryRoomParam=new QueryRoomParam();
        queryRoomParam.setBuildingName(build);
        queryRoomParam.setFindUsing("占用".equals(text.substring(7,9)));

        Long staD=System.currentTimeMillis()+(day*24*60*60*1000)-startDay;
        queryRoomParam.setQueryClassWeek((int)(staD/(1000*60*60*24))/7+1);
        queryRoomParam.setQueryWeek((int)(staD/(1000*60*60*24))%7);

        StringBuilder reText=new StringBuilder();
        try {
            sqls.customSqlSession(sqlSession ->{
                List<Map> resultList=sqlSession.selectList(S.ROOM.QUERY, queryRoomParam);
                reText.append("查询结果如下").append('\n')
                    .append("课室    时间");
                for (Map rMap:resultList) {
                    int value=(int)rMap.get("ClassTime");
                    int count=0;
                    String roomName=rMap.get("RoomName").toString();
                    for(int i=0;i<=14;i++){
                        if((value&1)==1){
                            count++;
                        }else if(value==0 && count==0){
                            break;
                        }else{
                            reText.append('\n').append(roomName).append(' ');
                            if(count>1){
                                reText.append(i-count+1).append('-');
                            }
                            reText.append(i);
                            count=0;
                        }
                        value>>=1;
                    }
                }
                return null;
            });
        }catch (PersistenceException e){
            reText.append(e.getCause().getMessage());
        }
        weChatIO.replyText(reText.toString());
    }

    private static Integer[] convertBitFlagToStr(int value) {
        List<Integer> l=new ArrayList<>();
        int count=0;
        for(int i=0;i<=14;i++){
            if((value&1)==1){
                count++;
            }else if(count>0){
                l.add(i*10+ count);
                count=0;
            }
            value>>=1;
        }
        return l.toArray(new Integer[l.size()]);
    }

    public static String ConvertBitFlagToStr(int SelectValue, int maxBit) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        int first = 0;
        int last = 0;
        SelectValue &= ((1 << maxBit-1) - 1);
        while (SelectValue != 0) {
            if ((SelectValue & 1) == 0) {
                if (first != 0) {
                    if (last == first || last == 0)
                        stringBuilder.append(first).append(",");
                    else
                        stringBuilder.append(first).append("-").append((i)).append(",");
                    first = last = 0;
                }
            } else {
                if (first == 0)
                    first = i;
                else
                    last = i;
            }
            SelectValue >>= 1;
            i++;
        }
        if (first != 0)
            if (last == first || last == 0)
                stringBuilder.append(first).append(",");
            else
                stringBuilder.append(first).append("-").append((i)).append(",");
        if (stringBuilder.length() > 0)
            return stringBuilder.substring(0,stringBuilder.length()-1);
        else
            return "";
    }
}
