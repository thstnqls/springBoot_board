package com.example.SpringACD.configuration;

import com.example.SpringACD.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//인증, 권한 부여, JWT 토큰 처리
@Configuration
public class SecurityConfiguration {

    private final RSAKeyProperties keys;
    //RSA키 관련 속성을 담고 있는 객체
    public SecurityConfiguration(RSAKeyProperties keys){
        this.keys=keys;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //사용자 인증 관리자 정의
    @Bean
    public AuthenticationManager authManager (UserDetailsService detailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        return new ProviderManager(daoProvider);
    }

    // HTTP 요청에 대한 보안 필터 체인을 설정
    //요청에 따라 접근 권한을 부여하고, OAuth2 JWT 토큰 인증 및 세션 관리 정책을 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())    //spring security로 잠겨있는 상황을 permitAll시켜 다 열어놓음
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
                    auth.anyRequest().authenticated();
                });
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    //JWT 디코더를 설정. keys.getPublicKey()를 사용하여 공개키를 이용해 JWT 디코더를 구성
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    //JWT 인코더 설정. 공개키와 개인키를 사용하여 JWK를 구성하고, 이를 이용해 JWT 인코더를 생성
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    //JWT 토큰에서 권한을 추출하여 스프링 시큐리티 권한으로 변환하는 데 사용
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}
