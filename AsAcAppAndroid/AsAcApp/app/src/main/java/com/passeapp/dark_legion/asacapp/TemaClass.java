package com.passeapp.dark_legion.asacapp;

import java.util.ArrayList;

public class TemaClass {
    private Integer _id;
    private String nombre;
    private String description;
    private String formulas;
    private ArrayList<TestClass> lstTest = new ArrayList<TestClass>();

    public TemaClass(Integer _id, String nombre, String description, String formulas) {
        this._id = _id;
        this.nombre = nombre;
        this.description = description;
        this.formulas = formulas;
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

    public ArrayList<TestClass> getLstTest() {
        return lstTest;
    }

    public void setLstTest(ArrayList<TestClass> lstTest) {
        this.lstTest = lstTest;
    }

    public String getFormulas() {
        return formulas + "?op=0";
    }

    public void setFormulas(String formulas) {
        this.formulas = formulas;
    }
}
