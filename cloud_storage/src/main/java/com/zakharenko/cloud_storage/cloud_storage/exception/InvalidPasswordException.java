package com.zakharenko.cloud_storage.cloud_storage.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
