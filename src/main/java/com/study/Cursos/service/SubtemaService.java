package com.study.Cursos.service;

import com.study.Cursos.model.Subtema;

import java.util.Collection;

public interface SubtemaService {

    public abstract Subtema insert(Subtema subtema);
    public abstract Subtema findByTitulo(String titulo);
    public abstract Subtema findById(Long subtemaId);
    public abstract Collection<Subtema> findAll();
}
