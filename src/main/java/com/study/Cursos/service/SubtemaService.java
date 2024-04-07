package com.study.Cursos.service;

import com.study.Cursos.model.Subtema;

import java.util.Collection;

public interface SubtemaService {

    Subtema insert(Subtema subtema);
    Subtema findByTitulo(String titulo);
    Subtema findById(Long subtemaId);
    Collection<Subtema> findAll();
}
