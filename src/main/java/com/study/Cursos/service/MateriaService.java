package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.EnumMateria;
import com.study.Cursos.model.Materia;

import java.util.Collection;

public interface MateriaService {

    Materia insert(Materia materia);
    Materia findByName(String name);
    Materia findById(Long materiaId);
    Collection<Materia> findAll();
    Collection<Materia> findAllByType(EnumMateria type);
    Collection<Curso> obtenerCursosDeMateriasOpcionales();
}
