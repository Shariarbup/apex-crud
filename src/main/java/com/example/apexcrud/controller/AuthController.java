package com.example.apexcrud.controller;

import com.example.apexcrud.configuration.securityHelper.JwtTokenHelper;
import com.example.apexcrud.dto.JwtAuthRequest;
import com.example.apexcrud.dto.JwtAuthResponse;
import com.example.apexcrud.dto.UserDto;
import com.example.apexcrud.exceptions.ApiException;
import com.example.apexcrud.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "AuthController", description = "AUTHENTICATION AND AUTHORIZATION API")
@SecurityRequirement(name="Bearer Authentication")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
    ) throws Exception {
        System.out.println("Request: " + request);
        this.authenticate(request.getUserId().toString(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserId().toString());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException ex){
            System.out.println("Invalid Details");
            throw new ApiException("Invalid username or Password !!");
        }

    }

    //register
    @PostMapping(value = "/auth/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
