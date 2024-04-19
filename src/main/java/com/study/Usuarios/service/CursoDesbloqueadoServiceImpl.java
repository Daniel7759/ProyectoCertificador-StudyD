package com.study.Usuarios.service;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Logro;
import com.study.Cursos.repository.CursoRepository;
import com.study.Usuarios.model.CursoDesbloqueado;
import com.study.Usuarios.model.EstadoCurso;
import com.study.Usuarios.model.User;
import com.study.Usuarios.repository.CursoDesbloqueadoRepository;
import com.study.Usuarios.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class CursoDesbloqueadoServiceImpl implements CursoDesbloqueadoService {

    private final CursoDesbloqueadoRepository cursoDesbloqueadoRepository;

    private final UserRepository usuarioRepository;

    private final CursoRepository cursoRepository;

    private final UserService userService;

    public CursoDesbloqueadoServiceImpl(CursoDesbloqueadoRepository cursoDesbloqueadoRepository, UserRepository usuarioRepository, CursoRepository cursoRepository, UserService userService) {
        this.cursoDesbloqueadoRepository = cursoDesbloqueadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public CursoDesbloqueado insert(CursoDesbloqueado cursoDesbloqueado) {
        Curso curso = cursoRepository.findById(cursoDesbloqueado.getCursoId())
                .orElseThrow(() -> new RuntimeException("El curso especificado no existe."));
        User usuario = usuarioRepository.findById(cursoDesbloqueado.getUsertId())
                .orElseThrow(() -> new RuntimeException("El usuario especificado no existe."));

        int costoCurso = curso.getPrice();

        if (curso.getCursoAnteriorId() == null) {
            userService.subtractStars(cursoDesbloqueado.getUsertId(), costoCurso);
            return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
        }

        Curso cursoAnterior = cursoRepository.findById(curso.getCursoAnteriorId())
                .orElseThrow(() -> new RuntimeException("El curso anterior no existe."));

        boolean cursoAnteriorFinalizado = usuario.getCursosDesbloqueados().stream()
                .anyMatch(c -> c.getCursoId().equals(cursoAnterior.getCursoId()) && c.getEstadoCurso().equals(EstadoCurso.FINALIZADO));

        if (!cursoAnteriorFinalizado) {
            throw new RuntimeException("No se puede desbloquear el curso actual porque el curso anterior " + cursoAnterior.getTitle() + " no está finalizado.");
        }

        if (usuario.getStars() < costoCurso) {
            throw new RuntimeException("El usuario no tiene suficientes estrellas.");
        }

        userService.subtractStars(cursoDesbloqueado.getUsertId(), costoCurso);
        return cursoDesbloqueadoRepository.save(cursoDesbloqueado);
    }

    @Override
    @Transactional
    public CursoDesbloqueado update(Long usertId, Long cursoId, Double notaExamen) {
        CursoDesbloqueado cursoDesbloqueadoActual = cursoDesbloqueadoRepository.findByUsertIdAndCursoId(usertId, cursoId);

        cursoDesbloqueadoActual.setNotaExamen(notaExamen);
        if (notaExamen >= 13) {
            cursoDesbloqueadoActual.setEstadoCurso(EstadoCurso.FINALIZADO);
            cursoDesbloqueadoActual.setFechaFinalizado(LocalDate.now());
            addBonusToExamen(usertId, cursoId);
            addLogroToUser(cursoDesbloqueadoActual.getUsertId(), cursoDesbloqueadoActual.getCursoId());
            return cursoDesbloqueadoRepository.save(cursoDesbloqueadoActual);
        }else{
            // Si es menor a 13, quitar el curso de la lista de cursosDesbloqueados del usuario
            User user = usuarioRepository.findById(usertId).orElse(null);
            if (user != null) {
                user.getCursosDesbloqueados().remove(cursoDesbloqueadoActual);
                usuarioRepository.save(user);
            }
            // No devolver el curso, ya que ha sido eliminado
            return null;
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

    private void addLogroToUser(Long userId, Long cursoId) {
        User usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("El usuario especificado no existe."));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("El curso especificado no existe."));
        Logro logro = curso.getLogro();
        usuario.getLogros().add(logro);
        userService.updateStarsAndExperience(userId,logro.getStarsBonus(),logro.getXpBonus());
        usuarioRepository.save(usuario);
    }

    private void addBonusToExamen(Long userId, Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("El curso especificado no existe."));
        int estrellas = curso.getExamen().getStarsBonus();
        double  experience = curso.getExamen().getXpBonus();
        userService.updateStarsAndExperience(userId,estrellas,experience);
    }
}
