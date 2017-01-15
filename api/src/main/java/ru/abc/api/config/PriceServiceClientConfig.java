package ru.abc.api.config;

import feign.Request;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceServiceClientConfig {

    public Request.Options options() {
        return new Request.Options(5000, 5000);
    }
}
