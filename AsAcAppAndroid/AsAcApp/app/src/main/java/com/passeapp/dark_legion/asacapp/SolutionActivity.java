package com.passeapp.dark_legion.asacapp;

import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


public class SolutionActivity extends AppCompatActivity {

    protected ImageButton nextExcludeQuestionBtn;
    protected ListView solutionList;
    private ImageView solutionImage;
    private ImageView solutionQuestionImage;
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
        this.nextExcludeQuestionBtn = (ImageButton)findViewById(R.id.nextExcludeQuestionBtn);
        this.nextExcludeQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VariablesActivity.scores.size()==5){
                    /*scoreDialog = new AlertDialog.Builder(SolutionActivity.this).create();
                    scoreDialog.setTitle("Tu score es: " + VariablesActivity.sumScore());
                    scoreDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scoreDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), TemaActivity.class));
                        }
                    });
                    scoreDialog.show();*/
                    finish();
                    startActivity(new Intent(getApplicationContext(), EndingActivity.class));
                }else{
                    String excludedId = VariablesActivity.actualQuestion.get_id().toString();
                    if(VariablesActivity.excludesQuestions != null && !VariablesActivity.excludesQuestions.isEmpty()){
                        VariablesActivity.excludesQuestions = VariablesActivity.excludesQuestions + "," + excludedId;
                    }else{
                        VariablesActivity.excludesQuestions = "?p=" + excludedId;
                    }
                    finish();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                }
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

    }

    @Override
    public void onBackPressed() {

    }
}
