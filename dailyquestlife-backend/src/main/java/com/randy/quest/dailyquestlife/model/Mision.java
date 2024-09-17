package com.randy.quest.dailyquestlife.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "mision")
public class Mision {
    //valores
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmision") // Coincide con la columna 'idmision' en la base de datos
    private int id;

    @Column(name = "nombremision") // Coincide con la columna 'nombremision' en la base de datos
    private String nombre;

    @Column(name = "categoriamision") // Coincide con la columna 'categoriaMision' en la base de datos
    private String categoriaMision;

    @Column(name = "tipo") // Coincide con la columna 'tipo' en la base de datos
    private String tipo;
    @ManyToMany(mappedBy = "misiones")
    private Set<User> users;
    // Constructor vacío
    public Mision() {
    }

    // Constructor con parámetros
    public Mision(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoriaMision() {
        return categoriaMision;
    }

    public void setCategoriaMision(String categoriaMision) {
        this.categoriaMision = categoriaMision;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Mision{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoriaMision='" + categoriaMision + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
