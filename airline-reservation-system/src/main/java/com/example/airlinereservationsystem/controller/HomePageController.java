package com.example.airlinereservationsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author Abdi Wako Jilo
 */
@Controller
public class HomePageController {

    @GetMapping(value = "/")
    public String displayHomePage(){
        return "home/index";
    }

    @GetMapping("/registration-form/index")
    public String displayRegistrationPage(){
        return "registration-form/index";
        }
}
