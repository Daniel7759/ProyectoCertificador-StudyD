package com.study.Usuarios.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
public class CursoDesbloqueadoDTO {

    private Long cursoUnlockedId;

    private Long usertId;

    private Long cursoId;

    private LocalDate fechaDesbloqueo;

    private Double promedioTareas ;

    private Double notaExamen ;

    private Duration tiempoCompletado;

    private EstadoCurso estadoCurso;
}
