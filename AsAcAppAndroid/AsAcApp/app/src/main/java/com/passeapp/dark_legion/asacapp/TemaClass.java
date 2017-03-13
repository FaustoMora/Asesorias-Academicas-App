package com.passeapp.dark_legion.asacapp;

public class TemaClass {
    private Integer _id;
    private String nombre;
    private String description;

    public TemaClass(Integer _id, String nombre, String description) {
        this._id = _id;
        this.nombre = nombre;
        this.description = description;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
