package com.vcc.cem.ssm.shop.domain;

import com.vcc.cem.ssm.shop.commons.constant.Validator;
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
    @Length(min=Validator.TITLE_MIN, max=Validator.TITLE_MAX, message=Validator.TITLE_MESSAGE)
    private String title;

    @Length(min=Validator.SUB_TITLE_MIN, max=Validator.SUB_TITLE_MAX, message=Validator.SUB_TITLE_MESSAGE)
    private String subTitle;

    @Length(min=Validator.TITLE_DESC_MIN, max=Validator.TITLE_DESC_MAX, message=Validator.TITLE_DESC_MESSAGE)
    private String titleDesc;

    private String url;
    private String pic;
    private String pic2;

    @Length(min=Validator.CONTENT_MIN, message=Validator.CONTENT_MESSAGE)
    private String content;

    @NotNull(message=Validator.CONTENT_CATEGORY_MESSAGE)
    private TbContentCategory tbContentCategory;
}
