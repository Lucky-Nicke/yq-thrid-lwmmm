package com.lanxige;

import com.lanxige.model.system.SysRole;
import com.lanxige.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = ServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest2 {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void test() {
        List<SysRole> roles = sysRoleService.list();
        for (SysRole role : roles) {
            System.out.println(role);
        }
    }
}
