package com.homura.common.constant;

import lombok.Data;

@Data
public class ReturnResult {
    private boolean flag;
    private String errorCode;
    private String message;
    private Object result;
}
