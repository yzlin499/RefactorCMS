package com.zhbit.cms.infobeans;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhbit.cms.infobeans.beaninterface.BasicInterface;
import com.zhbit.cms.infobeans.beaninterface.MapKey;

public class RoomInfo implements BasicInterface {
    private int RoomID=-1;
    private String RoomName;
    private String BuildingName;

    private int MaxStudentCount=-1;
    private String UseFor;

    public RoomInfo(){}


    @Override
    @JSONField(deserialize = false)
    public boolean isNotNull(){
        if(RoomName!=null && BuildingName!=null && RoomID!=-1 && MaxStudentCount!=-1 && UseFor !=null){
            return true;
        }else{
            return false;
        }
    }

    @JSONField(name = "room_id")
    public int getRoomID() {
        return RoomID;
    }

    @JSONField(name = "room_id")
    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    @JSONField(name = "room_name")
    public String getRoomName() {
        return RoomName;
    }

    @JSONField(name = "room_name")
    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    @JSONField(name = "building_name")
    public String getBuildingName() {
        return BuildingName;
    }

    @JSONField(name = "building_name")
    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    @JSONField(name = "max_student_count")
    public int getMaxStudentCount() {
        return MaxStudentCount;
    }

    @JSONField(name = "max_student_count")
    public void setMaxStudentCount(int maxStudentCount) {
        MaxStudentCount = maxStudentCount;
    }

    @JSONField(name = "user_for")
    public String getUseFor() {
        return UseFor;
    }

    @JSONField(name = "user_for")
    public void setUseFor(String useFor) {
        UseFor = useFor;
    }


}
