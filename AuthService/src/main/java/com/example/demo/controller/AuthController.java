package com.example.demo.controller;

import com.example.demo.dto.AuthDto;
import com.example.demo.dto.RequestUserDto;
import com.example.demo.dto.ResponseUserDto;
import com.example.demo.services.AuthService;
import com.example.demo.services.JwtService;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtService jwtService;

    public AuthController(AuthService authService,JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }


    @GetMapping("/hi") // test passed
    public String hiTest(){
        return "hi test succeded";
    }

    @GetMapping("/isRoleValidJwt") // test passed
    public ResponseEntity<String> roleJwtTest(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.replace("Bearer ", "");

        if (jwtService.isTokenValidForUser(jwt)) {
            return ResponseEntity.ok("JWT is valid and role is correct");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT");
    }


    @PostMapping("/login") // test passed (furher tests will be made when the client side is developed)
    public ResponseEntity<AuthDto> login(@RequestBody RequestUserDto requestUserDto){
        ResponseUserDto loggedUser = authService.safeLogIn(
                requestUserDto.getUserName(),
                requestUserDto.getPassword());
        if(loggedUser != null){
            String jwt = jwtService.generateJwt(loggedUser);
            AuthDto authDto = new AuthDto(loggedUser,jwt);
            return ResponseEntity.ok(authDto);
        }return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout") // test passed
    public ResponseEntity<Void> logout(){
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Null> refresh(){
        return null; //will be implemented when refresh token support is added
    }

    @PostMapping("/register") // test passed
    public ResponseEntity<AuthDto> register(@RequestBody RequestUserDto requestUserDto){

        if(authService.doesUserNameExist(requestUserDto.getUserName())){
            System.out.println(requestUserDto.getUserName()+" Username exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        }
        ResponseUserDto registeredUser = authService.registerUser(requestUserDto.getUserName(), requestUserDto.getPassword());
        String jwt = jwtService.generateJwt(registeredUser);
        AuthDto authDto = new AuthDto(registeredUser,jwt);
        return ResponseEntity.ok(authDto);
    }

    @PostMapping("/updateAccount") ResponseEntity<Null> updateAccount(){
        return null;
    }
}
