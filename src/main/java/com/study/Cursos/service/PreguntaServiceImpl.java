package com.study.Cursos.service;

import com.study.Cursos.model.Pregunta;
import com.study.Cursos.model.Tarea;
import com.study.Cursos.repository.PreguntaRepository;
import com.study.Cursos.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PreguntaServiceImpl implements PreguntaService{

    private final PreguntaRepository preguntaRepository;

    private final TareaRepository tareaRepository;

    public PreguntaServiceImpl(PreguntaRepository preguntaRepository, TareaRepository tareaRepository) {
        this.preguntaRepository = preguntaRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    public Pregunta insert(Pregunta pregunta) {

        Long tareaId = pregunta.getTarea().getTareaId();
        Tarea tarea = tareaRepository.findById(tareaId).orElse(null);

        if (tarea != null) {
            tarea.getPreguntas().add(pregunta);
            pregunta.setTarea(tarea);
            return preguntaRepository.save(pregunta);
        } else {
            return null;
        }
    }

    @Override
    public Pregunta findByPregunta(String pregunta) {
        return preguntaRepository.findByPregunta(pregunta);
    }

    @Override
    public Pregunta findById(Long preguntaId) {
        return preguntaRepository.findById(preguntaId).orElse(null);
    }

    @Override
    public Collection<Pregunta> findAll() {
        return preguntaRepository.findAll();
    }
}
