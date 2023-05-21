package com.example.assignment2.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Question {
    /**
     * The question to be asked
     * Consists of the number to be checked for primality
     */

    private int question;
}

