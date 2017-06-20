package ru.zaets.home.cache.key;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author <a href="mailto:sofyenkov@omnicomm.ru">Александр Софьенков</a>
 */

@Component
public class AddressKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        double longitude = (double) params[0];
        double latitude = (double) params[1];
        String locale = null;
        if (params.length == 3) {
            locale = (String) params[2];
        }
        return new AddressKey(longitude, latitude, locale);
    }
}
