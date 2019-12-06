package com.vcc.cem.ssm.shop.commons.dto;

import com.vcc.cem.ssm.shop.commons.persistence.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据传输对象
 * <p>Title: PageInfo</p>
 * <p>Description: </p>
 *
 * @author vcc
 * @version 1.0.0
 * @date 2018/6/21 15:25
 */
@Data
public class PageInfo<T extends BaseEntity> implements Serializable {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
    private String error;
}
