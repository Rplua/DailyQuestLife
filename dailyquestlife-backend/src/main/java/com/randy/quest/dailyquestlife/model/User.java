package com.randy.quest.dailyquestlife.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private int id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "nivel")
    private int nivel;
    @Column(name = "img")
    private String img;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "followers")
    private int followrs;
    @Column(name = "following")
    private int following;
    @Column(name = "friends")
    private int friends;
    @ManyToMany
    @JoinTable(
            name = "userobjecto", // Tabla de unión
            joinColumns = @JoinColumn(name = "iduser"), // Columna de clave externa para User
            inverseJoinColumns = @JoinColumn(name = "idobjecto") // Columna de clave externa para Objecto
    )
    private Set<Objecto> objectos;

    @ManyToMany
    @JoinTable(
            name = "usermision", // Tabla de unión
            joinColumns = @JoinColumn(name = "iduser"), // Columna de clave externa para User
            inverseJoinColumns = @JoinColumn(name = "idmision") // Columna de clave externa para Mision
    )
    @JsonIgnore
    private Set<Mision> misiones;



    public User(String name){
        this.name = name;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getImg() {
        return img;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getFollowrs() {
        return followrs;
    }

    public void setFollowrs(int followrs) {
        this.followrs = followrs;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Set<Objecto> getObjectos() {
        return objectos;
    }

    public void setObjectos(Set<Objecto> objectos) {
        this.objectos = objectos;
    }

    public Set<Mision> getMisiones() {
        return misiones;
    }

    public void setMisiones(Set<Mision> misiones) {
        this.misiones = misiones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
