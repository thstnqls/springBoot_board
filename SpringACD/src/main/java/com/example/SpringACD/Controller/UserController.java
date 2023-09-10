package com.example.SpringACD.Controller;

import com.example.SpringACD.model.models.UserDao;
import com.example.SpringACD.model.models.User_info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }
    @GetMapping("/get-all")  //사용자의 모든 정보를 불러올 때 사용 (포인트 때 사용할 예정)
    public List<User_info> getAllUsers(){
        return userDao.getAllUsers();
    }

    @GetMapping("/{id}")   //id값에 맞는 정보를 불러오기 위함.
    public User_info findUserById(@PathVariable("id") String id) throws UserPrincipalNotFoundException {
        return userDao.findUserByU_id(id);
        }

    @PostMapping("/save")  //사용자 정보를 저장
    public void save(@RequestBody User_info user){
        userDao.save(user);
    }

    @DeleteMapping("/{id}")   //사용자 삭제
    public void deleteUserById(@PathVariable("id") String id) throws UserPrincipalNotFoundException {
        userDao.deleteUserById(id);
    }

    @GetMapping("/exist/{id}")    //사용자 존재하는지 확인
    public boolean existUserById(@PathVariable("id")String u_id) throws UserPrincipalNotFoundException{
        return userDao.existsById(u_id);
    }

   /* @PutMapping("/user/{id}")
    public ResponseEntity<User_info> updateUsername(@PathVariable("id") String id, @RequestBody String newname) {
        try {
            User_info updatedUser = userDao.updateUsername(id, newname);
            return ResponseEntity.ok(updatedUser); // 업데이트된 사용자 정보 반환
        } catch (UserPrincipalNotFoundException e) {
            // 사용자 정보가 존재하지 않는 경우 404 Not Found 반환
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // 그 외의 예외 발생 시 500 Internal Server Error 반환
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/

}
