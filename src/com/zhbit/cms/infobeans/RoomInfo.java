package com.zhbit.cms.infobeans;

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
    public boolean isNotNull(){
        if(RoomName!=null && BuildingName!=null && RoomID!=-1 && MaxStudentCount!=-1 && UseFor !=null){
            return true;
        }else{
            return false;
        }
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public int getMaxStudentCount() {
        return MaxStudentCount;
    }

    public void setMaxStudentCount(int maxStudentCount) {
        MaxStudentCount = maxStudentCount;
    }

    public String getUseFor() {
        return UseFor;
    }

    public void setUseFor(String useFor) {
        UseFor = useFor;
    }


}
