package com.study.Cursos.service;

import com.study.Cursos.model.Subtema;
import com.study.Cursos.model.Tema;
import com.study.Cursos.repository.SubtemaRepository;
import com.study.Cursos.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SubtemaServiceImpl implements SubtemaService{

    @Autowired
    private SubtemaRepository subtemaRepository;

    @Autowired
    private TemaRepository temaRepository;

    @Override
    public Subtema insert(Subtema subtema) {
        Long temaId = subtema.getTema().getTemaId();
        Tema tema = temaRepository.findById(temaId).orElse(null);

        if (tema != null){
            tema.getSubtemas().add(subtema);
            subtema.setTema(tema);
            return subtemaRepository.save(subtema);
        }else {
            return null;
        }
    }

    @Override
    public Subtema findByTitulo(String titulo) {
        return subtemaRepository.findByTitulo(titulo);
    }

    @Override
    public Subtema findById(Long subtemaId) {
        return subtemaRepository.findById(subtemaId).orElse(null);
    }

    @Override
    public Collection<Subtema> findAll() {
        return subtemaRepository.findAll();
    }
}
