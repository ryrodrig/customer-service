package com.cfgtest.services.customerservice.api.exceptionhandlers;

import com.cfgtest.services.customerservice.model.ErrorDetails;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerServiceException extends RuntimeException {

    private ErrorDetails.ResultEnum resultEnum;

    private String sourceSystem;

    private String traceId;

    public CustomerServiceException(ErrorDetails.ResultEnum resultEnum, String sourceSystem, String traceId, Integer errorCode) {
        this.resultEnum = resultEnum;
        this.sourceSystem = sourceSystem;
        this.traceId = traceId;
        this.errorCode = errorCode;
    }

    public CustomerServiceException(String message, ErrorDetails.ResultEnum resultEnum, String sourceSystem, String traceId, Integer errorCode) {
        super(message);
        this.resultEnum = resultEnum;
        this.sourceSystem = sourceSystem;
        this.traceId = traceId;
        this.errorCode = errorCode;
    }

    public CustomerServiceException(String message, Throwable cause, ErrorDetails.ResultEnum resultEnum, String sourceSystem, String traceId, Integer errorCode) {
        super(message, cause);
        this.resultEnum = resultEnum;
        this.sourceSystem = sourceSystem;
        this.traceId = traceId;
        this.errorCode = errorCode;
    }

    public CustomerServiceException(Throwable cause, ErrorDetails.ResultEnum resultEnum, String sourceSystem, String traceId, Integer errorCode) {
        super(cause);
        this.resultEnum = resultEnum;
        this.sourceSystem = sourceSystem;
        this.traceId = traceId;
        this.errorCode = errorCode;
    }

    private Integer errorCode;


}
