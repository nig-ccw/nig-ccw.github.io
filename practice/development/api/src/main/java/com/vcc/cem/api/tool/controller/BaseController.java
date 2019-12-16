package com.vcc.cem.api.tool.controller;

import com.vcc.cem.api.tool.config.IPorperties;
import com.vcc.cem.api.tool.constant.Constant;
import com.vcc.cem.api.tool.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author czh
 * @date 2019-12-13
 */
public class BaseController {
    @Autowired
    protected IPorperties porperties;

    protected void checkResult(Result result){
        if (Objects.isNull(result)) {
            throw new RuntimeException("result is null.");
        }

        if (Constant.GAODE_RESULT_CODE_FAIL.equals(result.getStatus())) {
            throw new RuntimeException(result.getInfo());
        }
    }
}
