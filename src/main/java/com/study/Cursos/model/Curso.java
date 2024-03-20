package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoId;

    @NotBlank(message = "El título del curso no puede ser vacio")
    @Size(min = 10, max = 40)
    @Column(name = "title",length = 30, nullable = false)
    private String title;

    @Column(name = "image_url", nullable = true, columnDefinition = "TEXT")
    private String imageUrl;

    @NotBlank(message = "La descripcion del curso es obligatoria")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    private EnumTagCursos tag;

    @NotNull(message = "El precio del curso es obligatorio")
    @Positive(message = "El precio del curso debe ser mayor que cero")
    private Integer price;

    private LocalDate fechaCreacion;

    @Column(name = "curso_anterior_id")
    private Long cursoAnteriorId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Tema> temas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_id", nullable = false,referencedColumnName = "materiaId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Materia materia;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDate.now();
    }

}
