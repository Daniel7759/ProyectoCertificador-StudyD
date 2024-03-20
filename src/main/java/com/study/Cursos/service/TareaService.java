package com.study.Cursos.service;

import com.study.Cursos.model.Pregunta;
import com.study.Cursos.model.Tarea;

import java.util.Collection;

public interface TareaService {

    public abstract Tarea insert(Tarea tarea);
    public abstract Tarea findByTitulo(String titulo);
    public abstract Tarea findById(Long tareaId);
    public abstract Collection<Tarea> findAll();
}
