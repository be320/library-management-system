package com.system.library.exception;

import com.system.library.util.enums.ErrorEnum;

public class UserNotAuthorizedException extends RuntimeException{

    public String errorCode;

    public String errorMessage;

    public UserNotAuthorizedException(){
        errorCode = ErrorEnum.USER_NOT_AUTHORIZED.code;
        errorMessage = ErrorEnum.USER_NOT_AUTHORIZED.message;
    }
}
