package com.example.assignment2.controller;

import com.example.assignment2.dto.Answer;
import com.example.assignment2.repository.ResponseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that handles requests related to answer
 * The getRecentAnswers() endpoint handles GET requests and retrieves the recent answers from the ResponseRepository.
 * The receiveAnswer() endpoint handles POST requests and saves new answers to the ResponseRepository.
 * Class is mapped to /api/answer url path using @RequestMapping annotation
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final ResponseRepository responseRepository;

    /**
     * constructor takes ResponseRepository as dependency to facilitate dependency injection
     * required by AnswerController to retrieve and store answers therefore an instance of Response
     * Repository is available to AnswerController
     * @param responseRepository
     */

    public AnswerController(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    /**
     * handles GET requests to retrieve the recent answers from the ResponseRepository and
     * returns them as a list
     * @return
     */
    @GetMapping
    public List<Answer> getRecentAnswers(){ //swagger annotations TODO
        return responseRepository.getRecentAnswers();
    }

    /**
     * handles POST requests to receive new answers and saves them to the ResponseRepository
     * @param answer
     * @return
     */
    @PostMapping
    public ResponseEntity<String> receiveAnswer(@RequestBody Answer answer){
        responseRepository.saveAnswer(answer);
        return ResponseEntity.ok("Answer received");
    }
}
