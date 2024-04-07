package com.study.Cursos.service;

import com.study.Cursos.model.Tema;

import java.util.Collection;

public interface TemaService {

    Tema insert(Tema tema);
    Tema findByTitle(String title);
    Tema findById(Long temaId);
    Collection<Tema> findAll();
}
