package com.study.Cursos.service;

import com.study.Cursos.model.Logro;
import com.study.Cursos.repository.LogroRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LogroServiceImpl implements LogroService{

    private final LogroRepository logroRepository;

    public LogroServiceImpl(LogroRepository logroRepository) {
        this.logroRepository = logroRepository;
    }

    @Override
    public Logro insert(Logro logro) {
        return logroRepository.save(logro);
    }

    @Override
    public Logro findByNombreLogro(String nombreLogro) {
        return logroRepository.findByNombreLogro(nombreLogro);
    }

    @Override
    public Logro findById(Long logroId) {
        return logroRepository.findById(logroId).orElse(null);
    }

    @Override
    public Collection<Logro> findAll() {
        return logroRepository.findAll();
    }
}
