package com.passeapp.dark_legion.asacapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class VariablesActivity extends AppCompatActivity {

    public static ArrayList<MateriaClass> lstMaterias = new ArrayList<MateriaClass>();
    public static ArrayList<TemaClass> lstTemas = new ArrayList<TemaClass>();
    public static ArrayList<TestClass> lstTests = new ArrayList<TestClass>();
    public static ArrayList<QuestionClass> lstQuestions = new ArrayList<QuestionClass>();
    public static MateriaClass actualMateria;
    public static TemaClass actualTema;
    public static TestClass actualTest;
    public static QuestionClass actualQuestion;
    public static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static Integer actualIndexMateria = null;
    public static Integer actualIndexTema = null;
    public static Integer actualIndexTest = null;
    public static Integer actualIndexPregunta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);
    }

    public static void reset_variables(){
        actualTema = null;
        actualQuestion = null;
    }

    public static void resetQuestionvariables(){
        actualQuestion = null;
        lstQuestions.clear();
        scores.clear();
    }

    public static void resetAllvariables(){
        actualMateria = null;
        actualTema = null;
        actualQuestion = null;
    }

    public static int sumScore(){
        int aux=0;
        for (int i: scores ) {
            aux += i;
        }
        return aux;
    }

    public static void reset_list(){
        lstMaterias.clear();
        lstTemas.clear();
        lstQuestions.clear();
    }

    public static String displayedScore(){
        return String.valueOf(sumScore()) + "/" + String.valueOf(scores.size());
    }

    public static Object getElementForArrayListById(ArrayList list, int pk){
        for (Object ob: list) {
            switch (ob.getClass().getName()){
                case "MateriaClass":
                    if (((MateriaClass)ob).getId()==pk) return ob ;
                case "TemaClass":
                    if (((TemaClass)ob).get_id()==pk) return ob ;
                case "TestClass":
                    if (((TestClass)ob).get_id()==pk) return ob ;
                case "QuestionClass":
                    if (((QuestionClass)ob).get_id()==pk) return ob ;
                default:
                    break;
            }
        }
        return null;
    }
}
