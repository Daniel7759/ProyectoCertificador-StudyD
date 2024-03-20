package com.study.Usuarios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.Cursos.model.Curso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "user_curso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoUnlockedId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "usertId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", referencedColumnName = "cursoId", nullable = false)
    @NotNull
    private Curso curso;

    @Column(name = "fecha_desbloqueo")
    private LocalDate fechaDesbloqueo;

    @Column(name = "promedio_tareas")
    private Double promedioTareas = 0.0;

    @Column(name = "nota_examen")
    private Double notaExamen = 0.0;

    @Column(name = "tiempo_completado")
    private Duration tiempoCompletado = Duration.ZERO;

    @Column(name = "estado_curso")
    @Enumerated(EnumType.STRING)
    private EstadoCurso estadoCurso = EstadoCurso.ACTIVO;

    @PrePersist
    public void prePersist() {
        this.fechaDesbloqueo = LocalDate.now();
    }
}
