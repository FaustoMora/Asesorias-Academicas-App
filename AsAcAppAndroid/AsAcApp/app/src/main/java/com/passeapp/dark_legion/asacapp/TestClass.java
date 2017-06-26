package com.passeapp.dark_legion.asacapp;

import java.util.ArrayList;


public class TestClass {

    private Integer _id;
    private String nombre;
    private ArrayList<QuestionClass> lstPreguntas = new ArrayList<QuestionClass>();

    public TestClass(Integer _id, String nombre) {
        this._id = _id;
        this.nombre = nombre;
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

    public ArrayList<QuestionClass> getLstPreguntas() {
        return lstPreguntas;
    }

    public void setLstPreguntas(ArrayList<QuestionClass> lstPreguntas) {
        this.lstPreguntas = lstPreguntas;
    }
}
