package com.study.Cursos.repository;

import com.study.Cursos.model.Logro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogroRepository extends JpaRepository<Logro, Long> {

    Logro findByNombreLogro(String nombreLogro);
}
