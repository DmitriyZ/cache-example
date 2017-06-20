package ru.zaets.home.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.zaets.home.cache.key.AddressKey;
import ru.zaets.home.cache.service.CachedValueService;

import java.io.File;


public class CacheExampleApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheExampleApplication.class);

    public static void main(String[] args) {
        try {
            AbstractApplicationContext applicationContext;
            File f = new File("application-context.xml");
            System.out.println("FILE: " + f.getAbsolutePath());
            if (f.exists()) {
                System.out.println("file");
                LOGGER.info("Read Spring config from file");
                applicationContext = new FileSystemXmlApplicationContext("file:" + f.getAbsolutePath());
            } else {
                System.out.println("classpath");
                LOGGER.info("Read Spring config from class path");
                applicationContext = new ClassPathXmlApplicationContext("./application-context.xml");
            }

            CachedValueService cachedValueService = (CachedValueService) applicationContext.getBean("cachedValueService");
            String address1 = cachedValueService.getAddress(1, 1);
            System.out.println("result # " + address1);
            String address2 = cachedValueService.getAddress(2, 2);
            System.out.println("result # " + address2);
            String address3 = cachedValueService.getAddress(1, 1);
            System.out.println("result # " + address3);

            System.out.println();
            System.out.println("Read from cache: ");
            CacheManager cacheManage = (CacheManager) applicationContext.getBean("cacheManager");
            System.out.println(cacheManage.getCache("address").get(new AddressKey(1,1, null)).get());

            System.out.println();
            System.out.println();
            String example = cachedValueService.getExample("111111");
            System.out.println("result # " + example);
            example= cachedValueService.getExample("222222");
            System.out.println("result # " + example);
            example = cachedValueService.getExample("111111");
            System.out.println("result # " + example);



        } catch (Exception e) {
            LOGGER.warn("Error during initializing spring context", e);
        }

    }
}
