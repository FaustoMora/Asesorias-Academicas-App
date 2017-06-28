package com.passeapp.dark_legion.asacapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SolutionActivity extends AppCompatActivity {

    protected Button nextExcludeQuestionBtn;
    protected ListView solutionList;
    private ImageView solutionImage;
    private ImageView solutionQuestionImage;
    private TextView solutionYoutube;
    public AlertDialog scoreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        init();
    }

    public void init(){
        this.solutionQuestionImage = (ImageView)findViewById(R.id.solutionQuestionImage);
        this.solutionImage = (ImageView)findViewById(R.id.solutionImage);
        this.solutionYoutube = (TextView)findViewById(R.id.youtube);
        this.nextExcludeQuestionBtn = (Button)findViewById(R.id.nextExcludeQuestionBtn);
        this.nextExcludeQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        this.solutionList = (ListView)findViewById(R.id.solutionList);
        this.solutionList.setAdapter(new ArrayAdapter<OptionClass>(this, android.R.layout.simple_list_item_1, VariablesActivity.actualQuestion.getOpciones()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                OptionClass op = getItem(position);
                if(op.getEs_correcta())
                {
                    // do something change color
                    row.setBackgroundColor (Color.GREEN); // some color
                }
                else
                {
                    if(position == QuestionActivity.selectedPosOption){
                        row.setBackgroundColor (Color.RED);
                    }else{
                        row.setBackgroundColor (Color.WHITE);
                    }
                }
                return row;
            }
        });
        initialiceImage();
    }

    protected void initialiceImage(){
        byte[] decodedString = Base64.decode(VariablesActivity.actualQuestion.getPregunta_imagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.solutionQuestionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));

        decodedString = Base64.decode(VariablesActivity.actualQuestion.getSolucion_imagen(), Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.solutionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));

        String linkYoutube = VariablesActivity.actualQuestion.getLink_youtube();

        if(linkYoutube == "null"){
            this.solutionYoutube.setText("Esta pregunta no contiene video solución.");
        }else{
            this.solutionYoutube.setText(linkYoutube);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
