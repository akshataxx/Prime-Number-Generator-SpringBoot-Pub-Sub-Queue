package com.example.assignment2.publisher;

import com.example.assignment2.config.MessagingConfiguration;
import com.example.assignment2.dto.Question;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
/**
 * The QuestionPublisher class is responsible for publishing questions to a RabbitMQ message queue.
 * It is annotated with @Service and @RestController, which means that it is a Spring-managed service
 * and that it exposes REST endpoints.
 */

@Service
@RestController
public class QuestionPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public QuestionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * The method generates a random number based on the user-configured maximum value.
     * The number is then published to the RabbitMQ message queue.
     */
    private void publishQuestion(int max) {
        Question question = new Question();
        question.setQuestion(max);
        System.out.println("Question: " + question);
        this.rabbitTemplate.convertAndSend(MessagingConfiguration.EXCHANGE, MessagingConfiguration.ROUTING_KEY, question);
    }

    /**
     * The method is an endpoint that triggers manual publishing of a question by calling publishQuestion().
     * It is annotated with @PostMapping, which means that it is a REST endpoint that accepts POST requests.
     * The method accepts an optional max parameter, which is used to configure the maximum value of the random number.
     * If the parameter is not provided, the default value of 1000000 is used.
     * The method returns a ResponseEntity with a String body and an HTTP status code.
     * The body contains a message that indicates whether the question was published successfully.
     * The status code is 200 if the request was successful, or 400 if the request was invalid.
     */

    @PostMapping("/publish")
    public ResponseEntity<String> manualPublish(@RequestParam(value = "max", required = false) Integer max) {

        /*int selectedMax = (max != null && max > 0) ? max : defaultMax;
        publishQuestion(selectedMax);
        return ResponseEntity.ok("Question published");*/

        int selectedMax;

        if (max == null) {
            selectedMax = 100000; // Use the default value
        } else if (max > 0 && max <= 1000000) {
            selectedMax = max; // Use the user-provided value
        } else {
            return ResponseEntity.badRequest().body("Invalid max value"); // Reject invalid values
        }

        publishQuestion(selectedMax);
        return ResponseEntity.ok("Question published");
    }
}
