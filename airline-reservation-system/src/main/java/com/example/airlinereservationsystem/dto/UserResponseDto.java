package com.example.airlinereservationsystem.dto;

import com.example.airlinereservationsystem.domain.Address;
import com.example.airlinereservationsystem.domain.UserRole;
import lombok.Data;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
@Data
public class UserResponseDto {
    private long userID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String dataOfBirth;
    private List<UserRole> userRole;
    private Address residenceAddress;
}