package com.study.Cursos.repository;

import com.study.Cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {

    Curso findByTitle(String title);

    Curso findByCursoAnteriorId(Long cursoAnteriorId);

    Collection<Curso> findAllByOrderByFechaCreacionDesc();

    Collection<Curso> findByMateriaMateriaId(Long materiaId);
}
