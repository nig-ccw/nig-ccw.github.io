package com.vcc.cem.ssm.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vcc.cem.ssm.shop.commons.constant.Validator;
import com.vcc.cem.ssm.shop.commons.persistence.BaseEntity;
import com.vcc.cem.ssm.shop.commons.util.Regex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class TbUser extends BaseEntity {
    @Length(min = Validator.USER_NAME_MIN, max = Validator.USER_NAME_MAX, message = Validator.USER_NAME_MESSAGE)
    private String username;

    @JsonIgnore
    private String password;

    @Pattern(regexp = Regex.PHONE, message = Validator.PHONE_MESSAGE)
    private String phone;

    @Pattern(regexp = Regex.EMAIL, message = Validator.EMAIL_MESSAGE)
    private String email;
}
