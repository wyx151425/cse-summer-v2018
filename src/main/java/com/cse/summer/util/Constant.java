package com.cse.summer.util;

public class Constant {
    public static final String USER = "user";

    public static final class DocType {
        public static final String XML = "text/xml";
        public static final String XLS = "application/vnd.ms-excel";
        public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static final String XLSX_UTF8 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
    }

    public static final class MachineType {
        public static final String MAN = "MAN";
        public static final String WIN_GD = "WinGD";
        public static final String CSE = "CSE";
    }

    public static final class Database {
        public static final String DEV = "DEV";
        public static final String UAT = "UAT";
        public static final String PRO = "PRO";
        public static final class Name {
            public static final String DMS_DEV = "dms_dev";
            public static final String DMS_UAT = "dms_uat";
            public static final String DMS_PRO = "dms_pro";
        }
    }

    public static final class Role {
        public static final int COMMON = 1;
        public static final int DESIGNER = 2;
    }

    public static final class Status {
        public static final int DISABLE = 0;
        public static final int ENABLE = 1;
    }

    public static final class Roles {
        public static final String USER = "ROLE_USER";
        public static final String STRUCTURE_MANAGER = "ROLE_STRUCTURE_MANAGER";
        public static final String PROJECT_MANAGER = "ROLE_PROJECT_MANAGER";
        public static final String CHIEF_DESIGNER = "ROLE_CHIEF_DESIGNER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    public static final class Permissions {
        /**
         * 部套负责人权限
         */
        public static final String IMPORT_MACHINE_BOM = "IMPORT_MACHINE_BOM";
        public static final String EXPORT_MACHINE_BOM = "EXPORT_MACHINE_BOM";
        public static final String EXPORT_STRUCTURE = "EXPORT_STRUCTURE";

        /**
         * 项目负责人权限
         */
        public static final String UPDATE_MACHINE_INFO = "UPDATE_MACHINE_INFO";
        public static final String RELEASE_STRUCTURE = "RELEASE_STRUCTURE";
        public static final String UPDATE_STRUCTURE_VERSION = "UPDATE_STRUCTURE_VERSION";
        public static final String APPEND_STRUCTURE = "APPEND_STRUCTURE";

        /**
         * 主任设计师权限
         */
        public static final String IMPORT_NEW_MACHINE_BOM = "IMPORT_NEW_MACHINE_BOM";
        public static final String DELETE_STRUCTURE = "DELETE_STRUCTURE";
        public static final String IMPORT_STRUCTURE = "IMPORT_STRUCTURE";

        /**
         * 管理员权限
         */
        public static final String UPDATE_PERMISSION = "UPDATE_PERMISSION";
        public static final String DELETE_ALL_MACHINE = "DELETE_ALL_MACHINE";
    }
}
