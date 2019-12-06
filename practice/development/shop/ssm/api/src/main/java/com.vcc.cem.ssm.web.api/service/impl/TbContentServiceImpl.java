package com.vcc.cem.ssm.web.api.service.impl;

import com.vcc.cem.ssm.shop.domain.TbContent;
import com.vcc.cem.ssm.shop.domain.TbContentCategory;
import com.vcc.cem.ssm.web.api.dao.TbContentDao;
import com.vcc.cem.ssm.web.api.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentDao tbContentDao;

    @Override
    public List<TbContent> selectByCategoryId(Long categoryId) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(categoryId);

        TbContent tbContent = new TbContent();
        tbContent.setTbContentCategory(tbContentCategory);

        return tbContentDao.selectByCategoryId(tbContent);
    }
}
