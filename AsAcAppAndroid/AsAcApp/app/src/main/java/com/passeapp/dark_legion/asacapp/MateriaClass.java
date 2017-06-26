package com.passeapp.dark_legion.asacapp;


import java.util.ArrayList;

public class MateriaClass {

    private Integer id;
    private  String nombre;
    private String icono;
    private ArrayList<TemaClass> lstTemas = new ArrayList<TemaClass>();

    public MateriaClass(Integer id, String nombre, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public ArrayList<TemaClass> getLstTemas() {
        return lstTemas;
    }

    public void setLstTemas(ArrayList<TemaClass> lstTemas) {
        this.lstTemas = lstTemas;
    }
}
