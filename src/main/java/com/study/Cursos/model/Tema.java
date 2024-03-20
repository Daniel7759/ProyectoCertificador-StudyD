package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "temas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long temaId;

    @NotBlank(message = "El título del tema es obligatorio")
    @Size(min = 4, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotBlank(message = "El enunciado del tema es obligatorio")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tema", cascade = CascadeType.ALL)
    private Set<Subtema> subtemas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", referencedColumnName = "cursoId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Curso curso;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "tema", cascade = CascadeType.ALL)
    private Tarea tarea;
}
