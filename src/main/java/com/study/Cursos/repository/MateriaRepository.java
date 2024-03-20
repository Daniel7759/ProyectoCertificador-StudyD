package com.study.Cursos.repository;

import com.study.Cursos.model.EnumMateria;
import com.study.Cursos.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    public abstract Materia findByName(String name);

    public abstract Collection<Materia> findByType(EnumMateria type);
}
