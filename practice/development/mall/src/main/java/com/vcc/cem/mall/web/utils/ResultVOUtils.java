package com.vcc.cem.mall.web.utils;

import com.vcc.cem.mall.web.vo.ResultVO;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ResultVOUtils {

    static ResultVO fail(Integer code, String msg){
        return ResultVO.of(code, msg);
    }

    static ResultVO success(){
        return success(null);
    }

    static <T> ResultVO<T> success(T data){
        return ResultVO.of(data);
    }
}
