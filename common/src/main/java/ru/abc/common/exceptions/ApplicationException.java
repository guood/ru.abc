package ru.abc.common.exceptions;

public class ApplicationException extends Exception {

    private String errorCode;
    private String messageCode;
    private Object[] params;

    public ApplicationException(String errorCode, String messageCode, Object... params) {
        this.errorCode = errorCode;
        this.messageCode = messageCode;
        this.params = params;
    }

    public ApplicationException(String errorCode, String messageCode, Throwable cause, Object... params) {
        super(cause);
        this.errorCode = errorCode;
        this.messageCode = messageCode;
        this.params = params;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public Object[] getParams() {
        return params;
    }
}
