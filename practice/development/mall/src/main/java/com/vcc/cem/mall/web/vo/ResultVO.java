package com.vcc.cem.mall.web.vo;

import lombok.Data;

/**
 * @author czh
 * @date 2019-12-10
 */
@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;

    private ResultVO(Integer code,String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultVO<T> of(T data){
        return new ResultVO<>(0, "ok", data);
    }

    public static ResultVO of(Integer code, String msg){
        return new ResultVO(code, msg, null);
    }
}
