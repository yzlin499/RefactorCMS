package com.zhbit.cms.sqltools;

public interface SqlKey {
    String REGISTER     ="com.zhbit.cms.sqltools.DisposeUser.Register";
    String LOGIN_USER   ="com.zhbit.cms.sqltools.DisposeUser.LoginUser";
    String SELECT_PERSON="com.zhbit.cms.sqltools.DisposeUser.SelectPerson";

    String CREATE_COURSE="com.zhbit.cms.sqltools.DisposeCourse.CreateCourse";
    String FILTER_COURSE="com.zhbit.cms.sqltools.DisposeCourse.FilterCourse";
    String UPDATE_COURSE="com.zhbit.cms.sqltools.DisposeCourse.UpdateCourse";
    String DELETE_COURSE="com.zhbit.cms.sqltools.DisposeCourse.DeleteCourse";

    String SELECT_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.SelectRoom";
    String CREATE_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.CreateRoom";
    String UPDATE_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.UpdateRoom";
    String DELETE_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.DeleteRoom";
    String FILTER_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.FilterRoom";
    String  QUERY_ROOM = "com.zhbit.cms.sqltools.DisposeBuildRoom.QueryRoom";
    String BINDING_BUUID="com.zhbit.cms.sqltools.DisposeBuildRoom.BindingBuuid";

    String SELECT_TERM = "com.zhbit.cms.sqltools.DisposeTerm.SelectTerm";
    String CREATE_TERM = "com.zhbit.cms.sqltools.DisposeTerm.CreateTerm";
    String UPDATE_TERM = "com.zhbit.cms.sqltools.DisposeTerm.UpdateTerm";
    String DELETE_TERM = "com.zhbit.cms.sqltools.DisposeTerm.DeleteTerm";
    String CURRENT_TERM= "com.zhbit.cms.sqltools.DisposeTerm.CurrentTerm";

}
