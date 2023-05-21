package com.example.assignment2.dto;

import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Answer {
    /**
     * The answer to the question
     * Consists of the list of prime numbers and time taken in milliseconds
     */
    private List<Integer> answer;
    private long timeTaken;
}
