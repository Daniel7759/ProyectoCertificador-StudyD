package com.study.Usuarios.controller;

import com.study.Cursos.model.Curso;
import com.study.Usuarios.model.User;
import com.study.Usuarios.model.UserResponseDTO;
import com.study.Usuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            Collection<User> userssDB = userService.findAll();
            return new ResponseEntity<>(userssDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBUseryId(@PathVariable Long userId){
        try{
            User userDB= userService.findById(userId);
            return new ResponseEntity<>(userDB, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User existingUser = userService.findByUsername(user.getUsername());
            if (existingUser!=null){
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            }
            User userDB = userService.insert(user);
            return new ResponseEntity<>(userDB, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/updateStarsAndExperience")
    public ResponseEntity<?> updateStarsAndExperience(@PathVariable Long userId, @RequestParam int estrellas, @RequestParam double experience){
        try{
            User userDB=userService.updateStarsAndExperience(userId,estrellas,experience);
            return new ResponseEntity<>(userDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/subtractStars")
    public ResponseEntity<?> removeStars(@PathVariable Long userId, @RequestParam int estrellas) {
        try {
            User user = userService.subtractStars(userId, estrellas);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al restar estrellas: " + e.getMessage());
        }
    }

    @GetMapping("/userdto/{userId}")
    public ResponseEntity<?> getUserWithLevel(@PathVariable Long userId){
        try {
            UserResponseDTO userDB = userService.findAllUserWithLevel(userId);
            return new ResponseEntity<>(userDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> authenticateUser(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.authenticate(email, password);
            UserResponseDTO userDTO = userService.findAllUserWithLevel(user.getUsertId());
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al autenticar usuario: " + e.getMessage());
        }
    }

    @GetMapping("/datacursos/{userId}")
    public ResponseEntity<?> getCursosDesbloqueadosDelUsuario(@PathVariable Long userId){
        try {
            List<Curso> userCursos = userService.bucarCursosConDatos(userId);
            return new ResponseEntity<>(userCursos, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> actualizarDatosUsuario(@PathVariable Long userId, @RequestParam String passwordActual,@RequestBody User user){
        try {
            User existingUserDB = userService.findById(userId);
            if (existingUserDB!=null){
                userService.update(user,passwordActual);
                return new ResponseEntity<>("Usuario Actualizado Correctamente", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("El usuario no exite",HttpStatus.NOT_FOUND);
            }
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
