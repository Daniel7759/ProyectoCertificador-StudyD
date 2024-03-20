package com.study.Cursos.repository;

import com.study.Cursos.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    public abstract Pregunta findByPregunta(String pregunta);
}
