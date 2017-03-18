package com.passeapp.dark_legion.asacapp;


public class OptionClass {
    private Integer _id;
    private String detalle;
    private Boolean es_correcta;
    private Integer pregunta_id;

    public OptionClass(Integer _id, String detalle, Boolean es_correcta) {
        this._id = _id;
        this.detalle = detalle;
        this.es_correcta = es_correcta;
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

    public Boolean getEs_correcta() {
        return es_correcta;
    }

    public void setEs_correcta(Boolean es_correcta) {
        this.es_correcta = es_correcta;
    }

    public Integer getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(Integer pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    @Override
    public String toString() {
        return this.getDetalle();
    }
}
