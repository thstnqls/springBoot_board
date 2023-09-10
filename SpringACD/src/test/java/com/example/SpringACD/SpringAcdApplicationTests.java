package com.example.SpringACD;
import com.example.SpringACD.model.models.UserDao;
import com.example.SpringACD.model.models.User_info;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


@SpringBootTest   //Spring Boot 애플리케이션의 통합 테스트를 위해 사용.  Spring Boot 애플리케이션 컨텍스트가 로드되어 애플리케이션의 모든 빈과 설정을 테스트 환경에서 사용할 수 있음.
class SpringAcdApplicationTests {
    @Autowired
    private UserDao userDao;


    @Test
    void DeleteAllUsers() {
        List<User_info> users = userDao.getAllUsers();
        for(User_info user: users){
            userDao.delete(user);
        }
    }

    @Test
    void getAllUsers() {
        List<User_info> users = userDao.getAllUsers();
        System.out.println(users);
    }

    @Test
    void getUser() throws UserPrincipalNotFoundException {
        User_info user = userDao.findUserByU_id("456다456");
        System.out.println(user);
    }

    @Test
    void Deleteuser() throws UserPrincipalNotFoundException {    //특정 사용자 삭제
        User_info user = userDao.deleteUserById("456다456");
    }

    @Test
    void Updateusername() throws UserPrincipalNotFoundException {    //사용자 정보 업데이트
        String id = "456다456";
        //userDao.updateUsername(id,"thstnqls");
    }

    @Test
    void Existuser() throws UserPrincipalNotFoundException {   //사용자 존재 확인
        boolean result= userDao.existsById("456다456");
        System.out.println(result);
    }
}
