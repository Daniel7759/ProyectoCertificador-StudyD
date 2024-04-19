package com.study.Usuarios.service;

import com.study.Cursos.model.Curso;
import com.study.Usuarios.model.CursoFinalizadoDTO;
import com.study.Usuarios.model.TokenFCM;
import com.study.Usuarios.model.User;
import com.study.Usuarios.model.UserResponseDTO;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User insert(User user);
    User update(User user, String passwordActual);
    User setTokenFCMToUser(TokenFCM token);
    void deleteTokenFCMToUser(Long userId, String token);
    User findByUsername(String username);
    User findById(Long userId);
    User authenticate(String email, String password);
    User updateStarsAndExperience(Long userId, int estrellas, double experiencia);
    User subtractStars(Long userId, int estrellas);
    Collection<User> findAll();
    UserResponseDTO findAllUserWithLevel(Long userId);
    List<Curso> bucarCursosConDatos(Long userId);
    List<CursoFinalizadoDTO> findCursosFinalizados(Long userId);
}
