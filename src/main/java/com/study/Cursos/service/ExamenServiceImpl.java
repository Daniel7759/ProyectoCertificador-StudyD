package com.study.Cursos.service;

import com.study.Cursos.model.*;
import com.study.Cursos.repository.CursoRepository;
import com.study.Cursos.repository.ExamenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ExamenServiceImpl implements ExamenService{

    private final ExamenRepository examenRepository;

    private final CursoRepository cursoRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, CursoRepository cursoRepository) {
        this.examenRepository = examenRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Examen insert(Examen examen) {
        return examenRepository.save(examen);
    }

    @Override
    public Examen findById(Long examenId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("El examen especificado no existe."));

        Curso cursoExamen = cursoRepository.findById(examen.getCurso().getCursoId())
                .orElseThrow(() -> new RuntimeException("El curso especificado no existe."));

        for (Tema tema : cursoExamen.getTemas()) {
            Tarea tarea = tema.getTarea(); // Obtenemos la tarea del tema
            if (tarea != null) {
                List<Pregunta> preguntasTarea = new ArrayList<>(tarea.getPreguntas());
                Collections.shuffle(preguntasTarea);
                int cantidadPreguntasAgregar = Math.min(preguntasTarea.size(), 2); // Por ejemplo, limitamos a 2 preguntas por tarea
                examen.getPreguntas().addAll(preguntasTarea.subList(0, cantidadPreguntasAgregar));
                int cantidadPreguntas = examen.getPreguntas().size();
                double valorPregunta = (double) 20 /cantidadPreguntas;
                examen.setPuntajePregunta(valorPregunta);
            }
        }
        return examen;
    }

    @Override
    public Examen findByTitulo(String title) {
        return examenRepository.findByTitulo(title);
    }

    @Override
    public Collection<Examen> findAll() {
        return examenRepository.findAll();
    }
}
