package com.study.Cursos.service;

import com.study.Cursos.model.Curso;

import java.util.Collection;

public interface CursoService {

    Curso insert(Curso curso);
    Curso findByTitle(String title);
    Curso findById(Long cursoId);
    Collection<Curso> findAll();
    Collection<Curso> findAllByMateriaId(Long materiaId);
    Collection<Curso> findAllByFechaCreacionDesc();
}
