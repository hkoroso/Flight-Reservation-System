package com.example.airlinereservationsystem.service;

import com.example.airlinereservationsystem.domain.User;
import com.example.airlinereservationsystem.domain.UserRole;
import com.example.airlinereservationsystem.dto.RoleDto;
import com.example.airlinereservationsystem.dto.UserLoginDto;
import com.example.airlinereservationsystem.dto.UserDto;
import com.example.airlinereservationsystem.dto.UserResponseDto;
import com.example.airlinereservationsystem.repository.UserRepository;
import com.example.airlinereservationsystem.service.interfaces.UserService;
import com.example.airlinereservationsystem.util.security.UserSecurityDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * @author Abdi Wako Jilo
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Qualifier("encoder")
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        modelMapper.map(User.class, UserResponseDto.class);
        return ((List<User>) userRepository
                .findAll())
                .stream()
                .map(user->
                    modelMapper.map(user, UserResponseDto.class)
                 )
                .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<?> signup(User user) {
        User currentUser = userRepository.findUserByEmail(user.getEmail());
        if(currentUser != null){
            if(currentUser.getEmail().equals(user.getEmail()) || currentUser.getUsername().equals(user.getUsername())){
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
            }
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         userRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean login(UserLoginDto userLoginDto) {
        List<User> users = userRepository.login(userLoginDto.getUsername(), userLoginDto.getPassword());
        if (users.isEmpty())
            return false;
        return true;
    }

    @Override
    public UserDto addRole(RoleDto role) {
        User user = userRepository.findByUsername(role.getUserName()).get();
        user.getRole().add(role.getRole());
        userRepository.save(user);
        return convertUserToUserDto(user);
    }

    @Override
    public UserDto removeRole(RoleDto role) {

        User user = userRepository.findByUsername(role.getUserName()).get();

        UserRole userRole = user.getRole()
                .stream().filter(r-> r.getRoleName()
                        .equals(role.getRole().getRoleName())).collect(Collectors.toList()).get(0);
        user.getRole().remove(userRole);
        userRepository.save(user);
        return convertUserToUserDto(user);
    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserID(user.getID());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getRole());
        return userDto;
    }
    @Override
    public UserDetails getUserDetails(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("No user found: "+ username));
        return user.map(UserSecurityDetailsImpl::new).get();
    }

    @Override
    public void addUser(User user) {

    }

    @Override
	public Optional<User> findUserByID(long id) {
        return userRepository.findByID(id);
	}
}
