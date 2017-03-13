package com.passeapp.dark_legion.asacapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionActivity extends AppCompatActivity {

    protected ImageButton continueTestBtn;
    protected ListView optionsList;
    public ArrayAdapter<OptionClass> adapterOptions;
    public static QuestionClass actualQuestion;
    public static String usedExcludesQuestions;
    public OptionClass exampleArray[] = {new OptionClass(1,"Opcion 1",true,1), new OptionClass(2,"Opcion 2",true,1), new OptionClass(3,"Opcion 3",true,1), new OptionClass(4,"Opcion 4",true,1)};
    public ArrayList<OptionClass> arrayList = new ArrayList<OptionClass>(Arrays.asList(exampleArray));
    public Dialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        init();
    }

    protected void init(){

        this.continueTestBtn = (ImageButton)findViewById(R.id.questionPlayBtn);
        this.continueTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = optionsList.getCheckedItemPosition();
                if(pos > -1){
                    OptionClass aux = arrayList.get(pos);
                    Toast.makeText(getApplicationContext(),"Seleccionaste: "+aux.toString(),Toast.LENGTH_SHORT).show();
                    buildCustomDialog();

                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.optionsList = (ListView)findViewById(R.id.optionsList);
        this.adapterOptions = new ArrayAdapter<OptionClass>(this,android.R.layout.simple_list_item_single_choice,this.arrayList);
        this.optionsList.setAdapter(this.adapterOptions);
    }


    public void buildCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.custom_next_dialog, null));

        customDialog = builder.create();

        Button showSolutionBtn = (Button)customDialog.findViewById(R.id.showSolutionBtn);
        showSolutionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.cancel();
                startActivity(new Intent(getApplicationContext(), SolutionActivity.class));
            }
        });
        Button nextQuestionBtn = (Button)customDialog.findViewById(R.id.nextQuestionBtn);
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.cancel();
                startActivity(new Intent(getApplicationContext(), QuestionClass.class));
            }
        });

        customDialog.show();

    }
}
