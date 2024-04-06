package com.study.Cursos.controller;

import com.study.Cursos.model.Examen;
import com.study.Cursos.model.Logro;
import com.study.Cursos.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "examenes")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @GetMapping
    public ResponseEntity<?> getAllExamenes(){
        try {
            Collection<Examen> examenesDB=examenService.findAll();
            return new ResponseEntity<>(examenesDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{examenId}")
    public ResponseEntity<?> getExamenById(@PathVariable Long examenId){
        try{
            Examen examenDB= examenService.findById(examenId);
            return new ResponseEntity<>(examenDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createExamen(@RequestBody Examen examen){
        try{
            Examen existingExamen=examenService.findByTitulo(examen.getTitulo());

            if (existingExamen!=null){
                return new ResponseEntity<>(existingExamen, HttpStatus.OK);
            }
            Examen examenDB=examenService.insert(examen);
            return new ResponseEntity<>(examenDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
