package com.study.Cursos.service;

import com.study.Cursos.model.Tema;

import java.util.Collection;

public interface TemaService {

    public abstract Tema insert(Tema tema);
    public abstract Tema findByTitle(String title);
    public abstract Tema findById(Long temaId);
    public abstract Collection<Tema> findAll();
}
