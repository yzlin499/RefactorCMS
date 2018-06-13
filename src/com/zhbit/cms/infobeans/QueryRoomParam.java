package com.zhbit.cms.infobeans;



public class QueryRoomParam {
    private boolean FindUsing=true;
    private int QueryWeek=-1;
    private int QueryClassWeek=-1;
    private int TermID=0;
    private String BuildingName=null;

    public boolean isNotNull() {
        return QueryWeek!=-1 && QueryClassWeek!=-1;
    }

    public boolean isFindUsing() {
        return FindUsing;
    }

    public void setFindUsing(boolean findUsing) {
        FindUsing = findUsing;
    }

    public int getQueryWeek() {
        return QueryWeek;
    }

    public void setQueryWeek(int queryWeek) {
        QueryWeek = queryWeek;
    }

    public int getQueryClassWeek() {
        return QueryClassWeek;
    }

    public void setQueryClassWeek(int queryClassWeek) {
        QueryClassWeek = queryClassWeek;
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
