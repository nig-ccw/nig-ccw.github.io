package com.vcc.cem.ssm.shop.admin.service.service.impl;

import com.vcc.cem.ssm.shop.admin.service.dao.TbContentDao;
import com.vcc.cem.ssm.shop.admin.service.service.TbContentService;
import com.vcc.cem.ssm.shop.commons.dto.BaseResult;
import com.vcc.cem.ssm.shop.commons.validator.BeanValidator;
import com.vcc.cem.ssm.shop.domain.TbContent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class TbContentServiceImpl extends AbstractBaseServiceImpl<TbContent, TbContentDao> implements TbContentService {

    @Override
    @Transactional(readOnly = false)
    public BaseResult save(TbContent tbContent) {
        String validator = BeanValidator.validator(tbContent);

        // 验证不通过
        if (validator != null) {
            return BaseResult.fail(validator);
        }

        // 验证通过
        else {
            tbContent.setUpdateDate(new Date());

            // 新增
            if (tbContent.getId() == null) {
                // 密码需要加密处理
                tbContent.setCreateDate(new Date());
                dao.insert(tbContent);
            }

            // 编辑用户
            else {
                update(tbContent);
            }

            return BaseResult.success("保存内容信息成功");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByCategoryId(String[] categoryIds) {
        dao.deleteByCategoryId(categoryIds);
    }
}
