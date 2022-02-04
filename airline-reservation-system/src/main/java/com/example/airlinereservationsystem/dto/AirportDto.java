package com.example.airlinereservationsystem.dto;


import com.example.airlinereservationsystem.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Abdi Wako Jilo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto {
    private String code;
    private String name;
    private long address_id;
}
