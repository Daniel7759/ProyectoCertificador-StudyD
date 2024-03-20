package com.study.Usuarios.service;

import com.study.Niveles.model.Level;
import com.study.Niveles.model.LevelResponseDTO;
import com.study.Niveles.repository.LevelRepository;
import com.study.Usuarios.model.*;
import com.study.Usuarios.repository.RoleRepository;
import com.study.Usuarios.repository.UserCursoRepository;
import com.study.Usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCursoRepository userCursoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User insert(User user) {
        // Codificar la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Long levelId=user.getLevel().getLevelId();
        Level level=levelRepository.findById(levelId).orElse(null);

        if (level!=null){
            level.getUsers().add(user);
            user.setLevel(level);

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                // Si no tiene ningún rol asignado, asignar el rol de usuario (USER)
                Role userRole = roleRepository.findByNombreRol(EnumRoles.USER);
                if (userRole == null) {
                    // Si el rol no existe, crearlo y guardarlo en la base de datos
                    userRole = Role.builder().nombreRol(EnumRoles.USER).build();
                    roleRepository.save(userRole);
                }
                user.setRoles(Collections.singleton(userRole)); // Asignar el rol al usuario
            }
            return userRepository.save(user);
        }else {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User authenticate(String email, String password) {
        User userDB = userRepository.findByEmail(email);
        if (userDB != null && passwordEncoder.matches(password, userDB.getPassword())){
            return userDB;
        }
        return null;
    }

    @Override
    public User updateStarsAndExperience(Long userId, int estrellas, double experiencia) {
        User usuario = userRepository.findById(userId).orElse(null);
        if (usuario != null) {
            int nuevasEstrellas = usuario.getStars() + estrellas;
            double nuevaExperiencia = usuario.getExperience() + experiencia;

            // Verificar si existe un nivel superior al actual
            Level levelActual = usuario.getLevel();
            Level levelSiguiente = levelRepository.findById(levelActual.getLevelId() + 1).orElse(null);

            if (levelSiguiente != null && nuevaExperiencia >= levelSiguiente.getXpNeeded()) {
                // Si hay un nivel superior y la nueva experiencia es suficiente, actualizar el nivel
                usuario.setLevel(levelSiguiente);
            }

            usuario.setStars(nuevasEstrellas);
            usuario.setExperience(nuevaExperiencia);
            userRepository.save(usuario);
        }
        return usuario;
    }

    @Override
    public User subtractStars(Long userId, int estrellas) {
        return updateStarsAndExperience(userId,-estrellas,0);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO findAllUserWithLevel(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Level level = levelRepository.findById(user.getLevel().getLevelId()).orElse(null);
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setUserId(user.getUsertId());
            userResponseDTO.setFirstname(user.getFirstname());
            userResponseDTO.setLastname(user.getLastname());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setUsername(user.getUsername());
            userResponseDTO.setPhone(user.getPhone());
            userResponseDTO.setStars(user.getStars());
            userResponseDTO.setExperience(user.getExperience());

            // Mapeo del objeto Level
            if (level != null) {
                LevelResponseDTO levelResponseDTO = new LevelResponseDTO();
                levelResponseDTO.setLevelId(level.getLevelId());
                levelResponseDTO.setName(level.getName());
                levelResponseDTO.setImageUrl(level.getImageUrl());
                userResponseDTO.setLevel(levelResponseDTO);
            }


            return userResponseDTO;
        }
        return null;
    }
}
