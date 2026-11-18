package com.jcfontaneria.fontaneria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FontaneriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FontaneriaApplication.class, args);
    }
//CREADO para hacer pruebas
  //  @Bean
    //public PasswordEncoder passwordEncoder() {
  //      return new BCryptPasswordEncoder();
  //  }

}
