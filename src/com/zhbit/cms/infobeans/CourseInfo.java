package com.zhbit.cms.infobeans;


import com.zhbit.cms.infobeans.beaninterface.BasicInterface;
import com.zhbit.cms.infobeans.beaninterface.MapKey;

public class CourseInfo implements BasicInterface {
    @MapKey("CName")
    private String CourseName;
    @MapKey("CClass")
    private String ClassName;
    @MapKey("CWeek")
    private int Week=-1;
    @MapKey("CClWeek")
    private int ClassWeek=-1;
    @MapKey("CClTime")
    private int ClassTime=-1;
    @MapKey("CTeacher")
    private String Teacher;
    @MapKey("RID")
    private int RID=-1;
    @MapKey("CStuCount")
    private int StuCount=0;
    @MapKey("TID")
    private int CurTermID=-1;
    @MapKey("CID")
    private int CourseID=-1;

    public CourseInfo(){}

    @Override
    public boolean isNotNull(){
        return (CourseName!=null && ClassName!=null && Week!=-1 && ClassWeek!=-1 && ClassTime!=-1
                && Teacher!=null && RID!=-1 && StuCount!=-1 && CurTermID!=-1 && CourseID!=-1);
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
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

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public int getStuCount() {
        return StuCount;
    }

    public void setStuCount(int stuCount) {
        StuCount = stuCount;
    }

    public int getCurTermID() {
        return CurTermID;
    }

    public void setCurTermID(int curTermID) {
        CurTermID = curTermID;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }
}
