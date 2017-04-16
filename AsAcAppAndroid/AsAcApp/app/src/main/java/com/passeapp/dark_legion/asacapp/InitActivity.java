package com.passeapp.dark_legion.asacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {

    Button initBtn;
    ImageView icono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        init();
    }

    public void init(){
        this.icono = (ImageView)findViewById(R.id.iconoApp);
        this.initBtn = (Button)findViewById(R.id.startAppBtn);
        this.initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnlineConnect.isOnline(getApplicationContext())){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"No dispones de conexion a internet",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
