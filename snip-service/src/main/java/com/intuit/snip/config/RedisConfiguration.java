package com.intuit.snip.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import io.lettuce.core.ReadFrom;

@Configuration

public class RedisConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfiguration.class);

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
        .readFrom(ReadFrom.NEAREST)
        .build();
    RedisStandaloneConfiguration configuration =
        new RedisStandaloneConfiguration(host,port);

//    RedisStaticMasterReplicaConfiguration staticMasterReplicaConfiguration =
//        new RedisStaticMasterReplicaConfiguration(host);
//
//    staticMasterReplicaConfiguration.addNode(host, port);
//    return new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);

    LOGGER.info("Redis master is: {}", host);
    LOGGER.info("Redis port is: {}", port);
    return new LettuceConnectionFactory(configuration, clientConfig);
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate() {
    RedisConnectionFactory redisConnectionFactory = redisConnectionFactory();
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }

}
