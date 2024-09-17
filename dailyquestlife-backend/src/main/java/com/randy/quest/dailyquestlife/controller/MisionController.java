package com.randy.quest.dailyquestlife.controller;


import com.randy.quest.dailyquestlife.model.Mision;
import com.randy.quest.dailyquestlife.model.User;
import com.randy.quest.dailyquestlife.repository.MisionRepository;
import org.hibernate.query.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.Set;


//Siempre que no sean vistas. RestApi
@RestController
@RequestMapping("/mision")
public class MisionController {
    private static final Logger log = LoggerFactory.getLogger(MisionController.class);
    @Autowired
    private MisionRepository misionRepository;
    @CrossOrigin
    @GetMapping("/hola")
    public List<Mision> getMision() {
        try {
            return misionRepository.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Mision> getMisionById(@PathVariable int id) {
        System.out.println(id);
        try{
            Mision cat = misionRepository.findById(id).orElse(null);
            if (cat == null) {
                return new ResponseEntity<>(cat,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(cat,HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }
    }
    @CrossOrigin
    @GetMapping("/{id}/user")
    public Set<User> getMisionUser(@PathVariable int id) {
        try {
            Mision cat = misionRepository.findById(id).orElse(null);
            if (cat != null) {
                return cat.getUsers();
            }else {
                return null;
            }
        }catch (Exception ex){

            log.error(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @PostMapping("")
    public Mision createMision(@RequestBody Mision mision) {
        System.out.println(mision);
        try {
            if (mision.getId() == 0 && mision.getNombre() != null) {
                return misionRepository.save(mision);

            }else {
                return null;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public Mision put(@RequestBody Mision mision, @PathVariable int id) {
        System.out.println(mision);
        System.out.println(id);
        try {
            if (mision.getId() == id ){
                return misionRepository.save(mision);

            }else {
                return null;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        try{
            System.out.println(id);
            misionRepository.deleteById(id);
            return id;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return 0 ;
        }
    }

    @CrossOrigin
    @GetMapping("/random")
    public List<Mision> getRandomMision() {
       Long qty = misionRepository.count();

       if (qty <= 5) {
           return misionRepository.findAll();
       }
       int idx = (int) (Math.random() * qty / 5 );
       Pageable pageable = PageRequest.of(idx,5);
       return misionRepository.findAll(pageable).getContent();
    }

}

