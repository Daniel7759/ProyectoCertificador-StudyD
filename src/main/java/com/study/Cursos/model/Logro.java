package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "logros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logroId;

    @NotBlank(message = "El nombre debe ser obligatorio")
    @Size(min = 2,max = 30)
    @Column(length = 30, nullable = false)
    private String nombreLogro;

    @Column(name = "imagen_logro", columnDefinition = "TEXT")
    private String imagenLogro;

    @NotNull(message = "El campo starsBonus es obligatorio")
    @PositiveOrZero(message = "El valor del bono de estrellas debe ser positivo o cero")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int starsBonus;

    @NotNull(message = "El campo xpBonus es obligatorio")
    @PositiveOrZero(message = "El valor del bono de experiencia debe ser positivo o cero")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double xpBonus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false, referencedColumnName = "cursoId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Curso curso;

}
