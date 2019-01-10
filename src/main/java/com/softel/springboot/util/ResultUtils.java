package com.softel.springboot.util;

import com.softel.springboot.enums.ResultCode;

public class ResultUtils {
	
	public static Result success(){
		return success(null);
	}

	public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result warn(ResultCode resultCode, Object data) {
        Result result = new Result(resultCode, data);
        return result;
    }
    
    public static Result warn(ResultCode resultCode) {
        return new Result(resultCode);
    }

}
