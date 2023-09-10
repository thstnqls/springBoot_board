package com.example.SpringACD.model.models;

import com.example.SpringACD.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDao {

    @Autowired
    private UserRepository repository;


    public void save(User_info user) {
        repository.save(user);
    }  //업데이트는 이름과 번호,포인트만 사용이니까 이정도는 괜찮지 않을까

    public List<User_info> getAllUsers() {
        List<User_info> users = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(users::add);
        return users;
    }

    public void delete(User_info user) {
        repository.delete(user);

    /*public void delete(String userId) {
        repository.deleteById(userId);
    }*/
    }


    public User_info deleteUserById(String password) throws UserPrincipalNotFoundException {
        // 해당 u_id를 가진 사용자 조회
        Optional<User_info> userOptional = Optional.ofNullable(repository.findById(password)
                .orElseThrow(() -> new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.")));

        // 사용자가 존재하면 삭제
        if (userOptional.isPresent()) {
            repository.deleteById(password);
        } else {
            throw new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.");
        }
        return null;
    }


    public User_info findUserByU_id(String password) throws UserPrincipalNotFoundException {
        Optional<User_info> user = Optional.ofNullable(repository.findById(password)
                .orElseThrow(() -> new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.")));
        return user.get();
    }

    public boolean existsById(String u_id) {
        // 사용자가 존재하는지 확인하는 메서드
        return repository.existsById(u_id);
    }

    /* public User_info updateUsername(String id, String newname) throws UserPrincipalNotFoundException {
        Optional<User_info> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User_info user = userOptional.get();
            // 여기에서 updatedUser 객체의 필드들을 사용하여 사용자 정보를 업데이트하거나 처리합니다.
            user.setU_name(newname);
            // 다른 필드들도 필요에 따라 업데이트
            // 업데이트된 사용자 정보를 저장
            repository.save(user);
            return user;
        } else {
            throw new UserPrincipalNotFoundException("사용자 정보가 존재하지 않습니다.");
        }

    } */







}