package com.randy.quest.dailyquestlife.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "objecto")
public class Objecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idobjecto")
    private int id;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "img")
    private String img;
    @Column(name = "objectodes")
    private String objectodes;
    @ManyToMany(mappedBy = "objectos")
    @JsonIgnore
    private Set<User> users;
    //Contructor vacio
    public Objecto(){

    }
    public Objecto(String tipo){
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getObjectodes() {
        return objectodes;
    }

    public void setObjectodes(String objectodes) {
        this.objectodes = objectodes;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Objecto{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
