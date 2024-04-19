package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "examenes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examenId;

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @Transient
    private List<Pregunta> preguntas = new ArrayList<>();

    @Transient
    private double puntajePregunta;

    @NotNull(message = "El campo starsBonus es obligatorio")
    @PositiveOrZero(message = "El valor del bono de estrellas debe ser positivo o cero")
    private int starsBonus;

    @NotNull(message = "El campo xpBonus es obligatorio")
    @PositiveOrZero(message = "El valor del bono de experiencia debe ser positivo o cero")
    private double xpBonus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false, referencedColumnName = "cursoId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Curso curso;


}
