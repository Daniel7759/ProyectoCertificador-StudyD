package com.study.Cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "materias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materiaId;
    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Column(name = "name", nullable = false)
    private String name;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EnumMateria type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "materia", cascade = CascadeType.ALL)
    private Set<Curso> cursos = new HashSet<>();
}
