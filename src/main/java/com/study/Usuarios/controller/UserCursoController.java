package com.study.Usuarios.controller;

import com.study.Cursos.model.Curso;
import com.study.Cursos.service.CursoService;
import com.study.Usuarios.model.CursoDesbloqueado;
import com.study.Usuarios.model.TokenFCM;
import com.study.Usuarios.model.User;
import com.study.Usuarios.service.CursoDesbloqueadoService;
import com.study.Usuarios.service.UserService;
import com.study.firebase.FirebaseMessagingService;
import com.study.firebase.NotificationMessage;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "usercursos")
public class UserCursoController {

    private final CursoDesbloqueadoService cursoDesbloqueadoService;

    private final CursoService cursoService;

    private final UserService userService;

    private final FirebaseMessagingService firebaseMessagingService;

    public UserCursoController(CursoDesbloqueadoService cursoDesbloqueadoService, CursoService cursoService, UserService userService, FirebaseMessagingService firebaseMessagingService) {
        this.cursoDesbloqueadoService = cursoDesbloqueadoService;
        this.cursoService = cursoService;
        this.userService = userService;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersCursos(){
        try {
            Collection<CursoDesbloqueado> userCursosDB = cursoDesbloqueadoService.findAll();
            return new ResponseEntity<>(userCursosDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cursoUnlockedId}")
    public ResponseEntity<?> getUserCursoById(@PathVariable Long cursoUnlockedId){
        try{
            CursoDesbloqueado userCursoDB= cursoDesbloqueadoService.findById(cursoUnlockedId);
            return new ResponseEntity<>(userCursoDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUserCurso(@RequestBody CursoDesbloqueado cursoDesbloqueado){
        try {
            // Validar que el curso exista
            Curso curso = cursoService.findById(cursoDesbloqueado.getCursoId());
            if (curso == null) {
                return new ResponseEntity<>("El curso con ID " + cursoDesbloqueado.getCursoId() + " no existe", HttpStatus.BAD_REQUEST);
            }

            User usuarioActual = userService.findById(cursoDesbloqueado.getUsertId());
            // Verificar si el curso ya ha sido desbloqueado anteriormente por el usuario actual
            CursoDesbloqueado existingCurso = cursoDesbloqueadoService.findByUserIdAndCursoId(usuarioActual.getUsertId(), cursoDesbloqueado.getCursoId());
            if (existingCurso != null) {
                return new ResponseEntity<>("Ya has desbloqueado este curso", HttpStatus.NOT_ACCEPTABLE);
            }
            // Asignar el usuario al curso desbloqueado
            cursoDesbloqueado.setUsertId(usuarioActual.getUsertId());
            cursoDesbloqueadoService.insert(cursoDesbloqueado);
            // Enviar un mensaje de éxito
            String mensaje = "El curso" + cursoDesbloqueado.getCursoId() + " ha sido desbloqueado exitosamente";
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/{cursoId}")
    public ResponseEntity<?> updateUserCurso(@PathVariable("userId") Long userId, @PathVariable("cursoId") Long cursoId, @PathParam("notaExamen") Double notaExamen){
        try {
            CursoDesbloqueado cursoDesbloqueadoDB = cursoDesbloqueadoService.findByUserIdAndCursoId(userId, cursoId);
            if (cursoDesbloqueadoDB == null) {
                return new ResponseEntity<>("El curso desbloqueado no existe", HttpStatus.NOT_FOUND);
            }
            if (notaExamen < 0) {
                return new ResponseEntity<>("La nota del examen no puede ser menor a cero", HttpStatus.BAD_REQUEST);
            }
            cursoDesbloqueadoService.update(userId, cursoId, notaExamen);

            if (notaExamen >= 13) {
                //mandamos la notificacion a la app para el siguiente curso
                User user = userService.findById(cursoDesbloqueadoDB.getUsertId());
                Set<String> userTokens = new HashSet<>();
                for (TokenFCM token: user.getTokens()){
                    userTokens.add(token.getToken());
                }

                Curso nextCursoDB = cursoService.findNextCurso(cursoDesbloqueadoDB.getCursoId());
                String titulo = "Siguiente Reto!!";
                String cuerpo = "¡Ahora puedes desbloquear: " + nextCursoDB.getTitle() + "!";
                String image = nextCursoDB.getImageUrl();
                NotificationMessage notificationMessage = new NotificationMessage(null,titulo,cuerpo,image,null);
                firebaseMessagingService.sendNotificationByTokens(notificationMessage, userTokens);
            }

            return new ResponseEntity<>(cursoDesbloqueadoDB, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
