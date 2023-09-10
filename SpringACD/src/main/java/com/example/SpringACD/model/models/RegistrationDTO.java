package com.example.SpringACD.model.models;

public class RegistrationDTO {    //회원등록을 위한 getter과 setter메소드 생성
    private String username;
    private String password;
    private String u_num;
    private String u_email;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password,String u_num, String u_email){
        super();
        this.username=username;
        this.password=password;
        this.u_num=u_num;
        this.u_email=u_email;
        
    }
    public String getUsername(){
        return this.username;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password=password;
    }

    public String getUserNumber() {
        return this.u_num;
    }

    public void setUserNumber(String userNumber) {
        this.u_num = userNumber;
    }

    public String getUserEmail() {
        return this.u_email;
    }

    public void setUserEmail(String userEmail) {
        this.u_email = userEmail;
    }


    public String toString(){
        return "Registration info: username"+this.username+"password:"+this.password+"number:"+this.u_num+"email:"+this.u_email;
    }
}
