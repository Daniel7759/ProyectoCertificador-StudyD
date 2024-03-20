package com.study.Cursos.service;

import com.study.Cursos.model.Pregunta;

import java.util.Collection;

public interface PreguntaService {

    public abstract Pregunta insert(Pregunta pregunta);
    public abstract Pregunta findByPregunta(String pregunta);
    public abstract Pregunta findById(Long preguntaId);
    public abstract Collection<Pregunta> findAll();
}
