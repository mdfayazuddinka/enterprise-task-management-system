package com.fayaz.taskmanagement.CustomException;

public class TaskCreationException extends RuntimeException {
    public TaskCreationException(String message) {
        super(message);
    }
}
