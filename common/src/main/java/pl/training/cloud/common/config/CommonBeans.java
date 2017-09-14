package pl.training.cloud.common.config;

import feign.RequestInterceptor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.cloud.common.feign.OAuth2FeignRequestInterceptor;
import pl.training.cloud.common.model.Mapper;

@EnableCircuitBreaker
@EnableFeignClients(basePackages = "pl.training.cloud")
@EnableCaching
@EnableDiscoveryClient
@Configuration
public class CommonBeans {

    @Bean
    public Mapper mapper(MessageSource messageSource) {
        return new Mapper(messageSource);
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor();
    }

}
