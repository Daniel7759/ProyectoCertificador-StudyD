package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.EnumMateria;
import com.study.Cursos.model.Materia;

import java.util.Collection;

public interface MateriaService {

    public abstract Materia insert(Materia materia);
    public abstract Materia findByName(String name);
    public abstract Materia findById(Long materiaId);
    public abstract Collection<Materia> findAll();
    public abstract Collection<Materia> findAllByType(EnumMateria type);
    public abstract Collection<Curso> obtenerCursosDeMateriasOpcionales();
}
