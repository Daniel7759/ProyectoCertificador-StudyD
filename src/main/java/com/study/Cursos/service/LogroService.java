package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Logro;

import java.util.Collection;

public interface LogroService {

    public abstract Logro insert(Logro logro);
    public abstract Logro findByNombreLogro(String nombreLogro);
    public abstract Logro findById(Long logroId);
    public abstract Collection<Logro> findAll();
}
