package com.study.Usuarios.service;

import com.study.Usuarios.model.User;
import com.study.Usuarios.model.UserResponseDTO;

import java.util.Collection;

public interface UserService {

    public abstract User insert(User user);
    public abstract User findByUsername(String username);
    public abstract User findById(Long userId);

    public abstract User authenticate(String email, String password);
    public abstract User updateStarsAndExperience(Long userId, int estrellas, double experiencia);
    public abstract User subtractStars(Long userId, int estrellas);
    public abstract Collection<User> findAll();
    public abstract UserResponseDTO findAllUserWithLevel(Long userId);
}
