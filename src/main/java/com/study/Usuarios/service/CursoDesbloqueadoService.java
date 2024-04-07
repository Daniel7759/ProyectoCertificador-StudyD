package com.study.Usuarios.service;

import com.study.Usuarios.model.CursoDesbloqueado;

import java.util.Collection;

public interface CursoDesbloqueadoService {

    CursoDesbloqueado insert(CursoDesbloqueado cursoDesbloqueado);

    CursoDesbloqueado update(Long cursoUnlockedId, Double notaExamen);

    CursoDesbloqueado findById(Long cursoUnlockedId);

    CursoDesbloqueado findByUserIdAndCursoId(Long usertId, Long cursoId);

    Collection<CursoDesbloqueado> findAll();
}
