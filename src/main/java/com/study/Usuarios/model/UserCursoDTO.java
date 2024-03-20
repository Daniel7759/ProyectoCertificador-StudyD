package com.study.Usuarios.model;

import com.study.Cursos.model.Curso;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCursoDTO {

    private Long cursoUnlockedId;

    private Curso curso;

    private LocalDate fechaDesbloqueo;

    private Double promedioTareas;

    private Double notaExamen;

    private Duration tiempoCompletado;

    private EstadoCurso estadoCurso;
}
