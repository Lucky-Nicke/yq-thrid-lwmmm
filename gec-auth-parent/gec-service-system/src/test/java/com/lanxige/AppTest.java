package com.lanxige;

import com.lanxige.mapper.SysRoleMapper;
import com.lanxige.model.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void test(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }

    @Test
    public void test2(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("Nicke");
        sysRole.setRoleCode("admin");
        sysRole.setDescription("管理员");

        int row = this.sysRoleMapper.insert(sysRole);
        System.out.println(row);
    }

    @Test
    public void test3(){
        SysRole role = this.sysRoleMapper.selectById(1L);
        role.setDescription("超级管理员2");

        int row = this.sysRoleMapper.updateById(role);
        System.out.println(row);
    }

    @Test
    public void test4(){
        int row = this.sysRoleMapper.deleteById(1L);
        System.out.println(row);
    }

    @Test
    public void test5(){
        int row = this.sysRoleMapper.deleteBatchIds(Arrays.asList(1L,2L));
        System.out.println(row);
    }
}
