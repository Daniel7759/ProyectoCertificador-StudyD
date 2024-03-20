package com.study.Usuarios.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.repository.CursoRepository;
import com.study.Usuarios.model.CursoDesbloqueado;
import com.study.Usuarios.model.EstadoCurso;
import com.study.Usuarios.model.User;
import com.study.Usuarios.repository.CursoDesbloqueadoRepository;
import com.study.Usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CursoDesbloqueadoServiceImpl implements CursoDesbloqueadoService {

    @Autowired
    private CursoDesbloqueadoRepository cursoDesbloqueadoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public CursoDesbloqueado insert(CursoDesbloqueado cursoDesbloqueado) {
        Curso curso = cursoRepository.findById(cursoDesbloqueado.getCursoId()).orElse(null);
        User usuario = usuarioRepository.findById(cursoDesbloqueado.getUsertId()).orElse(null);

        if (curso != null) {
            // Verificar si el curso tiene un curso anterior
            if (curso.getCursoAnteriorId() == null) {
                // No hay curso anterior, se puede desbloquear sin restricciones
                return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
            } else {
                Curso cursoAnterior = cursoRepository.findById(curso.getCursoAnteriorId()).orElse(null);

                // Verificar si el curso anterior está desbloqueado y finalizado para el usuario
                if (cursoAnterior != null && usuario != null && usuario.getCursosDesbloqueados().stream()
                        .anyMatch(c -> c.getCursoId().equals(cursoAnterior.getCursoId()) && c.getEstadoCurso().equals(EstadoCurso.FINALIZADO))) {
                    return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
                } else {
                    // Curso anterior no desbloqueado o no finalizado, no se puede desbloquear este curso
                    throw new RuntimeException("No se puede desbloquear el curso actual porque el curso anterior no está desbloqueado o finalizado.");
                }
            }
        } else {
            // No se encontró el curso, lanzar una excepción
            throw new RuntimeException("El curso especificado no existe.");
        }
    }

    @Override
    public CursoDesbloqueado findById(Long cursoUnlockedId) {
        return cursoDesbloqueadoRepository.findById(cursoUnlockedId).orElse(null);
    }

    @Override
    public Collection<CursoDesbloqueado> findAll() {
        return cursoDesbloqueadoRepository.findAll();
    }
}
