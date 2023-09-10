package com.example.SpringACD.model.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity   //해당 클래스가 JPA 엔티티(Entity)임을 나타낸다.  JPA는 이 클래스와 데이터베이스 테이블을 매핑하여 객체와 관계형 데이터베이스 간에 데이터를 주고받을 수 있도록 한다.
@Table(name = "user_info")

public class User_info implements UserDetails {
    @Id
    @Column(unique = true)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)   //기본 키의 값을 자동으로 생성하는 방법을 설정. GenerationType.IDENTITY를 사용하면 데이터베이스가 자동으로 기본 키 값을 생성해주는 자동 증가 방식을 사용
    private String password;    //u_id
    private String username;     //u_name
    private String u_num;
    private String u_email;


    public User_info(){

    }
    public User_info( String admin, String password){
        super();

    }

    public User_info(String username, String password, String u_num, String u_email){
        super();
        this.username=username;
        this.password=password;
        this.u_num=u_num;
        this.u_email=u_email;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username){
        this.username=username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getU_num() {
        return this.u_num;
    }

    public void setU_num(String u_num) {
        this.u_num = u_num;
    }

    public String getU_email() {
        return this.u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    @Override
    public String toString() {
        return "User_info{" +
                "u_id='" + password + '\'' +
                ", u_name='" + username + '\'' +
                ", u_num=" + u_num +
                ", u_email='" + u_email + '\'' +
                '}';
    }
}
