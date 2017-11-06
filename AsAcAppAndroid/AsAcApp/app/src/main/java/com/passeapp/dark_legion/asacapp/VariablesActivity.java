package com.passeapp.dark_legion.asacapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class VariablesActivity extends AppCompatActivity {

    public static ArrayList<MateriaClass> lstMaterias = new ArrayList<MateriaClass>();
    public static ArrayList<TemaClass> lstTemas = new ArrayList<TemaClass>();
    public static ArrayList<TestClass> lstTests = new ArrayList<TestClass>();
    public static MateriaClass actualMateria;
    public static TemaClass actualTema;
    public static TestClass actualTest;
    public static QuestionClass actualQuestion;
    public static Integer actualIndexMateria = null;
    public static Integer actualIndexTema = null;
    public static Integer actualIndexTest = null;
    public static Integer actualIndexPregunta = null;
    public static Integer pastIndexPregunta = null;

    public static final String SERVER = "http://174.138.80.160/";
    public static final String TUTORS_URL = "https://teachersaidmath.wixsite.com/teachersaid/tutores";

    // DEV
    //public static final String APP_ID = "ca-app-pub-3940256099942544~3347511713";
    // PROD
    public static final String APP_ID = "ca-app-pub-5442937502585048~9781052374";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);
    }

    public static QuestionClass getQuestionForIndexList(int index){
        return lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().get(index);
    }

    public static int getQuestionListSize(){
        return lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().size();
    }

    public static void reset_variables(){
        actualTema = null;
        actualQuestion = null;
    }

    public static void resetQuestionvariables(){
        actualQuestion = null;
        //lstQuestions.clear();
    }

    public static void resetAllvariables(){
        actualMateria = null;
        actualTema = null;
        actualQuestion = null;
    }

    public static void reset_list(){
        lstMaterias.clear();
        lstTemas.clear();
        //lstQuestions.clear();
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
