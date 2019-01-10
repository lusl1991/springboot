package com.softel.springboot.util;

import com.softel.springboot.enums.ResultCode;
import lombok.Data;

@Data
public class Result {

    private int code;
    private String msg;
    private Object data;

    public Result() {
    	
    }

    public Result(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
