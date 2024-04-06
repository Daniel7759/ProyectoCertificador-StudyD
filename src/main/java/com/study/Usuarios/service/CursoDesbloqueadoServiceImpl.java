package com.study.Usuarios.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.repository.CursoRepository;
import com.study.Usuarios.model.CursoDesbloqueado;
import com.study.Usuarios.model.EstadoCurso;
import com.study.Usuarios.model.User;
import com.study.Usuarios.repository.CursoDesbloqueadoRepository;
import com.study.Usuarios.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public CursoDesbloqueado insert(CursoDesbloqueado cursoDesbloqueado) {
        Curso curso = cursoRepository.findById(cursoDesbloqueado.getCursoId()).orElse(null);
        User usuario = usuarioRepository.findById(cursoDesbloqueado.getUsertId()).orElse(null);

        if (curso != null) {
            //Obtemos el costo de curso
            int costoCurso = curso.getPrice();
            // Verificar si el curso tiene un curso anterior
            if (curso.getCursoAnteriorId() == null) {
                userService.subtractStars(cursoDesbloqueado.getUsertId(),costoCurso);
                return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
            } else {
                Curso cursoAnterior = cursoRepository.findById(curso.getCursoAnteriorId()).orElse(null);

                // Verificar si el curso anterior está desbloqueado y finalizado para el usuario
                if (cursoAnterior != null && usuario != null && usuario.getCursosDesbloqueados().stream()
                        .anyMatch(c -> c.getCursoId().equals(cursoAnterior.getCursoId()) && c.getEstadoCurso().equals(EstadoCurso.FINALIZADO))) {

                    //Evaluamos si en usuario tiene suficientes estrellas para desbloquearlo
                    if (usuario.getStars()>=costoCurso){
                        userService.subtractStars(cursoDesbloqueado.getUsertId(),costoCurso);
                        return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
                    }else {
                        throw new RuntimeException("El usuario no tiene suficientes estrellas.");
                    }
                } else {
                    // Curso anterior no desbloqueado o no finalizado, no se puede desbloquear este curso
                    throw new RuntimeException("No se puede desbloquear el curso actual porque el curso anterior "+cursoAnterior.getTitle()+" no está finalizado.");
                }
            }
        } else {
            // No se encontró el curso, lanzar una excepción
            throw new RuntimeException("El curso especificado no existe.");
        }
    }

    @Override
    @Transactional
    public CursoDesbloqueado findById(Long cursoUnlockedId) {
        return cursoDesbloqueadoRepository.findById(cursoUnlockedId).orElse(null);
    }

    @Override
    @Transactional
    public CursoDesbloqueado findByUserIdAndCursoId(Long usertId, Long cursoId) {
        return cursoDesbloqueadoRepository.findByUsertIdAndCursoId(usertId, cursoId);
    }

    @Override
    @Transactional
    public Collection<CursoDesbloqueado> findAll() {
        return cursoDesbloqueadoRepository.findAll();
    }
}
