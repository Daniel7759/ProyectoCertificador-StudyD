package com.study.Cursos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "subtemas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subtema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subtemaId;

    @NotBlank(message = "El título del subtema es obligatorio")
    @Size(min = 4, max = 50)
    @Column(nullable = false, length = 50)
    private String titulo;

    @NotBlank(message = "El concepto del subtema es obligatorio")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String concepto;

    @Column(name = "image_ejemplo", columnDefinition = "TEXT")
    private String imageEjemplo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tema_id", referencedColumnName = "temaId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tema tema;
}
