package com.study.Usuarios.controller;

import com.study.Cursos.model.Curso;
import com.study.Cursos.service.CursoService;
import com.study.Usuarios.model.CursoDesbloqueado;
import com.study.Usuarios.service.CursoDesbloqueadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "usercursos")
public class UserCursoController {

    @Autowired
    private CursoDesbloqueadoService cursoDesbloqueadoService;

    @Autowired
    private CursoService cursoService;

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
            CursoDesbloqueado userCursoDB = cursoDesbloqueadoService.insert(cursoDesbloqueado);
            return new ResponseEntity<>(userCursoDB, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
