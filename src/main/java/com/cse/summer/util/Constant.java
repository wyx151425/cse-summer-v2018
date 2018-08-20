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

    public static final class Role {
        public static final int COMMON = 1;
        public static final int DESIGNER = 2;
    }
}
