package com.softel.springboot.exception;

import com.softel.springboot.enums.ResultCode;

public class ResultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
