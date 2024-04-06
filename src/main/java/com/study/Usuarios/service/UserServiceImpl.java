package com.study.Usuarios.service;

import com.study.Cursos.DTO.LogroDTO;
import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Logro;
import com.study.Cursos.service.CursoService;
import com.study.Cursos.service.LogroService;
import com.study.Niveles.model.Level;
import com.study.Niveles.model.LevelResponseDTO;
import com.study.Niveles.repository.LevelRepository;
import com.study.Usuarios.model.*;
import com.study.Usuarios.repository.CursoDesbloqueadoRepository;
import com.study.Usuarios.repository.RoleRepository;
import com.study.Usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CursoDesbloqueadoRepository cursoDesbloqueadoRepository;

    @Autowired
    private LogroService logroService;

    @Autowired
    private CursoService cursoService;

    @Override
    public User insert(User user) {
        // Codificar la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Long levelId=1L;
        Level level=levelRepository.findById(levelId).orElse(null);

        if (level!=null){
            level.getUsers().add(user);
            user.setLevel(level);

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                // Si no tiene ningún rol asignado, asignar el rol de usuario (USER)
                Role userRole = roleRepository.findByNombreRol(EnumRoles.USER);
                if (userRole == null) {
                    // Si el rol no existe, crearlo y guardarlo en la base de datos
                    userRole = Role.builder().nombreRol(EnumRoles.USER).build();
                    roleRepository.save(userRole);
                }
                user.setRoles(Collections.singleton(userRole)); // Asignar el rol al usuario
            }
            return userRepository.save(user);
        }else {
            return null;
        }
    }

    @Override
    public User update(User user, String passwordActual) {
        // Obtener el usuario existente de la base de datos
        User existingUser = userRepository.findById(user.getUsertId()).orElse(null);
        if (existingUser != null && passwordEncoder.matches(passwordActual, existingUser.getPassword())) {
            // Actualizar los campos permitidos
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());

            User emailValido= userRepository.findByEmail(user.getEmail());
            User usernameValido= userRepository.findByUsername(user.getUsername());
            if (emailValido==null){
                existingUser.setEmail(user.getEmail());
            }else{
                throw new RuntimeException("Este email ya esta en uso");
            }
            if (usernameValido==null){
                existingUser.setUsername(user.getUsername());
            }else {
                throw new RuntimeException("Este nombre de usuario ya existe");
            }

            // Verificar si se proporcionó una nueva contraseña y codificarla
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            existingUser.setPhone(user.getPhone());
            // Guardar los cambios
            return userRepository.save(existingUser);
        } else {
            // El usuario no existe, puedes lanzar una excepción o devolver null según tu lógica de negocio
            throw  new RuntimeException("la contraseña actual no coincide");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User authenticate(String email, String password) {
        User userDB = userRepository.findByEmail(email);
        if (userDB != null && passwordEncoder.matches(password, userDB.getPassword())){
            return userDB;
        }
        return null;
    }

    @Override
    public User updateStarsAndExperience(Long userId, int estrellas, double experiencia) {
        User usuario = userRepository.findById(userId).orElse(null);
        if (usuario != null) {
            // Si se están restando estrellas, asegúrate de que el resultado no sea negativo
            if (estrellas < 0 && usuario.getStars() < -estrellas) {
                estrellas = -usuario.getStars(); // Resta solo hasta cero
            }
            if (experiencia < 0) {
                experiencia = 0;
            }
            int nuevasEstrellas = usuario.getStars() + estrellas;
            double nuevaExperiencia = usuario.getExperience() + experiencia;

            // Verificar si existe un nivel superior al actual
            Level levelActual = usuario.getLevel();
            Level levelSiguiente = levelRepository.findById(levelActual.getLevelId() + 1).orElse(null);

            if (levelSiguiente != null && nuevaExperiencia >= levelSiguiente.getXpNeeded()) {
                // Si hay un nivel superior y la nueva experiencia es suficiente, actualizar el nivel
                usuario.setLevel(levelSiguiente);
            }

            usuario.setStars(nuevasEstrellas);
            usuario.setExperience(nuevaExperiencia);
            userRepository.save(usuario);
        }
        return usuario;
    }

    @Override
    public User subtractStars(Long userId, int estrellas) {
        return updateStarsAndExperience(userId,-estrellas,0);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO findAllUserWithLevel(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Level level = levelRepository.findById(user.getLevel().getLevelId()).orElse(null);
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setUserId(user.getUsertId());
            userResponseDTO.setFirstname(user.getFirstname());
            userResponseDTO.setLastname(user.getLastname());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setUsername(user.getUsername());
            userResponseDTO.setPhone(user.getPhone());
            userResponseDTO.setStars(user.getStars());
            userResponseDTO.setExperience(user.getExperience());


            // Mapeo del objeto Level
            if (level != null) {
                LevelResponseDTO levelResponseDTO = new LevelResponseDTO();
                levelResponseDTO.setLevelId(level.getLevelId());
                levelResponseDTO.setName(level.getName());
                levelResponseDTO.setImageUrl(level.getImageUrl());

                Level levelSiguiente = levelRepository.findById(level.getLevelId() + 1).orElse(null);
                if (levelSiguiente!=null){
                    levelResponseDTO.setXpToNextLevel(levelSiguiente.getXpNeeded());
                }

                userResponseDTO.setLevel(levelResponseDTO);
            }

            List<CursoDesbloqueado> cursosDesbloqueados = cursoDesbloqueadoRepository.findByUsertId(userId);
            if (cursosDesbloqueados != null && !cursosDesbloqueados.isEmpty()) {
                List<CursoDesbloqueadoDTO> cursosDesbloqueadosDTO = new ArrayList<>();
                for (CursoDesbloqueado cursoDesbloqueado : cursosDesbloqueados) {
                    CursoDesbloqueadoDTO cursoDesbloqueadoDTO = getCursoDesbloqueadoDTO(cursoDesbloqueado);
                    cursosDesbloqueadosDTO.add(cursoDesbloqueadoDTO);
                }
                userResponseDTO.setCursosDesbloqueados(cursosDesbloqueadosDTO);
            }

            // Mapeo de los logros del usuario
            Set<Logro> logros = user.getLogros();
            if (logros != null && !logros.isEmpty()) {
                List<LogroDTO> logrosDTO = new ArrayList<>();
                for (Logro logro : logros) {
                    LogroDTO logroDTO = new LogroDTO();
                    logroDTO.setLogroId(logro.getLogroId());
                    logroDTO.setNombreLogro(logro.getNombreLogro());
                    logroDTO.setImagenLogro(logro.getImagenLogro());
                    logrosDTO.add(logroDTO);
                }
                userResponseDTO.setLogros(logrosDTO);
            }

            return userResponseDTO;
        }
        return null;
    }

    private static CursoDesbloqueadoDTO getCursoDesbloqueadoDTO(CursoDesbloqueado cursoDesbloqueado) {
        CursoDesbloqueadoDTO cursoDesbloqueadoDTO = new CursoDesbloqueadoDTO();
        cursoDesbloqueadoDTO.setCursoUnlockedId(cursoDesbloqueado.getCursoUnlockedId());
        cursoDesbloqueadoDTO.setUsertId(cursoDesbloqueado.getUsertId());
        cursoDesbloqueadoDTO.setCursoId(cursoDesbloqueado.getCursoId());
        cursoDesbloqueadoDTO.setFechaDesbloqueo(cursoDesbloqueado.getFechaDesbloqueo());
        cursoDesbloqueadoDTO.setPromedioTareas(cursoDesbloqueado.getPromedioTareas());
        cursoDesbloqueadoDTO.setNotaExamen(cursoDesbloqueado.getNotaExamen());
        cursoDesbloqueadoDTO.setTiempoCompletado(cursoDesbloqueado.getTiempoCompletado());
        cursoDesbloqueadoDTO.setEstadoCurso(cursoDesbloqueado.getEstadoCurso());
        return cursoDesbloqueadoDTO;
    }

    @Override
    public List<Curso> bucarCursosConDatos(Long userId) {
        List<Curso> cursosDesbloqueadosDatos = new ArrayList<>();

        // Obtener el usuario
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Recorrer la lista de cursos desbloqueados del usuario
            for (CursoDesbloqueado cursoDesbloqueado : user.getCursosDesbloqueados()) {
                // Obtener el curso por su ID
                Curso curso = cursoService.findById(cursoDesbloqueado.getCursoId());
                if (curso != null) {
                    cursosDesbloqueadosDatos.add(curso);
                }
            }
        }
        return cursosDesbloqueadosDatos;
    }
}
