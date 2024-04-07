package com.study.Cursos.controller;

import com.study.Cursos.model.Subtema;
import com.study.Cursos.service.SubtemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "subtemas")
public class SubtemaController {

    private final SubtemaService subtemaService;

    public SubtemaController(SubtemaService subtemaService) {
        this.subtemaService = subtemaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSubtemas(){
        try {
            Collection<Subtema> subtemasDB=subtemaService.findAll();
            return new ResponseEntity<>(subtemasDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{subtemaId}")
    public ResponseEntity<?> getSubtemaById(@PathVariable Long subtemaId){
        try{
            Subtema subtemaDB= subtemaService.findById(subtemaId);
            return new ResponseEntity<>(subtemaDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSubema(@RequestBody Subtema subtema){
        try{
            Subtema existingSubtema=subtemaService.findByTitulo(subtema.getTitulo());

            if (existingSubtema!=null){
                return new ResponseEntity<>(existingSubtema, HttpStatus.OK);
            }
            Subtema subtemaDB=subtemaService.insert(subtema);
            return new ResponseEntity<>(subtemaDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
