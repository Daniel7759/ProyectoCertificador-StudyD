package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.EnumMateria;
import com.study.Cursos.model.Materia;
import com.study.Cursos.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MateriaServiceImpl implements MateriaService{

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Materia insert(Materia materia) {
        return materiaRepository.save(materia);
    }

    @Override
    public Materia findByName(String name) {
        return materiaRepository.findByName(name);
    }

    @Override
    public Materia findById(Long materiaId) {
        return materiaRepository.findById(materiaId).orElse(null);
    }

    @Override
    public Collection<Materia> findAll() {
        return materiaRepository.findAll();
    }

    @Override
    public Collection<Materia> findAllByType(EnumMateria type) {
        return materiaRepository.findByType(type);
    }

    @Override
    public Collection<Curso> obtenerCursosDeMateriasOpcionales() {
        Collection<Materia> materiasOpcionales = materiaRepository.findByType(EnumMateria.OPCIONAL);
        Collection<Curso> cursos = new ArrayList<>();
        for (Materia materia:materiasOpcionales){
            cursos.addAll(materia.getCursos());
        }
        return cursos;
    }
}
