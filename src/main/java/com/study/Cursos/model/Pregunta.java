package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preguntaId;

    private String pregunta;

    @ElementCollection
    @CollectionTable(name = "opciones_respuesta", joinColumns = @JoinColumn(name = "pregunta_id"))
    @Column(name = "opcion")
    private Set<String> opcionesRespuesta = new HashSet<>();

    private String respuestaCorrecta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tarea_id", nullable = false, referencedColumnName = "tareaId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tarea tarea;
}
