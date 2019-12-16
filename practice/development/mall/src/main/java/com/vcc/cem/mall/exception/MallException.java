package com.vcc.cem.mall.exception;

import com.vcc.cem.mall.enums.ResultEnum;

/**
 * @author czh
 * @date 2019-12-10
 */
public class MallException extends RuntimeException{

    private Integer code;

    public MallException(ResultEnum resultEnum){
        super(resultEnum.getMessege());
        this.code = resultEnum.getCode();
    }

    public MallException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
}
