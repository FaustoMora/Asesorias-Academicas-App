package com.passeapp.dark_legion.asacapp;

import java.util.ArrayList;

public class QuestionClass {
    private Integer _id;
    private String detalle;
    private Integer tema_id;
    private String youtube;
    private String pregunta_imagen;
    private String solucion_imagen;
    private ArrayList<OptionClass> opciones;

    public QuestionClass(Integer _id, String detalle, Integer tema_id, String link_youtube,String pregunta_imagen, String solucion_imagen) {
        this._id = _id;
        this.detalle = detalle;
        this.tema_id = tema_id;
        this.youtube = link_youtube;
        this.pregunta_imagen = pregunta_imagen;
        this.solucion_imagen = solucion_imagen;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getTema_id() {
        return tema_id;
    }

    public void setTema_id(Integer tema_id) {
        this.tema_id = tema_id;
    }

    public String getLink_youtube(){ return youtube; }

    public void setLink_youtube(String link_youtube){ this.youtube = link_youtube; }

    public String getPregunta_imagen() {
        return pregunta_imagen.replace("data:image/png;base64,","");
    }

    public void setPregunta_imagen(String pregunta_imagen) {
        this.pregunta_imagen = pregunta_imagen;
    }

    public String getSolucion_imagen() {
        return solucion_imagen.replace("data:image/png;base64,","");
    }

    public void setSolucion_imagen(String solucion_imagen) {
        this.solucion_imagen = solucion_imagen;
    }

    public ArrayList<OptionClass> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<OptionClass> opciones) {
        this.opciones = opciones;
    }
}
