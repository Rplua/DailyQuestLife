package com.randy.quest.dailyquestlife.controller;


import com.randy.quest.dailyquestlife.model.Mision;
import com.randy.quest.dailyquestlife.model.Objecto;
import com.randy.quest.dailyquestlife.model.User;
import com.randy.quest.dailyquestlife.repository.ObjectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/objecto")
public class ObjectoController {
    @Autowired
    ObjectoRepository objectoRepository;
    @CrossOrigin
    @GetMapping("/hola")
    public List<Objecto> getObjectos() {
        try {
            return objectoRepository.findAll();

        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Objecto> getObjectoById(@PathVariable int id) {
        try{
            Objecto obj  = objectoRepository.findById(id).orElse(null);
            if(obj != null) {

                return new ResponseEntity<>(obj, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @CrossOrigin
    @GetMapping("/{id}/user")
    public Set<User> getObjectoUser(@PathVariable int id) {
        try {
           Objecto cat = objectoRepository.findById(id).orElse(null);
            if (cat != null) {
                return cat.getUsers();
            }else {
                return null;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @PostMapping("")
    public Objecto add(@RequestBody Objecto obj) {
        try {
            if(obj.getId() == 0 && obj.getTipo() != null) {
                return objectoRepository.save(obj);
            }else {
                return null;
            }
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public Objecto update(@RequestBody Objecto obj,@PathVariable int id) {
        try {
            if(obj.getId() == id) {
                return objectoRepository.save(obj);
            }else {
                return null;
            }
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        try {
            objectoRepository.deleteById(id);
            return id;
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
