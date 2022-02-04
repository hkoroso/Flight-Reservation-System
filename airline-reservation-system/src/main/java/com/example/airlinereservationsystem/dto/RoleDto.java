package com.example.airlinereservationsystem.dto;


import com.example.airlinereservationsystem.domain.UserRole;
import lombok.Data;
/**
 * @author Abdi Wako Jilo
 */
@Data
public class RoleDto {

    private UserRole role;
    private String userName ;

}
