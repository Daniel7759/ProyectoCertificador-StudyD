package com.study.Cursos.repository;

import com.study.Cursos.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    Examen findByTitulo(String titulo);
    Examen findByCursoCursoId(Long cursoId);
}
