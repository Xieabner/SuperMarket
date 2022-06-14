package test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Provider;
import service.Provider.ProviderService;
import service.user.UserService;

import java.util.List;

public class Providertest {



    private Logger logger = Logger.getLogger(UserTest.class);

    @Test
    public void testGetProviderCount() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        ProviderService providerService = (ProviderService) ctx.getBean("providerService");
        int count=0;
        String proName="";
        String proCode="";
        count = providerService.getProviderCount(proName,proCode);
        logger.debug("共有"+count+"条记录");
    }


    @Test
    public void testGetProviderListByPage() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        ProviderService providerService = (ProviderService) ctx.getBean("providerService");
        int count = 0;
        String proName = null;
        String proCode = null;
        int currentPageNo = 0;
        int pageSize = 5;
        List<Provider> providerList = providerService.getProviderListByPage(proName, proCode, currentPageNo, pageSize);
        logger.debug("当前记录从"+currentPageNo+1+"开始");
        logger.debug("共显示"+pageSize+"条记录");
        for (Provider provider : providerList) {
            logger.debug("ID:" + provider.getId() +
                    " 供应商编码：" + provider.getProCode() +
                    " 供应商名称:" + provider.getProName());
        }
    }
}
