package com.example.airlinereservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
/**
 * @author Abdi Wako Jilo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationResponse {
    private Map<String, Object> jwt;
}
