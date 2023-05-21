package com.example.assignment2.repository;

import com.example.assignment2.dto.Answer;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ResponseRepository {
    /**
     * The recentAnswers list is a LinkedList of Answer objects.
     * It is static, which means that it is shared between all instances of the ResponseRepository class.
     * This is necessary because the RabbitMQ listener is a Spring-managed bean,
     * and the ResponseRepository is a Spring-managed repository component.
     * The recentAnswers list is private, which means that it can only be accessed from within the ResponseRepository class.
     * This is necessary because the ResponseRepository class is the only class that should be able to modify the list.
     */
    private static final LinkedList<Answer> recentAnswers = new LinkedList<>();
    /**
     * The saveAnswer() method adds the provided Answer object to the recentAnswers list.
     * If the list contains more than 50 answers, the oldest answers are removed from the list.
     */
    public void saveAnswer(Answer answer) {
        recentAnswers.add(answer);
        if (recentAnswers.size() > 50) {
            recentAnswers.removeFirst();
        }
    }

    /**
     * The getRecentAnswers() method returns the recentAnswers list.
     * The method is public, which means that it can be accessed from outside the ResponseRepository class.
     * This is necessary because the ResponseController class needs to be able to access the list.
     */
    public List<Answer> getRecentAnswers() {
        return recentAnswers;
    }

    public Answer getLatestAnswer() {
        return recentAnswers.isEmpty() ? null : recentAnswers.getLast();
    }
}

