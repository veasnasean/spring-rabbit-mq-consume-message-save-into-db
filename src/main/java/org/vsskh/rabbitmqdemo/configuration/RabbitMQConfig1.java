package org.vsskh.rabbitmqdemo.configuration;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitMQConfig1 {

    @Value("${rabbitmq.queue.exchange}")
    private String exchange;
    @Value("${rabbitmq.queue.jsonqueue}")
    private String jsonQueue;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;
    //creat other queue for store another queue
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue ,false);
    }
    //spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    //binding for json message
    @Bean
    public Binding bindingJson(){
        return BindingBuilder.bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }
    //create RabbitTemplate for support sent json message
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
