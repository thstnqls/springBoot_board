package com.example.SpringACD.model.models;

public class LoginResponseDTO {
    private User_info user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(User_info user, String jwt){
        this.user=user;
        this.jwt=jwt;
    }

    public User_info getUser(){
        return this.user;
    }

    public void setUser(User_info user){
        this.user=user;
    }
    public String getJwt(){
        return this.jwt;
    }
    public void setJwt(String jwt){
        this.jwt=jwt;
    }
}
