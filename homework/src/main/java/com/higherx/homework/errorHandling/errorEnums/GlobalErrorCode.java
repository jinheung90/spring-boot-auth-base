package com.higherx.homework.errorHandling.errorEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor

@Getter
public enum GlobalErrorCode implements IErrorCode {
    BAD_REQUEST("bad request", 400),
    SEND_FAIL("not connected child server", 500),
    ASYNC_FAIL("ASYNC_FAIL", 500),
    SQL_ERROR("SQL_ERROR", 500),
    IO_ERROR("io error", 500),
    NOT_EXISTS_USER("not exists user", 400),
    ALREADY_EXISTS_USER_ID("already exist user id",  400),
    ALREADY_EXISTS_NICKNAME("already exist user nickname",  400),
    ALREADY_EXISTS_CRN("already exist crn",  400),
    NOT_ALLOW_USER("not allow user", 401),
    NOT_VALID_TOKEN("not valid access token", 403),
    ;

    private String message;
    private int status;
    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return this.message;
    }
    @Override
    public String getCode() {
        // TODO Auto-generated method stub
        return this.toString();
    }
    @Override
    public int getStatus() {

        return this.status;
    }
}
