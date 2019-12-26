package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Configuration
    @EnableWebSecurity
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser("kn")
                    .password(passwordEncoder.encode("k"))
                    .roles("USER", "ADMIN")
                    .and()
                    .withUser("user")
                    .password(passwordEncoder.encode("u"))
                    .roles("USER");
        }
    }

}
