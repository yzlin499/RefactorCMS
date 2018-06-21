package com.zhbit.cms.sqltools;

public final class S {
    private S(){}
    private static final String PACKAGE="com.zhbit.cms.sqltools.";

    public final static class USER{
        private USER(){}
        private static final String PACKAGE= S.PACKAGE+"DisposeUser.";

        public static final String REGISTER     =PACKAGE+"DisposeUser.Register";
        public static final String LOGIN        =PACKAGE+"DisposeUser.LoginUser";
        public static final String SELECT_PERSON=PACKAGE+"DisposeUser.SelectPerson";
    }

    public final static class COURSE{
        private COURSE(){}
        private static final String PACKAGE= S.PACKAGE+"DisposeCourse.";

        public static final String CREATE=PACKAGE+"CreateCourse";
        public static final String FILTER=PACKAGE+"FilterCourse";
        public static final String UPDATE=PACKAGE+"UpdateCourse";
        public static final String DELETE=PACKAGE+"DeleteCourse";
    }

    public final static class ROOM {
        private ROOM() {}
        private static final String PACKAGE = S.PACKAGE + "DisposeBuildRoom.";

        public static final String SELECT = PACKAGE+"SelectRoom";
        public static final String CREATE = PACKAGE+"CreateRoom";
        public static final String UPDATE = PACKAGE+"UpdateRoom";
        public static final String DELETE = PACKAGE+"DeleteRoom";
        public static final String FILTER = PACKAGE+"FilterRoom";
        public static final String  QUERY = PACKAGE+"QueryRoom";

    }

    public final static class TERM{
        private TERM(){}
        private static final String PACKAGE = S.PACKAGE + "DisposeTerm.";

        public static final String SELECT = PACKAGE+"SelectTerm";
        public static final String CREATE = PACKAGE+"CreateTerm";
        public static final String UPDATE = PACKAGE+"UpdateTerm";
        public static final String DELETE = PACKAGE+"DeleteTerm";
        public static final String CURRENT= PACKAGE+"CurrentTerm";
    }

    public final static class OTHER{
        private OTHER(){}
        private static final String PACKAGE = S.PACKAGE + "other.";

        public static final String GET_TABLE_FIELD = PACKAGE+"GetTableField";
    }

    public static final String BINDING_BUUID=PACKAGE+"DisposeBuildRoom.BindingBuuid";

}