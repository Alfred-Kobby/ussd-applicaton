package com.example.ussdapplication;

import com.example.ussdapplication.utility.ApplicationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@SpringBootApplication
public class UssdApplication {
	@Autowired
	ApplicationProperties applicationProperties;

	@Bean
	LettuceConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisConFactory = new RedisStandaloneConfiguration();
		redisConFactory.setHostName(applicationProperties.getRedisHost());
		redisConFactory.setPort(Integer.parseInt(applicationProperties.getRedisPort()));
		redisConFactory.setPassword("");
		return new LettuceConnectionFactory(redisConFactory);
	}

	@Bean
	RedisTemplate< String, Object > redisTemplate() {
		final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
		template.setConnectionFactory( jedisConnectionFactory());
		template.setKeySerializer( new StringRedisSerializer());
		template.setDefaultSerializer(new StringRedisSerializer());
		template.setEnableDefaultSerializer(true);
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(UssdApplication.class, args);
	}

}
