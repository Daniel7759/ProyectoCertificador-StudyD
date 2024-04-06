package com.study.Cursos.controller;

import com.study.Cursos.model.Logro;
import com.study.Cursos.model.Tema;
import com.study.Cursos.service.LogroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "logros")
public class LogroController {

    @Autowired
    private LogroService logroService;

    @GetMapping
    public ResponseEntity<?> getAllLogros(){
        try {
            Collection<Logro> logrosDB=logroService.findAll();
            return new ResponseEntity<>(logrosDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{logroId}")
    public ResponseEntity<?> getTemaById(@PathVariable Long logroId){
        try{
            Logro logroDB= logroService.findById(logroId);
            return new ResponseEntity<>(logroDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTema(@RequestBody Logro logro){
        try{
            Logro existingLogro=logroService.findByNombreLogro(logro.getNombreLogro());

            if (existingLogro!=null){
                return new ResponseEntity<>(existingLogro, HttpStatus.OK);
            }
            Logro logroDB=logroService.insert(logro);
            return new ResponseEntity<>(logroDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
