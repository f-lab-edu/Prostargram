package flab.project.config;

import flab.project.data.dto.cache.VoteCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class VoteRedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory voteRedisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, VoteCache> voteRedisTemplate() {
        RedisTemplate<String, VoteCache> voteRedisTemplate = new RedisTemplate<>();
        voteRedisTemplate.setConnectionFactory(voteRedisConnectionFactory());

        voteRedisTemplate.setKeySerializer(new StringRedisSerializer());
        voteRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(VoteCache.class));

        return voteRedisTemplate;
    }
}