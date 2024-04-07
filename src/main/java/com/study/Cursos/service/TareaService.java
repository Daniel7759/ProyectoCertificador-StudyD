package com.study.Cursos.service;

import com.study.Cursos.model.Tarea;

import java.util.Collection;

public interface TareaService {

    Tarea insert(Tarea tarea);
    Tarea findByTitulo(String titulo);
    Tarea findById(Long tareaId);
    Collection<Tarea> findAll();
}
