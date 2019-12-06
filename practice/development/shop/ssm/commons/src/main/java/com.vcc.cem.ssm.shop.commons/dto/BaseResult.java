package com.vcc.cem.ssm.shop.commons.dto;

import lombok.Data;
import java.io.Serializable;
import com.vcc.cem.ssm.shop.commons.constant.Constant;

/**
 * 自定义返回结果
 * <p>Title: BaseResult</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/6/19 14:31
 */
@Data
public final class BaseResult implements Serializable {
    private int code;
    private String message;
    private Object data;

    private BaseResult(){}

    public static BaseResult success() {
        return of(Constant.CODE_SUCCESS, Constant.MESSAGE_SUCCESS, Constant.NULL);
    }

    public static BaseResult success(String message) {
        return of(Constant.CODE_SUCCESS, message, Constant.NULL);
    }

    public static BaseResult success(String message, Object data) {
        return of(Constant.CODE_SUCCESS, message, data);
    }

    public static BaseResult fail() {
        return of(Constant.CODE_FAIL, Constant.MESSAGE_FAIL, Constant.NULL);
    }

    public static BaseResult fail(String message) {
        return of(Constant.CODE_FAIL, message, Constant.NULL);
    }

    public static BaseResult fail(int status, String message) {
        return of(status, message, Constant.NULL);
    }

    private static BaseResult of(int code, String message, Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }
}
