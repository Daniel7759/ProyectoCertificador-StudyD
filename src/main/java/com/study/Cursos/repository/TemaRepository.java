package com.study.Cursos.repository;

import com.study.Cursos.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema,Long> {

    Tema findByTitle(String title);
}
