package com.vcc.cem.ssm.shop.commons.persistence;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类的基类
 * <p>Title: BaseEntity</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/6/21 15:28
 */
@Data
public abstract class BaseEntity implements Serializable {
    private Long id;
    private Date createDate;
    private Date updateDate;
}
