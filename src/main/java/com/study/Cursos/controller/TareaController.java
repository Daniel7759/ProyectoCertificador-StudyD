package com.study.Cursos.controller;

import com.study.Cursos.model.Tarea;
import com.study.Cursos.service.TareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTareas(){
        try {
            Collection<Tarea> tareasDB=tareaService.findAll();
            return new ResponseEntity<>(tareasDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{tareaId}")
    public ResponseEntity<?> getTareaById(@PathVariable Long tareaId){
        try{
            Tarea tareaDB= tareaService.findById(tareaId);
            return new ResponseEntity<>(tareaDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTarea(@RequestBody Tarea tarea){
        try{
            Tarea existingTarea=tareaService.findByTitulo(tarea.getTitulo());

            if (existingTarea!=null){
                return new ResponseEntity<>(existingTarea, HttpStatus.OK);
            }
            Tarea tareaDB=tareaService.insert(tarea);
            return new ResponseEntity<>(tareaDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
