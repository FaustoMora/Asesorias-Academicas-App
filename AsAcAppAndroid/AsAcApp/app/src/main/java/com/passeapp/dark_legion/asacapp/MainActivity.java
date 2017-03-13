package com.passeapp.dark_legion.asacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected ImageButton startTestBtn;
    protected ListView listTemas;
    public ArrayAdapter<TemaClass> adapterTemas;

    public TemaClass exampleArray[] = {new TemaClass(1,"Tema 1","Tema 1"), new TemaClass(2,"Tema DOS","Tema DOS"), new TemaClass(2,"Tema Tres","Tema Tres")};
    public ArrayList<TemaClass> arrayList = new ArrayList<TemaClass>(Arrays.asList(exampleArray));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    protected void init(){

        this.startTestBtn = (ImageButton)findViewById(R.id.startTestBtn);
        this.startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posTema = listTemas.getCheckedItemPosition();
                if(posTema > -1){
                    TemaClass auxTema = arrayList.get(posTema);
                    Toast.makeText(getApplicationContext(),"Seleccionaste: "+auxTema.toString(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.listTemas = (ListView)findViewById(R.id.listTemas);
        this.adapterTemas = new ArrayAdapter<TemaClass>(this,android.R.layout.simple_list_item_single_choice,this.arrayList);
        this.listTemas.setAdapter(this.adapterTemas);
    }
}
