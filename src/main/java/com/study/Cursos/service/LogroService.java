package com.study.Cursos.service;

import com.study.Cursos.model.Logro;

import java.util.Collection;

public interface LogroService {

    Logro insert(Logro logro);
    Logro findByNombreLogro(String nombreLogro);
    Logro findById(Long logroId);
    Collection<Logro> findAll();
}
