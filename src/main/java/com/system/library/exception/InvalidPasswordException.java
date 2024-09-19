package com.system.library.exception;


import com.system.library.util.enums.ErrorEnum;

public class InvalidPasswordException extends RuntimeException{

    public String errorCode;

    public String errorMessage;

    public InvalidPasswordException(){
        this.errorCode = ErrorEnum.INVALID_PASSWORD.code;
        this.errorMessage = ErrorEnum.INVALID_PASSWORD.message;
    }
}
