package com.vcc.cem.ssm.shop.domain;

import com.vcc.cem.ssm.shop.commons.constant.Validation;
import com.vcc.cem.ssm.shop.commons.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 内容管理
 * <p>Title: TbContent</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/6/25 14:02
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TbContent extends BaseEntity {
    @Length(min=Validation.TITLE_MIN, max=Validation.TITLE_MAX, message=Validation.TITLE_MESSAGE)
    private String title;

    @Length(min=Validation.SUB_TITLE_MIN, max=Validation.SUB_TITLE_MAX, message=Validation.SUB_TITLE_MESSAGE)
    private String subTitle;

    @Length(min=Validation.TITLE_DESC_MIN, max=Validation.TITLE_DESC_MAX, message=Validation.TITLE_DESC_MESSAGE)
    private String titleDesc;

    private String url;
    private String pic;
    private String pic2;

    @Length(min=Validation.CONTENT_MIN, message=Validation.CONTENT_MESSAGE)
    private String content;

    @NotNull(message=Validation.CONTENT_CATEGORY_MESSAGE)
    private TbContentCategory tbContentCategory;
}
