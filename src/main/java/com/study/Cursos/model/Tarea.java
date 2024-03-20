package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tareaId;

    private String titulo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tarea", cascade = CascadeType.ALL)
    private Set<Pregunta> preguntas = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tema_id", referencedColumnName = "temaId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tema tema;
}
