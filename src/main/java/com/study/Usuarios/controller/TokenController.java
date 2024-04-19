package com.study.Usuarios.controller;

import com.study.Usuarios.model.TokenFCM;
import com.study.Usuarios.model.User;
import com.study.Usuarios.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "tokens")
public class TokenController {

    private final UserService userService;

    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addTokenToUser")
    public ResponseEntity<?> addTokenToUser(@RequestBody TokenFCM token) {
        if (token.getToken() == null || token.getToken().isEmpty()) {
            return new ResponseEntity<>("El token es requerido", HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userService.setTokenFCMToUser(token);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Aquí puedes manejar diferentes tipos de excepciones de manera más específica
            if (e.getMessage().contains("El usuario especificado no existe")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("El token no puede estar vacío")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTokenToUSer(@RequestBody TokenFCM token) {
        if (token.getToken() == null || token.getToken().isEmpty()) {
            return new ResponseEntity<>("El token es requerido", HttpStatus.BAD_REQUEST);
        }
        try {
            userService.deleteTokenFCMToUser(token.getUsertId(), token.getToken());
            return new ResponseEntity<>("Token eliminado correctamente", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Aquí puedes manejar diferentes tipos de excepciones de manera más específica
            if (e.getMessage().contains("El usuario especificado no existe")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("El token no puede estar vacío")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
