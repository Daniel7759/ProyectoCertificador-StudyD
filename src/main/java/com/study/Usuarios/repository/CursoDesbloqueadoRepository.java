package com.study.Usuarios.repository;

import com.study.Usuarios.model.CursoDesbloqueado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoDesbloqueadoRepository extends JpaRepository<CursoDesbloqueado, Long> {
}
