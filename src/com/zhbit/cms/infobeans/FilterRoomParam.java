package com.zhbit.cms.infobeans;

public class FilterRoomParam {
    private boolean FindUsing=true;
    private int Week=128;
    private int ClassWeek=1048575;
    private int ClassTime=32767;
    private int TermID=0;
    private String BuildingName=null;

    public boolean isFindUsing() {
        return FindUsing;
    }

    public void setFindUsing(boolean findUsing) {
        FindUsing = findUsing;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        Week = week;
    }

    public int getClassWeek() {
        return ClassWeek;
    }

    public void setClassWeek(int classWeek) {
        ClassWeek = classWeek;
    }

    public int getClassTime() {
        return ClassTime;
    }

    public void setClassTime(int classTime) {
        ClassTime = classTime;
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        TermID = termID;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }
}
