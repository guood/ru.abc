package ru.abc.common.dto;

import java.beans.Transient;
import java.io.Serializable;

public class Response implements Serializable {
    public static final String OK = "0";
    public static final String SYSTEM_ERROR_CODE = "1";
    public static final String NOT_FOUND_CODE = "2";
    public static final String VALIDATION_ERROR_CODE = "3";

    private String errorCode = OK;
    private String errorMessage = "";

    public Response() {
    }

    public Response(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Transient
    public boolean isOk() {
        return OK.equals(errorCode);
    }

    @Transient
    public boolean isNotFound() {
        return NOT_FOUND_CODE.equals(errorCode);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("errorCode=").append(errorCode).append(", ")
                .append("errorMessage=").append(errorMessage)
                .toString();
    }

}
