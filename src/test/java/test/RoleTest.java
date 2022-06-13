package test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Role;
import service.role.RoleService;

import java.util.List;

public class RoleTest {
    private Logger logger = Logger.getLogger(RoleTest.class);

    @Test
    public void getId() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        RoleService roleService = (RoleService) ctx.getBean("roleService");
        List<Role> roleList;

        roleList = roleService.getRoleList();

        for( Role role :roleList){
            logger.debug("角色ID: " + role.getId()+
                    " 角色代码: "+role.getRoleCode()+
                    " 角色名称:"+ role.getRoleName());
        }
    }
}
