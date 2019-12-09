package com.vcc.cem.ssm.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vcc.cem.ssm.shop.commons.constant.Validation;
import com.vcc.cem.ssm.shop.commons.persistence.BaseEntity;
import com.vcc.cem.ssm.shop.commons.util.Regex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class TbUser extends BaseEntity {
    @Length(min = Validation.USER_NAME_MIN, max = Validation.USER_NAME_MAX, message = Validation.USER_NAME_MESSAGE)
    private String username;

    @JsonIgnore
    private String password;

    @Pattern(regexp = Regex.PHONE, message = Validation.PHONE_MESSAGE)
    private String phone;

    @Pattern(regexp = Regex.EMAIL, message = Validation.EMAIL_MESSAGE)
    private String email;
}
