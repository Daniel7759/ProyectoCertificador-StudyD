package com.study.Cursos.controller;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Tema;
import com.study.Cursos.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "temas")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @GetMapping
    public ResponseEntity<?> getAllTemas(){
        try {
            Collection<Tema> temasDB=temaService.findAll();
            return new ResponseEntity<>(temasDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{temaId}")
    public ResponseEntity<?> getTemaById(@PathVariable Long temaId){
        try{
            Tema temaDB= temaService.findById(temaId);
            return new ResponseEntity<>(temaDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTema(@RequestBody Tema tema){
        try{
            Tema existingTema=temaService.findByTitle(tema.getTitle());

            if (existingTema!=null){
                return new ResponseEntity<>(existingTema, HttpStatus.OK);
            }
            Tema temaDB=temaService.insert(tema);
            return new ResponseEntity<>(temaDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
