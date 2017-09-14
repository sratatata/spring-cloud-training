package pl.training.cloud.common.feign;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

@Configuration
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty(value = "feign.oauth2.enabled", matchIfMissing = true)
public class OAuth2FeignAutoConfiguration {

    @ConditionalOnBean(OAuth2ClientContext.class)
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor();
    }

}