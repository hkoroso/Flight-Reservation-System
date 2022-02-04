package com.example.airlinereservationsystem.controller;

import com.example.airlinereservationsystem.domain.User;
import com.example.airlinereservationsystem.domain.UserRole;
import com.example.airlinereservationsystem.dto.*;
import com.example.airlinereservationsystem.service.interfaces.UserService;
import com.example.airlinereservationsystem.util.constant.Roles;
import com.example.airlinereservationsystem.util.exception.ErrorDetails;
import com.example.airlinereservationsystem.util.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * @author Abdi Wako Jilo
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtil jwtUtil;


    @Qualifier("encoder")
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRoleName(Roles.ROLE_USER);
        userRoles.add(userRole);
        User user = modelMapper.map(userDto,User.class);
        user.setRole(userRoles);
        ResponseEntity<?> responseEntity = userService.signup(user);
        if(responseEntity.getStatusCode() == HttpStatus.CONFLICT){
            return ResponseEntity.ok(new HashMap<>(){{put( responseEntity.getStatusCode(),responseEntity.getBody());}} );
        }else{
            final UserDetails userDetails = userService.getUserDetails(user.getUsername());
            final Map<String, Object> jwt  = jwtUtil.generateToken(userDetails);
            logger.info("JWT: " + jwt);
            return ResponseEntity.ok(new UserRegistrationResponse(jwt));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
       UserDetails userDetails =  userService.getUserDetails(userLoginDto.getUsername());

       if(passwordEncoder.matches(userLoginDto.getPassword(),userDetails.getPassword())){
           final Map<String, Object> jwt  = jwtUtil.generateToken(userDetails);
           logger.info("JWT: " + jwt);
           return ResponseEntity.ok(new UserRegistrationResponse(jwt));
       }else{
            return new ResponseEntity<>(new ErrorDetails(new Date(),"No user found","Please check your username or password")
                    ,HttpStatus.UNAUTHORIZED);
       }
    }

    @PostMapping("/admin/add-role")
    public ResponseEntity<?> addRole(@RequestBody RoleDto userRole) {
        System.out.println("inside addrole.");
        userService.addRole(userRole);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/remove-role")
    public ResponseEntity<?> removeRole(@RequestBody RoleDto userRole) {
        System.out.println("inside removeRole controller. ");
        userService.removeRole(userRole);
        return ResponseEntity.ok().build();
    }
}
