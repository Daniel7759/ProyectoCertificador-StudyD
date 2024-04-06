package com.study.Cursos.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogroDTO {
    private Long logroId;
    private String nombreLogro;
    private String imagenLogro;
}
