package com.example.assignment2.config;

import com.example.assignment2.subscriber.QuestionSubscriber;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

//class provides bean definition and configurations for RabbitMQ messaging
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

    //durability is false so that it doesn't have session storage
    @Bean
    Queue queue() {
        return new Queue(QUEUE, false);
    }

    //bean method creates a new topic exchange with the specified name
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    //bean method creates a binding between the queue and the exchange using the routing key
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    //playing with object datatype not string therefore converter required
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    /* Responsible for creating and managing the message listener. It connects to the
    RabbitMQ broker, listens to the specified queue, and receives messages from it.
    Handles the low-level details of establishing the connection, managing the consumer, and
    dispatching messages to the listener.
    */
    /*@Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                   MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    /*Used to adapt the QuestionSubscriber bean (or any other message listener bean) to the
    required message listener interface. Bridge between MessageListener and SimpleMessageListenerContainer.
    Responsible for receiving messages from the queue and passing them to the delegated listener in
    QuestionSubscriber.
    */
    /*@Bean
    public MessageListenerAdapter messageListenerAdapter(QuestionSubscriber questionSubscriber,
                                                         MessageConverter messageConverter) {
        //MessageListenerAdapter adapter = new MessageListenerAdapter(questionSubscriber, "consumeQuestionFromQueue");
        MessageListenerAdapter adapter = new MessageListenerAdapter();

        //adapter.setDelegate(questionSubscriber);
        //adapter.setDefaultListenerMethod("consumeQuestionFromQueue");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    } */

    /*bean method creates an instance of RabbitTemplate that serves as the AmqpTemplate for
    publishing messages. It is configured with the connection factory and message converter. */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    //provides configuration for RestTemplate to send HTTP requests
    /*@Bean
    public RestTemplate restTemplate() {
        // HttpComponentsClientHttpRequestFactory for making HTTP requests
        return new RestTemplate();
    }*/

    @Bean
    public TaskScheduler taskScheduler() {

        return new ThreadPoolTaskScheduler();
    }
}
