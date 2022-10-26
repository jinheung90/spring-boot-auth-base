package com.higherx.homework.errorHandling.customRuntimeException;

import com.higherx.homework.errorHandling.errorEnums.IErrorCode;



public class RuntimeExceptionWithCode extends RuntimeException {

    private IErrorCode code;
    private String message;


    public RuntimeExceptionWithCode(IErrorCode code) {
        this.message = code.getMessage();
        this.code = code;
    }

    public RuntimeExceptionWithCode(IErrorCode code, String customMessage) {
        this.code = code;
        this.message = customMessage;
    }

    public IErrorCode getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
