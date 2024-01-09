package flab.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;
import java.util.Map;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    private RedisConnectionFactory redisConnectionFactory(int namespace) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
        lettuceConnectionFactory.setDatabase(namespace);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    public StringRedisTemplate signUpRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory(0));
    }


    @Bean
    public RedisTemplate<Long, Long> followRedisTemplate() {
        RedisTemplate<Long, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory(1));
        return template;
    }

    @Bean
    public RedisTemplate<Long, Long> newsFeedRedisTemplate() {
        RedisTemplate<Long, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory(2));
        return template;
    }
}
