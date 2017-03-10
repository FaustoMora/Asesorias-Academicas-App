package com.passeapp.dark_legion.asacapp;

/**
 * Created by dark-legion on 3/10/17.
 */

public class QuestionClass {
    private Integer _id;
    private String detalle;
    private Integer tema_id;
    private String pregunta_imagen;
    private String solucion_imagen;

    public QuestionClass(Integer _id, String detalle, Integer tema_id, String pregunta_imagen, String solucion_imagen) {
        this._id = _id;
        this.detalle = detalle;
        this.tema_id = tema_id;
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

    public String getPregunta_imagen() {
        return pregunta_imagen;
    }

    public void setPregunta_imagen(String pregunta_imagen) {
        this.pregunta_imagen = pregunta_imagen;
    }

    public String getSolucion_imagen() {
        return solucion_imagen;
    }

    public void setSolucion_imagen(String solucion_imagen) {
        this.solucion_imagen = solucion_imagen;
    }
}
