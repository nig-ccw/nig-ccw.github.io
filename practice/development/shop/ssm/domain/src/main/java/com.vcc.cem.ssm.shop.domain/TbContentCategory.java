package com.vcc.cem.ssm.shop.domain;

import com.vcc.cem.ssm.shop.commons.constant.Validation;
import com.vcc.cem.ssm.shop.commons.persistence.BaseTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 分类管理
 * <p>Title: TbContentCategory</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/6/25 9:14
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TbContentCategory extends BaseTreeEntity<TbContentCategory> {
    @Length(min=Validation.CONTENT_CATEGORY_NAME_MIN, max=Validation.CONTENT_CATEGORY_NAME_MAX, message=Validation.CONTENT_CATEGORY_NAME_MESSAGE)
    private String name;
    private Integer status;

    @NotNull(message=Validation.CONTENT_CATEGORY_ORDER_MESSAGE)
    private Integer order;
//    private Boolean isParent;
//    private TbContentCategory parent;
}
