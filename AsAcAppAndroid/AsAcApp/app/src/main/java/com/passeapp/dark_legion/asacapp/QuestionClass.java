package com.passeapp.dark_legion.asacapp;

import android.graphics.Color;

import java.util.ArrayList;

public class QuestionClass {
    private Integer _id;
    private String detalle;
    private String youtube;
    private String pregunta_imagen;
    private String solucion_imagen;
    private String pdf;
    private ArrayList<OptionClass> opciones = new ArrayList<>();
    private int selectedOP = -1;
    private boolean hasSelected = false;
    private int selectedColorOption = Color.WHITE;
    private String actualIndex = "";

    public QuestionClass(Integer _id, String detalle, String link_youtube,String pregunta_imagen, String solucion_imagen,String pdf) {
        this._id = _id;
        this.detalle = detalle;
        this.youtube = link_youtube;
        this.pregunta_imagen = pregunta_imagen;
        this.solucion_imagen = solucion_imagen;
        this.pdf = pdf;
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


    public String getLink_youtube(){
        if(youtube != null && youtube != "null" && youtube != ""){
            return youtube;
        }else {
            return null;
        }
    }

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

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public int getSelectedOP() {
        return selectedOP;
    }

    public void setSelectedOP(int selectedOP) {
        this.selectedOP = selectedOP;
    }

    public boolean isHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }

    public int getSelectedColorOption() {
        return selectedColorOption;
    }

    public void setSelectedColorOption(int selectedColorOption) {
        this.selectedColorOption = selectedColorOption;
    }

    public void resetQuestionVariables(){
        this.setSelectedOP(-1);
        this.setHasSelected(false);
        this.setSelectedColorOption(Color.WHITE);
    }

    public String getActualIndex() {
        return actualIndex;
    }

    public void setActualIndex(String actualIndex) {
        this.actualIndex = actualIndex;
    }

    public String getPdf() {
        if(pdf != null && pdf != "null" && pdf != ""){
            return pdf;
        }else {
            return null;
        }
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
