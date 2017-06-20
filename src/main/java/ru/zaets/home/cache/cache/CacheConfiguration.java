package ru.zaets.home.cache.cache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dzaets on 21.06.2017.
 * cache-example
 */
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private CacheManager cacheManager;

    @Bean
    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        cacheManager = createInMemoryCacheManager();
        return new JCacheCacheManager(cacheManager);
    }

    @PreDestroy
    public void destroy() {
        log.info("Close Cache Manager");
        cacheManager.close();
    }

    private CacheManager createInMemoryCacheManager() {
        Map<String, org.ehcache.config.CacheConfiguration<?, ?>> caches = new HashMap<>();


        long cacheSize = 200000;
        long ttl = 3600; // second

        ResourcePoolsBuilder.newResourcePoolsBuilder();
        org.ehcache.config.CacheConfiguration<Object, Object> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(cacheSize))
                .withExpiry(Expirations.timeToLiveExpiration(new org.ehcache.expiry.Duration(ttl, TimeUnit.SECONDS)))
                .build();


        caches.put("address", cacheConfiguration);

        XmlConfiguration xmlConfiguration = new XmlConfiguration(getClass().getResource("/template-sample.xml"));
        CacheConfigurationBuilder<Object, Object> configurationBuilder = null;
        try {
            configurationBuilder = xmlConfiguration.newCacheConfigurationBuilderFromTemplate("example", Object.class, Object.class);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        configurationBuilder = configurationBuilder.withResourcePools(ResourcePoolsBuilder.heap(1000));

        org.ehcache.config.CacheConfiguration<Object, Object> sampleXmlConfiguration = configurationBuilder.build();

        caches.put("example", sampleXmlConfiguration);

        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        DefaultConfiguration configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
        return provider.getCacheManager(provider.getDefaultURI(), configuration);
    }

}