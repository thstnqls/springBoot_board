package com.example.SpringACD.services;


import com.example.SpringACD.model.models.User_info;
import com.example.SpringACD.model.repository.UserRepository;
import com.example.SpringACD.model.models.LoginResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

//DAO같은거
@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;



    public User_info registerUser(String username, String password, String u_num, String u_email) {
        String encodedPassword = passwordEncoder.encode(password);     //비밀번호 암호화

        return userRepository.save(new User_info(username, encodedPassword, u_num, u_email));
    }
    public boolean existsbyUsername(String username, String u_id) {
        // 사용자가 존재하는지 확인하는 메서드
        return userRepository.existsById(u_id);
    }

    public LoginResponseDTO loginUser(String u_email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(u_email, password)
            );
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(u_email).get(), token);   //여기에서 토큰이 발급된다
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }


    public User_info deleteUserById(String password) throws UserPrincipalNotFoundException {
        // 해당 u_id를 가진 사용자 조회
        Optional<User_info> userOptional = Optional.ofNullable(userRepository.findById(password)
                .orElseThrow(() -> new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.")));

        // 사용자가 존재하면 삭제
        if (userOptional.isPresent()) {
            userRepository.deleteById(password);
        } else {
            throw new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.");
        }
        return null;
    }


    public Boolean existbyUsername(String username, String password) {
        Optional<User_info> userOptional = userRepository.findByUsername(username);

        // 사용자를 찾지 못한 경우
        if (!userOptional.isPresent()) {
            // 처리할 로직을 추가하거나 예외를 던질 수 있음
            // 여기서는 사용자가 없으면 false를 반환
            return false;
        }

        // 사용자를 찾은 경우, 비밀번호를 비교
        User_info user = userOptional.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true; // 비밀번호 일치
        } else {
            return false; // 비밀번호 불일치
        }
    }
}
