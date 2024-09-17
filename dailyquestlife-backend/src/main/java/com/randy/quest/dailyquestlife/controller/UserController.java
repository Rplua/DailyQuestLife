package com.randy.quest.dailyquestlife.controller;


import com.randy.quest.dailyquestlife.model.Mision;
import com.randy.quest.dailyquestlife.model.Objecto;
import com.randy.quest.dailyquestlife.model.User;
import com.randy.quest.dailyquestlife.repository.ObjectoRepository;
import com.randy.quest.dailyquestlife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectoRepository objectoRepository;
    @CrossOrigin
    @GetMapping("")
    public List<User> getUser() {
        try {
            return userRepository.findAll();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Obtener un usuario por ID
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {


        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @CrossOrigin
    @GetMapping("/{id}/misiones")
    public Set<Mision> getUserMisiones(@PathVariable int id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                return user.getMisiones();
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/objectos")
    public Set<Objecto> getUserObjectos(@PathVariable int id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                return user.getObjectos();
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/available-objectos")
    public List<Objecto> getAvailableObjectos(@PathVariable int id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {

                List<Objecto> allObjectos = objectoRepository.findAll();


                Set<Objecto> userObjectos = user.getObjectos();


                allObjectos.removeAll(userObjectos);

                return allObjectos;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }


    // Agregar un nuevo usuario
    @CrossOrigin
    @PostMapping("")
    public User add(@RequestBody User user) {
        System.out.println(user);
        try {
            if (user.getId() == 0 && user.getName() != null) {
              return userRepository.save(user);
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public User put(@RequestBody User cat, @PathVariable int id) {
        try {
            if (cat.getId() == id) {
                return userRepository.save(cat);
            } else {
                return null;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        try {
            System.out.println(id);
            userRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        try {
            User user = userRepository.findByUsuario(loginUser.getUsuario());
            if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"error\":\"Invalid name or password\"}", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("{\"error\":\"An unexpected error occurred\"}", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @CrossOrigin
    @GetMapping("/exclude/{id}")
    public ResponseEntity<List<User>> getUsersExcluding(@PathVariable int id) {
        try {
            List<User> allUsers = userRepository.findAll();
            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getId() != id)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }



}

