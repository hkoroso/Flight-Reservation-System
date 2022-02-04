package com.example.airlinereservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Abdi Wako Jilo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto {
    private String code;
    private String name;
    private String history;
}
