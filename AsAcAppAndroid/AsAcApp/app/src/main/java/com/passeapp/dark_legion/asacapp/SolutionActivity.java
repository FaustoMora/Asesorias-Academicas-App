package com.passeapp.dark_legion.asacapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import uk.co.senab.photoview.PhotoViewAttacher;


public class SolutionActivity extends AppCompatActivity {

    protected Button nextExcludeQuestionBtn;
    protected ListView solutionList;
    private ImageView solutionImage;
    private ImageView solutionQuestionImage;
    private ImageView solutionYoutube;
    public AlertDialog scoreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        init();
    }

    public void init(){
        //this.solutionQuestionImage = (ImageView)findViewById(R.id.solutionQuestionImage);
        this.solutionImage = (ImageView)findViewById(R.id.solutionImage);
        this.solutionYoutube = (ImageView) findViewById(R.id.youtube);
        this.nextExcludeQuestionBtn = (Button)findViewById(R.id.nextExcludeQuestionBtn);
        this.nextExcludeQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        /*this.solutionList = (ListView)findViewById(R.id.solutionList);
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
        });*/
        initialiceImage();
        this.solutionYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkYoutube = VariablesActivity.actualQuestion.getLink_youtube();

                if(linkYoutube != "null"){
                    try{
                        Intent videoIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(linkYoutube));
                        startActivity(videoIntent);
                    }catch (Exception e){
                        Log.e("youtube error", e.getLocalizedMessage());
                        Toast.makeText(SolutionActivity.this,"NO TIENES INSTALADO YOUTUBE APP",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //PhotoViewAttacher photoView = new PhotoViewAttacher(solutionImage);
        //photoView.update();
    }

    protected void initialiceImage(){
        /*byte[] decodedString = Base64.decode(VariablesActivity.actualQuestion.getPregunta_imagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.solutionQuestionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));*/

        byte[] decodedString;
        Bitmap decodedByte;
        decodedString = Base64.decode(VariablesActivity.actualQuestion.getSolucion_imagen(), Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.solutionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));

        String linkYoutube = VariablesActivity.actualQuestion.getLink_youtube();

        if(linkYoutube != "null"){
            this.solutionYoutube.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
