package com.study.Cursos.repository;

import com.study.Cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {

    public abstract Curso findByTitle(String title);

    public abstract Collection<Curso> findAllByOrderByFechaCreacionDesc();

    public abstract Collection<Curso> findByMateriaMateriaId(Long materiaId);
}
