package com.fayaz.taskmanagement.task.enums;

import java.util.HashMap;
import java.util.Map;

public enum StatusEnum {
    READY("Ready For Development"),
    DEVELOPING("Developing"),
    BLOCKED("Blocked"),
    QA_TESTING("QA Testing"),
    DONE("Done");

    private String status;

    // Map for reverse lookup
    private static final Map<String, StatusEnum> LOOKUP = new HashMap<>();

    // Populate the map once
    static {
        for (StatusEnum s : StatusEnum.values()) {
            LOOKUP.put(s.getStatus(), s);
        }
    }

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // Value → Key
    public static StatusEnum fromValue(String value) {
        return LOOKUP.get(value);
    }
}

