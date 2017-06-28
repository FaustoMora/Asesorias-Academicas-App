package com.passeapp.dark_legion.asacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EndingActivity extends AppCompatActivity {

    private TextView lblScore;
    private Button restartBtn;
    private Button repeatTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        init();
    }

    public void init(){
        lblScore = (TextView)findViewById(R.id.lblScore);
        lblScore.setText(VariablesActivity.displayedScore());
        restartBtn = (Button)findViewById(R.id.repeatBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
            }
        });
        repeatTestBtn = (Button)findViewById(R.id.repeatTestBtn);
        repeatTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TestActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
