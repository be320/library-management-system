package com.system.library.exception;

import com.system.library.util.enums.ErrorEnum;

public class EntityNotFoundException extends RuntimeException{

    public String errorCode;

    public String errorMessage;

    public EntityNotFoundException(){
        this.errorCode = ErrorEnum.ENTITY_NOT_FOUND.code;
        this.errorMessage = ErrorEnum.ENTITY_NOT_FOUND.message;
    }
}
