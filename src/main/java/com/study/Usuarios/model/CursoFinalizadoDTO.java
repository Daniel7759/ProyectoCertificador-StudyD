package com.study.Usuarios.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CursoFinalizadoDTO {

    private Long cursoUnlockedId;

    private Long usertId;

    private String nombreCompleto;

    private Long cursoId;

    private String nombreCurso;

    private String imagenCurso;

    private LocalDate fechaDesbloqueo;

    private LocalDate fechaFinalizado;

    private Double notaExamen ;

    private EstadoCurso estadoCurso;
}
