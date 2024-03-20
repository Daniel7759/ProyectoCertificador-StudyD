package com.study.Usuarios.model;

import com.study.Niveles.model.LevelResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String phone;
    private int stars;
    private double experience;
    private LevelResponseDTO level;
    private List<UserCursoDTO> cursosDesbloqueados;
}
