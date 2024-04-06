package com.study.Usuarios.service;

import com.study.Usuarios.model.CursoDesbloqueado;

import java.util.Collection;

public interface CursoDesbloqueadoService {

    public abstract CursoDesbloqueado insert(CursoDesbloqueado cursoDesbloqueado);

    public abstract CursoDesbloqueado findById(Long cursoUnlockedId);

    public abstract CursoDesbloqueado findByUserIdAndCursoId(Long usertId, Long cursoId);

    public abstract Collection<CursoDesbloqueado> findAll();
}
