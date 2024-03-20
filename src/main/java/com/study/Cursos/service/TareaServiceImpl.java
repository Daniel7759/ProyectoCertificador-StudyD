package com.study.Cursos.service;

import com.study.Cursos.model.Tarea;
import com.study.Cursos.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TareaServiceImpl implements TareaService{

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public Tarea insert(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea findByTitulo(String titulo) {
        return tareaRepository.findByTitulo(titulo);
    }

    @Override
    public Tarea findById(Long tareaId) {
        return tareaRepository.findById(tareaId).orElse(null);
    }

    @Override
    public Collection<Tarea> findAll() {
        return tareaRepository.findAll();
    }
}
