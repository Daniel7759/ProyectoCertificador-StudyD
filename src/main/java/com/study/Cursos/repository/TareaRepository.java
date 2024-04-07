package com.study.Cursos.repository;

import com.study.Cursos.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    Tarea findByTitulo(String titulo);
}
