package com.gympass.kartrace.commons;

public enum LogFileColumnsEnum {

    PILOT_NUMBER(1),
    PILOT_NAME(3),
    LAP_NUM(4),
    LAP_TIME(5),
    LAP_AVERAGE_TIME(6)
    ;

    private final int column;

    LogFileColumnsEnum(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
