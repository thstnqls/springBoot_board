package com.example.SpringACD.Controller;


import com.example.SpringACD.model.models.LoginResponseDTO;
import com.example.SpringACD.model.models.RegistrationDTO;
import com.example.SpringACD.model.models.User_info;
import com.example.SpringACD.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {    //회원가입을 위한 컨트롤러
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User_info registerUser(@RequestBody RegistrationDTO body){
        //Logger logger = LoggerFactory.getLogger(getClass());
        //logger.info("Received data from client: {}", body);
        return authenticationService.registerUser(body.getUsername(),body.getPassword(),body.getUserNumber(),body.getUserEmail());
    }
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(),body.getPassword());
    }
    @GetMapping("/test")
    public String helloUserController(){
        return "User access level";
    }


    @GetMapping("/exist/{username}/{id}")    //사용자 존재하는지 확인
    public boolean existUserByUsernameAndId(
            @PathVariable("username") String username,
            @PathVariable("id") String u_id) throws UserPrincipalNotFoundException {
        return authenticationService.existbyUsername(username, u_id);
    }

    @DeleteMapping("/{id}")   //사용자 삭제
    public void deleteUserById(@PathVariable("id") String id) throws UserPrincipalNotFoundException {
        authenticationService.deleteUserById(id);
    }
}
