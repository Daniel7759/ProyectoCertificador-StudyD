package com.study.Cursos.controller;

import com.study.Cursos.model.Pregunta;
import com.study.Cursos.model.Subtema;
import com.study.Cursos.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "preguntas")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @GetMapping
    public ResponseEntity<?> getAllPreguntas(){
        try {
            Collection<Pregunta> preguntasDB=preguntaService.findAll();
            return new ResponseEntity<>(preguntasDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<?> getPreguntaById(@PathVariable Long preguntaId){
        try{
            Pregunta preguntaDB= preguntaService.findById(preguntaId);
            return new ResponseEntity<>(preguntaDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPregunta(@RequestBody Pregunta pregunta){
        try{
            Pregunta existingPregunta=preguntaService.findByPregunta(pregunta.getPregunta());

            if (existingPregunta!=null){
                return new ResponseEntity<>(existingPregunta, HttpStatus.OK);
            }
            Pregunta preguntaDB=preguntaService.insert(pregunta);
            return new ResponseEntity<>(preguntaDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
