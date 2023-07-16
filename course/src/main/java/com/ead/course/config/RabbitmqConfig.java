package com.ead.course.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModules(javaTimeModule);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    FanoutExchange fanoutUserEvent() {
        return new FanoutExchange(exchangeUserEvent);
    }
    /*
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Value(value = "${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);

        template.setMessageConverter(messageConverter);

        return template;
    }

    @Bean
    public FanoutExchange fanoutUserEvent() {
        return new FanoutExchange(exchangeUserEvent);
    }*/


}
