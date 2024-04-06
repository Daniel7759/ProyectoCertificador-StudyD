package com.study.Usuarios.repository;

import com.study.Usuarios.model.CursoDesbloqueado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoDesbloqueadoRepository extends JpaRepository<CursoDesbloqueado, Long> {

    CursoDesbloqueado findByUsertIdAndCursoId(Long usertId, Long cursoId);
    List<CursoDesbloqueado> findByUsertId(Long usertId);
}
