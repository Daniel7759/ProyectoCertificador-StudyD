package com.study.Usuarios.service;

import com.study.Cursos.DTO.LogroDTO;
import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Logro;
import com.study.Cursos.service.CursoService;
import com.study.Niveles.model.Level;
import com.study.Niveles.model.LevelResponseDTO;
import com.study.Niveles.repository.LevelRepository;
import com.study.Usuarios.model.*;
import com.study.Usuarios.repository.CursoDesbloqueadoRepository;
import com.study.Usuarios.repository.RoleRepository;
import com.study.Usuarios.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final LevelRepository levelRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final CursoDesbloqueadoRepository cursoDesbloqueadoRepository;

    private final CursoService cursoService;

    public UserServiceImpl(UserRepository userRepository, LevelRepository levelRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CursoDesbloqueadoRepository cursoDesbloqueadoRepository, CursoService cursoService) {
        this.userRepository = userRepository;
        this.levelRepository = levelRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cursoDesbloqueadoRepository = cursoDesbloqueadoRepository;
        this.cursoService = cursoService;
    }

    @Override
    public User insert(User user) {
        encodePassword(user);

        Level level = getInitialLevel();

        level.getUsers().add(user);
        user.setLevel(level);

        assignUserRole(user);

        return userRepository.save(user);
    }

    @Override
    public User update(User user, String passwordActual) {
        User existingUser = userRepository.findById(user.getUsertId())
                .orElseThrow(() -> new RuntimeException("El usuario especificado no existe."));
        if (passwordEncoder.matches(passwordActual, existingUser.getPassword())) {
            updateUserFields(user, existingUser);
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("La contraseña actual no coincide");
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
        if (userDB == null) {
            throw new RuntimeException("El usuario especificado no existe.");
        }
        if (!passwordEncoder.matches(password, userDB.getPassword())) {
            throw new RuntimeException("La contraseña proporcionada no coincide.");
        }
        return userDB;
    }

    @Override
    public User updateStarsAndExperience(Long userId, int estrellas, double experiencia) {
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("El usuario especificado no existe."));

        updateStars(usuario, estrellas);
        updateExperience(usuario, experiencia);
        updateLevel(usuario);

        userRepository.save(usuario);
        return usuario;
    }

    @Override
    public User subtractStars(Long userId, int estrellas) {
        if (estrellas < 0) {
            throw new IllegalArgumentException("El número de estrellas a restar debe ser positivo");
        }
        return updateStarsAndExperience(userId, -estrellas, 0);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDTO findAllUserWithLevel(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("El usuario especificado no existe."));

        Level level = levelRepository.findById(user.getLevel().getLevelId())
                .orElseThrow(() -> new RuntimeException("El nivel especificado no existe."));

        UserResponseDTO userResponseDTO = mapUserToUserResponseDTO(user, level);

        List<CursoDesbloqueado> cursosDesbloqueados = cursoDesbloqueadoRepository.findByUsertId(userId);
        if (!cursosDesbloqueados.isEmpty()) {
            List<CursoDesbloqueadoDTO> cursosDesbloqueadosDTO = cursosDesbloqueados.stream()
                    .map(this::getCursoDesbloqueadoDTO)
                    .collect(Collectors.toList());
            userResponseDTO.setCursosDesbloqueados(cursosDesbloqueadosDTO);
        }

        Set<Logro> logros = user.getLogros();
        if (!logros.isEmpty()) {
            List<LogroDTO> logrosDTO = logros.stream()
                    .map(this::getLogroDTO)
                    .collect(Collectors.toList());
            userResponseDTO.setLogros(logrosDTO);
        }

        return userResponseDTO;
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

    private UserResponseDTO mapUserToUserResponseDTO(User user, Level level) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(user.getUsertId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setStars(user.getStars());
        userResponseDTO.setExperience(user.getExperience());

        LevelResponseDTO levelResponseDTO = new LevelResponseDTO();
        levelResponseDTO.setLevelId(level.getLevelId());
        levelResponseDTO.setName(level.getName());
        levelResponseDTO.setImageUrl(level.getImageUrl());

        levelRepository.findById(level.getLevelId() + 1)
                .ifPresent(nextLevel -> levelResponseDTO.setXpToNextLevel(nextLevel.getXpNeeded()));

        userResponseDTO.setLevel(levelResponseDTO);

        return userResponseDTO;
    }

    private LogroDTO getLogroDTO(Logro logro) {
        LogroDTO logroDTO = new LogroDTO();
        logroDTO.setLogroId(logro.getLogroId());
        logroDTO.setNombreLogro(logro.getNombreLogro());
        logroDTO.setImagenLogro(logro.getImagenLogro());
        return logroDTO;
    }

    //Metodo para obtener un objeto CursoDesbloqueadoDTO a partir de un objeto CursoDesbloqueado
    private CursoDesbloqueadoDTO getCursoDesbloqueadoDTO(CursoDesbloqueado cursoDesbloqueado) {
        CursoDesbloqueadoDTO cursoDesbloqueadoDTO = new CursoDesbloqueadoDTO();
        cursoDesbloqueadoDTO.setCursoUnlockedId(cursoDesbloqueado.getCursoUnlockedId());
        cursoDesbloqueadoDTO.setUsertId(cursoDesbloqueado.getUsertId());
        cursoDesbloqueadoDTO.setCursoId(cursoDesbloqueado.getCursoId());
        cursoDesbloqueadoDTO.setFechaDesbloqueo(cursoDesbloqueado.getFechaDesbloqueo());
        cursoDesbloqueadoDTO.setFechaDesbloqueo(cursoDesbloqueado.getFechaFinalizado());
        cursoDesbloqueadoDTO.setNotaExamen(cursoDesbloqueado.getNotaExamen());
        cursoDesbloqueadoDTO.setTiempoCompletado(cursoDesbloqueado.getTiempoCompletado());
        cursoDesbloqueadoDTO.setEstadoCurso(cursoDesbloqueado.getEstadoCurso());
        return cursoDesbloqueadoDTO;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private Level getInitialLevel() {
        return levelRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("El nivel inicial no existe."));
    }

    private void assignUserRole(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByNombreRol(EnumRoles.USER);
            if (userRole == null) {
                userRole = Role.builder().nombreRol(EnumRoles.USER).build();
                roleRepository.save(userRole);
            }
            user.setRoles(Collections.singleton(userRole));
        }
    }

    private void updateUserFields(User user, User existingUser) {
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        validateAndSetEmail(user, existingUser);
        validateAndSetUsername(user, existingUser);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existingUser.setPhone(user.getPhone());
    }

    private void validateAndSetEmail(User user, User existingUser) {
        User emailValido = userRepository.findByEmail(user.getEmail());
        if (emailValido == null) {
            existingUser.setEmail(user.getEmail());
        } else {
            throw new RuntimeException("Este email ya está en uso");
        }
    }

    //Metodo para validar y establecer el nombre de usuario
    private void validateAndSetUsername(User user, User existingUser) {
        User usernameValido = userRepository.findByUsername(user.getUsername());
        if (usernameValido == null) {
            existingUser.setUsername(user.getUsername());
        } else {
            throw new RuntimeException("Este nombre de usuario ya existe");
        }
    }

    //Metodos para actualizar las estrellas del usuario
    private void updateStars(User usuario, int estrellas) {
        // Si se están restando estrellas, asegúrate de que el resultado no sea negativo
        if (estrellas < 0 && usuario.getStars() < -estrellas) {
            estrellas = -usuario.getStars(); // Resta solo hasta cero
        }
        usuario.setStars(usuario.getStars() + estrellas);
    }

    //Metodos para actualizar la experiencia del usuario
    private void updateExperience(User usuario, double experiencia) {
        if (experiencia < 0) {
            experiencia = 0;
        }
        usuario.setExperience(usuario.getExperience() + experiencia);
    }

    //Metodos para actualizar el nivel del usuario
    private void updateLevel(User usuario) {
        // Verificar si existe un nivel superior al actual
        Level levelActual = usuario.getLevel();
        Level levelSiguiente = levelRepository.findById(levelActual.getLevelId() + 1).orElse(null);

        if (levelSiguiente != null && usuario.getExperience() >= levelSiguiente.getXpNeeded()) {
            // Si hay un nivel superior y la nueva experiencia es suficiente, actualizar el nivel
            usuario.setLevel(levelSiguiente);
        }
    }
}
