package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Materia;
import com.study.Cursos.model.Tema;
import com.study.Cursos.repository.CursoRepository;
import com.study.Cursos.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TemaServiceImpl implements TemaService{

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Tema insert(Tema tema) {

        Long cursoId = tema.getCurso().getCursoId();
        Curso curso = cursoRepository.findById(cursoId).orElse(null);

        if (curso != null){
            curso.getTemas().add(tema);
            tema.setCurso(curso);
            return temaRepository.save(tema);
        }else {
            return null;
        }
    }

    @Override
    public Tema findByTitle(String title) {
        return temaRepository.findByTitle(title);
    }

    @Override
    public Tema findById(Long temaId) {
        return temaRepository.findById(temaId).orElse(null);
    }

    @Override
    public Collection<Tema> findAll() {
        return temaRepository.findAll();
    }
}
