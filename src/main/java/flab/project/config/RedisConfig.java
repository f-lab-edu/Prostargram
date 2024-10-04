package flab.project.config;

import flab.project.domain.post.model.BasePost;
import flab.project.domain.user.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public StringRedisTemplate redisTemplate(
            RedisConnectionFactory redisConnectionFactory
    ) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }

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

        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setConnectionFactory(redisConnectionFactory(1));

        return template;
    }

    @Bean
    public RedisTemplate<Long, Long> newsFeedRedisTemplate() {
        RedisTemplate<Long, Long> template = new RedisTemplate<>();

        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setConnectionFactory(redisConnectionFactory(2));

        return template;
    }

    @Bean
    public RedisTemplate<Long, BasePost> postRedisTemplate() {
        RedisTemplate<Long, BasePost> template = new RedisTemplate<>();

        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(BasePost.class));
        template.setConnectionFactory(redisConnectionFactory(3));

        return template;
    }

    @Bean
    public RedisTemplate<Long, Profile> userRedisTemplate() {
        RedisTemplate<Long, Profile> template = new RedisTemplate<>();

        template.setKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Profile.class));
        template.setConnectionFactory(redisConnectionFactory(4));

        return template;
    }
}
