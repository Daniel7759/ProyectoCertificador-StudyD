package com.study.Cursos.service;

import com.study.Cursos.model.Examen;

import java.util.Collection;

public interface ExamenService {

    Examen insert(Examen examen);
    Examen findById(Long examenId);
    Examen findByTitulo(String title);
    Collection<Examen> findAll();
}
