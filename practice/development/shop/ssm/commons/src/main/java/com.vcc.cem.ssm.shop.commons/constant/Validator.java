package com.vcc.cem.ssm.shop.commons.constant;

/**
 * @author czh
 * @date 2019-12-06
 */
public interface Validator {
    int USER_NAME_MIN=6;
    int USER_NAME_MAX=20;
    int TITLE_MIN=1;
    int TITLE_MAX=20;
    int SUB_TITLE_MIN=1;
    int SUB_TITLE_MAX=20;
    int TITLE_DESC_MIN=1;
    int TITLE_DESC_MAX=20;
    int CONTENT_MIN = 1;
    int CONTENT_CATEGORY_NAME_MIN = 1;
    int CONTENT_CATEGORY_NAME_MAX = 20;
    String USER_NAME_MESSAGE="姓名的长度必须介于 " + USER_NAME_MIN + " - " + USER_NAME_MAX + " 位之间";
    String PHONE_MESSAGE="手机号格式不正确";
    String EMAIL_MESSAGE="邮箱格式不正确";
    String TITLE_MESSAGE="标题长度介于 " + TITLE_MIN + " - " + TITLE_MAX + " 个字符之间";
    String SUB_TITLE_MESSAGE="子标题长度介于 " + SUB_TITLE_MIN + " - " + SUB_TITLE_MAX + " 个字符之间";
    String TITLE_DESC_MESSAGE="标题描述长度介于 " + TITLE_DESC_MIN + " - " + TITLE_DESC_MAX + " 个字符之间";
    String CONTENT_MESSAGE = "内容不能为空";
    String CONTENT_CATEGORY_MESSAGE = "父级类目不能为空";
    String CONTENT_CATEGORY_NAME_MESSAGE = "分类名称必须介于 " + CONTENT_CATEGORY_NAME_MIN + " - " + CONTENT_CATEGORY_NAME_MAX + " 位之间";
    String CONTENT_CATEGORY_ORDER_MESSAGE = "排序不能为空";
}
