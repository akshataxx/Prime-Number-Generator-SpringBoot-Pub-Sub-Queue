package com.example.assignment2.subscriber;

import com.example.assignment2.config.MessagingConfiguration;
import com.example.assignment2.dto.Answer;
import com.example.assignment2.dto.Question;
import com.example.assignment2.repository.ResponseRepository;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionSubscriber  {
    /**
     * The class is responsible for consuming messages from the RabbitMQ message queue.
     * It is annotated with @Service, which means that it is a Spring-managed service.
     * The class implements the MessageListener interface, which means that it is a message listener.
     * The class is autowired with the ResponseRepository, RestTemplate, and RPC endpoint URL.
     */
    private final ResponseRepository responseRepository;
    private final RestTemplate restTemplate;
    private final String rpcEndpoint;

    //TODO change the @Value to import the client.rpc.url fromm application.properties

    /**
     * The constructor accepts the response repository, RestTemplate, and RPC endpoint URL as parameters.
     * The parameters are autowired by Spring.
     */
    public QuestionSubscriber(ResponseRepository responseRepository, RestTemplate restTemplate,
                              @Value("${client.rpc.url}") String rpcUrl) {
        this.responseRepository = responseRepository;
        this.restTemplate = restTemplate;
        this.rpcEndpoint = rpcUrl;
    }

    /**
     * The method consumes messages from the RabbitMQ message queue.
     * It is annotated with @RabbitListener, which means that it is a Spring-managed service
     * The method accepts a Question object as a parameter, which contains the number to calculate the prime numbers for.
     * The method returns a list of prime numbers and time taken.
     */
    @RabbitListener(queues = MessagingConfiguration.QUEUE)
    public void consumeQuestionFromQueue(Question question) {
        try{
            if (question.getQuestion() >= 1000000 || question.getQuestion() < 0){
                return;
            }
            System.out.println("Question from subscriber: "+question.getQuestion());
            long startTime = System.currentTimeMillis();
            System.out.println("starttime from subscriber: "+startTime);
            //Thread.sleep(50);

            List<Integer> primes = calculatePrimes(question.getQuestion());

            long endTime = System.currentTimeMillis();
            System.out.println("endtime from subscriber: "+endTime);

            long timeTaken = endTime - startTime;
            System.out.println("Time taken from subscriber: "+timeTaken);

            Answer answer = new Answer();
            answer.setAnswer(primes);
            System.out.println("Primes from subscriber: "+primes);
            answer.setTimeTaken(timeTaken);

            responseRepository.saveAnswer(answer);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or log it as needed
        }
    }

    //TODO put all the below in another service class - CalculatePrimeService

    /**
     * The method calculates the prime numbers up to the given number(max).
     */
    private List<Integer> calculatePrimes(int max) {
        List<Integer> primes = new ArrayList<>();
        for (int num = 2; num <= max; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }
        return primes;
    }

    /**
     * The method checks if the given number is prime.
     */
    private boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
