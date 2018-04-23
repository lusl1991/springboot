package com.softel.springboot.base;

import com.softel.springboot.enums.ResultCode;
import com.softel.springboot.util.Result;

public class BaseController {
	
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
