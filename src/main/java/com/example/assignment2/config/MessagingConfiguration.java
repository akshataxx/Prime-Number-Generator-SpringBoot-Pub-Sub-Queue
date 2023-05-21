package com.example.assignment2.config;

import com.example.assignment2.subscriber.QuestionSubscriber;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Class to provide bean definition and configurations for RabbitMQ messaging
 */
@Configuration
public class MessagingConfiguration {

    //set up the connection factory, message listener container, and message converter
    public static final String QUEUE = "question-queue";

    //represents the name of the RabbitMQ exchange to which the queue binds
    public static final String EXCHANGE = "question-exchange";

    //represents the routing key used in the binding
    public static final String ROUTING_KEY = "questions_routing_key";

    @Autowired
    private Environment environment;

    /**
     * Method to create a new queue with the specified name
     * Durability is false so that it doesn't have session storage
     * @return Queue
     */
    @Bean
    Queue queue() {
        return new Queue(QUEUE, false);
    }

    /**
     * Method to create a new topic exchange with the specified name
     * @return TopicExchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    /**
     * Method to create a binding between the queue and the exchange using the routing key
     * @param queue
     * @param exchange
     * @return Binding
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * method with object datatype not string therefore converter required
     * @return MessageConverter
     */
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Method to create an instance of rabbit template that serves as the AmqpTemplate for publishing messages
     * Configured with the connection factory and message converter
     * @param connectionFactory
     * @return AmqpTemplate
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
