package com.unsij.backend.vacantes_app.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "123456";
        String encrypted = encoder.encode(rawPassword);

        System.out.println("Contraseña original: " + rawPassword);
        System.out.println("Hash encriptado: " + encrypted);
    }
}
