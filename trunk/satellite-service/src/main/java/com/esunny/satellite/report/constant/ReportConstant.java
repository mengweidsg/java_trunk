package com.esunny.satellite.report.constant;

public interface ReportConstant {

    public static final String DOC_PATH         = "svn://svn.esunny.com/esunnysource/doc/aone";
    /** 下拉框选择全部 */
    public static final String ESUNNY_ALL       = "esunny_all";
    /** 默认分隔符【逗号】 */
    public static final String DEFULT_SPLIT_STR = ",";

    /**
     * 搜索条件表
     */
    public static interface ReportSearchCondition {

        public static String ID                  = "ID";
        public static String TABLE_NAME          = "TABLE_NAME";
        public static String COLUMN_NAME         = "COLUMN_NAME";
        public static String COLUMN_DISPLAY_NAME = "COLUMN_DISPLAY_NAME";
        public static String INPUT_TYPE          = "INPUT_TYPE";
        public static String ENUM_TABLE_NAME     = "ENUM_TABLE_NAME";
        public static String IS_COMPLETE_MATCH   = "IS_COMPLETE_MATCH";
    }

    /**
     * 搜索条件表
     */
    public static interface ReportDisplayColumn {

        public static String ID                  = "ID";
        public static String TABLE_NAME          = "TABLE_NAME";
        public static String COLUMN_NAME         = "COLUMN_NAME";
        public static String COLUMN_DISPLAY_NAME = "COLUMN_DISPLAY_NAME";
        public static String IS_ENUM             = "IS_ENUM";
        public static String ENUM_TABLE_NAME     = "ENUM_TABLE_NAME";
        public static String SORT                = "SORT";
        public static String IS_SUM              = "IS_SUM";
        public static String COLUMN_DATA_TYPE    = "COLUMN_DATA_TYPE";
        public static String IS_GROUP_BY         = "IS_GROUP_BY";
    }

    /**
     * report_menu
     */
    public static interface ReportMenu {

        public static String ID          = "ID";
        public static String TABLE_NAME  = "TABLE_NAME";
        public static String NAME        = "NAME";
        public static String URL         = "URL";
        public static String MENU_LEVEL  = "MENU_LEVEL";
        public static String PARENT_ID   = "PARENT_ID";
        public static String REPORT_NAME = "REPORT_NAME";
    }

    /**
     * 输入控件类型
     */
    public interface INPUT_TYPE {

        /** 文本框 */
        int TEXT        = 1;

        /** 下拉框 */
        int SELECT      = 2;

        /** 日期控件 */
        int DATE        = 3;

        /** 日期(from - to)控件 */
        int DOUBLE_DATE = 4;

        /** 链接控件 */
        int LI_LINK     = 5;

        /** 隐藏控件 */
        int HIDDEN      = 6;

        /** 其他控件 */
        int OTHER       = 99;
    }

    /**
     * 是否完全匹配
     */
    public interface COMPLETE_MATCH {

        /** 完全匹配 */
        int YES = 1;

        /** 模糊匹配 */
        int NO  = 0;

    }

    /**
     * 是否sum
     */
    public interface SumColumn {

        /** 是 */
        int YES = 1;

        /** 否 */
        int NO  = 0;

    }

    /**
     * 是否有groupby
     */
    public interface GroupByColumn {

        /** 有 */
        int YES = 1;

        /** 无 */
        int NO  = 0;
    }

    /**
     * 是否enum
     */
    public interface IsEnum {

        /** 是 */
        int YES = 1;

        /** 否 */
        int NO  = 0;
    }

    /**
     * 是否enum
     */
    public interface MenuLeval {

        /** 一级菜单 */
        String FIRST  = "1";

        /** 二级菜单 */
        String SECOND = "2";

        /** 三级菜单 */
        String THIRD  = "3";
    }

    public interface UserAuthority {

        /** 普通用户 */
        int USER        = 1;
        /** 管理员 */
        int ADMIN       = 2;
        /** 超级管理员 */
        int SUPER_ADMIN = 9;
    }

    public interface AuthorityType {

        /** 普通用户 */
        int REPORT      = 1;
        /** 管理员 */
        int ADMIN       = 2;
        /** 全员 */
        int ALL         = 3;
        /** 超级管理员 */
        int SUPER_ADMIN = 99;
    }
}
