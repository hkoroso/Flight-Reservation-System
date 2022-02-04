package com.example.airlinereservationsystem.dto;

import com.example.airlinereservationsystem.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsDto {
    private long userId;
    private long[] flightInstanceIds;

}
