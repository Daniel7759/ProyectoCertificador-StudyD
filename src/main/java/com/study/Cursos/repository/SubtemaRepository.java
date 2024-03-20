package com.study.Cursos.repository;

import com.study.Cursos.model.Subtema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtemaRepository extends JpaRepository<Subtema,Long> {

    public abstract Subtema findByTitulo(String titulo);
}
