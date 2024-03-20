package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Materia;

import java.util.Collection;

public interface CursoService {

    public abstract Curso insert(Curso curso);
    public abstract Curso findByTitle(String title);
    public abstract Curso findById(Long cursoId);
    public abstract Collection<Curso> findAll();
    public abstract Collection<Curso> findAllByMateriaId(Long materiaId);
    public abstract Collection<Curso> findAllByFechaCreacionDesc();
}
