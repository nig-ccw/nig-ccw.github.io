package com.vcc.cem.ssm.shop.admin;

import com.vcc.cem.ssm.shop.admin.service.TbUserService;
import com.vcc.cem.ssm.shop.domain.TbUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserServiceTest {
    @Autowired
    private TbUserService tbUserService;

    @Test
    public void testSelectAll() {
        List<TbUser> tbUsers = tbUserService.selectAll();
        for (TbUser tbUser : tbUsers) {
            System.out.println(tbUser.getUsername());
        }
    }

    @Test
    public void testInsert() {
        TbUser tbUser = new TbUser();
        tbUser.setUsername("vcc12345");
        tbUser.setPhone("15888888888");
        tbUser.setEmail("vcc@vcc.com");
        tbUser.setPassword("vcc12345");
        tbUser.setCreateDate(new Date());
        tbUser.setUpdateDate(new Date());

        tbUserService.save(tbUser);
    }

    @Test
    public void testDelete() {
        tbUserService.delete(1L);
    }

    @Test
    public void testGetById() {
        TbUser tbUser = tbUserService.getById(1L);
        if (tbUser != null) {
            System.out.println(tbUser.getUsername());
        }
    }

    @Test
    public void testUpdate() {
        TbUser tbUser = tbUserService.getById(1L);
        if (tbUser != null) {
            tbUser.setUsername("Lusifer2");
        }
        tbUserService.update(tbUser);
    }

    @Test
    public void login(){
        tbUserService.login("vcc@vcc.com","vcc12345");
    }

    @Test
    public void testMD5() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }
}
