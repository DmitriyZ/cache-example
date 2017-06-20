package ru.zaets.home.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * Created by dzaets on 21.06.2017.
 * cache-example
 */
@Service
public class CachedValueService {


    @Cacheable(value = "address", keyGenerator = "addressKeyGenerator")
    public String getAddress(double lon, double lat) {
        System.out.println("get real: " + lon + " " + lat);
        return "1" + lon + "_" + lat;
    }

    @Cacheable(value = "example")
    public String getExample(String v) {
        System.out.println("get real: " + v);
        return "1" + v;
    }
}
