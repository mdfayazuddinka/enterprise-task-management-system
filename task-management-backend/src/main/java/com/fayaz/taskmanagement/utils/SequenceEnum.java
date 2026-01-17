package com.fayaz.taskmanagement.utils;

public enum SequenceEnum {
    USER_SEQUENCE("user_sequence"),
    TASK_SEQUENCE("task_sequence"),
    PROJECT_SEQUENCE("project_sequence"),
    SUB_TASK_SEQUENCE("sub_task_sequence");

    private String sequenceName;

    SequenceEnum(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSequenceName() {
        return sequenceName;
    }
}
