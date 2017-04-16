package com.passeapp.dark_legion.asacapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class VariablesActivity extends AppCompatActivity {

    public static ArrayList<MateriaClass> lstMaterias = new ArrayList<MateriaClass>();
    public static ArrayList<TemaClass> lstTemas = new ArrayList<TemaClass>();
    public static MateriaClass actualMateria;
    public static TemaClass actualTema;
    public static QuestionClass actualQuestion;
    public static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static String excludesQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);
    }

    public static void reset_variables(){
        actualMateria = null;
        actualTema = null;
        actualQuestion = null;
        excludesQuestions =null;
    }

    public static int sumScore(){
        int aux=0;
        for (int i: scores ) {
            aux = aux + scores.get(i);
        }
        return aux;
    }

    public static void reset_list(){
        lstMaterias.clear();
        lstTemas.clear();
    }
}
