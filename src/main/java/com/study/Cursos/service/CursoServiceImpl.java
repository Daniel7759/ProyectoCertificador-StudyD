package com.study.Cursos.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Materia;
import com.study.Cursos.repository.CursoRepository;
import com.study.Cursos.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CursoServiceImpl implements CursoService{

    private final CursoRepository cursoRepository;

    private final MateriaRepository materiaRepository;

    public CursoServiceImpl(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Curso insert(Curso curso) {

        Long materiaId = curso.getMateria().getMateriaId();
        Materia materia = materiaRepository.findById(materiaId).orElse(null);

        if (materia != null) {
            materia.getCursos().add(curso);
            curso.setMateria(materia);
            return cursoRepository.save(curso);
        } else {

            return null;
        }
    }

    @Override
    public Curso findByTitle(String title) {
        return cursoRepository.findByTitle(title);
    }

    @Override
    public Curso findById(Long cursoId) {
        return cursoRepository.findById(cursoId).orElse(null);
    }

    @Override
    public Collection<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Collection<Curso> findAllByMateriaId(Long materiaId) {
        return cursoRepository.findByMateriaMateriaId(materiaId);
    }

    @Override
    public Collection<Curso> findAllByFechaCreacionDesc() {
        return cursoRepository.findAllByOrderByFechaCreacionDesc();
    }

    @Override
    public Curso findNextCurso(Long cursoId) {
        Curso nextCurso = cursoRepository.findByCursoAnteriorId(cursoId);
        if (nextCurso != null) {
            return nextCurso;
        } else {
            throw new RuntimeException("No hay un curso siguiente");
        }
    }
}
