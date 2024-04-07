package com.study.Usuarios.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
@Entity
@Table(name = "curso_desbloqueado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoDesbloqueado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_unlocked_id")
    private Long cursoUnlockedId;

    private Long usertId;

    private Long cursoId;

    @Column(name = "fecha_desbloqueo")
    private LocalDate fechaDesbloqueo;

    @Column(name = "nota_examen")
    private Double notaExamen = 0.0;

    @Column(name = "tiempo_completado")
    private Duration tiempoCompletado = Duration.ZERO;

    @Column(name = "fecha_finalizado")
    private LocalDate fechaFinalizado;

    @Column(name = "estado_curso")
    @Enumerated(EnumType.STRING)
    private EstadoCurso estadoCurso = EstadoCurso.ACTIVO;

    @PrePersist
    public void prePersist() {
        this.fechaDesbloqueo = LocalDate.now();
    }
}
