package com.study.Cursos.controller;

import com.study.Cursos.model.Curso;
import com.study.Cursos.service.CursoService;
import com.study.Cursos.service.MateriaService;
import com.study.firebase.FirebaseMessagingService;
import com.study.firebase.NotificationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "cursos")
public class CursoController {

    private final CursoService cursoService;

    private final MateriaService materiaService;

    private final FirebaseMessagingService firebaseMessagingService;

    public CursoController(CursoService cursoService, MateriaService materiaService, FirebaseMessagingService firebaseMessagingService) {
        this.cursoService = cursoService;
        this.materiaService = materiaService;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCursos(@RequestParam(required = false) String tag){
        try {
            Collection<Curso> cursosDB = cursoService.findAll();

            // Filtrar cursos por tag si se proporciona el parámetro tag
            if (tag != null && !tag.isEmpty()) {
                Collection<Curso> cursosFiltrados = cursosDB.stream()
                        .filter(curso -> curso.getTag().name().equalsIgnoreCase(tag))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(cursosFiltrados, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(cursosDB, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<?> getCursoById(@PathVariable Long cursoId){
        try{
            Curso cursoDB= cursoService.findById(cursoId);
            return new ResponseEntity<>(cursoDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCurso(@RequestBody Curso curso){
        try{
            Curso existingCurso=cursoService.findByTitle(curso.getTitle());

            if (existingCurso!=null){
                return new ResponseEntity<>(existingCurso, HttpStatus.OK);
            }
            Curso cursoDB=cursoService.insert(curso);

            //mandamos la notificacion a la app
            String titulo = "Nuevo Curso, miralo en Novedades";
            String cuerpo = "¡Nuevo curso disponible: " + cursoDB.getTitle() + "!";
            String image = "https://i.pinimg.com/236x/ac/15/a6/ac15a619b4ff84341dab0c1b93b6556b.jpg";
            NotificationMessage notificationMessage = new NotificationMessage(titulo,cuerpo,image);
            firebaseMessagingService.sendNotifyByTopic(notificationMessage);

            return new ResponseEntity<>(cursoDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/recientes")
    public ResponseEntity<?> getAllCursosByFechaCreacion() {
        try {
            Collection<Curso> cursosDB = cursoService.findAllByFechaCreacionDesc();
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<?> getCursosByMateriaId(@PathVariable Long materiaId) {
        try {
            Collection<Curso> cursosDB = cursoService.findAllByMateriaId(materiaId);
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/opcionales")
    public ResponseEntity<?> getCursosByMateriaOpcional(){
        try{
            Collection<Curso> cursosDB = materiaService.obtenerCursosDeMateriasOpcionales();
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
