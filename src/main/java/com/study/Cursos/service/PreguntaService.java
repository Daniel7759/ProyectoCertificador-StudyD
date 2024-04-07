package com.study.Cursos.service;

import com.study.Cursos.model.Pregunta;

import java.util.Collection;

public interface PreguntaService {

    Pregunta insert(Pregunta pregunta);
    Pregunta findByPregunta(String pregunta);
    Pregunta findById(Long preguntaId);
    Collection<Pregunta> findAll();
}
