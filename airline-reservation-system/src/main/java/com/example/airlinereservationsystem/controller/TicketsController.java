package com.example.airlinereservationsystem.controller;


import com.example.airlinereservationsystem.service.interfaces.TicketsService;
import com.example.airlinereservationsystem.service.TicketsServiceImpl;
import com.example.airlinereservationsystem.util.ResponseHandler;
import com.example.airlinereservationsystem.util.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * @author Abdi Wako Jilo
 */
@RestController
@RequestMapping
public class TicketsController {
    Logger logger = LoggerFactory.getLogger(TicketsController.class);
    @Autowired
    TicketsService ticketsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TicketsServiceImpl ticketsServiceImpl;

}
