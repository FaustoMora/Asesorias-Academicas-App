package com.passeapp.dark_legion.asacapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class QuestionActivity extends AppCompatActivity {

    protected ImageButton continueTestBtn;
    protected ListView optionsList;
    public ArrayAdapter<OptionClass> adapterOptions;
    public static TemaClass actualTema;
    public static QuestionClass actualQuestion;
    public static String excludesQuestions;
    public AlertDialog scoreDialog;
    private ImageView questionImage;
    public static int selectedPosOption;
    public static int selectedColorOption;
    public boolean hasSelectedOption = false;
    public ProgressDialog progressDialog;
    public Dialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        //recreate();
        try{
            new HttpGET_QuestionTask().execute();
        }catch (Exception e){
            Log.e("error api",e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(),"CONEXION A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
        }
    }

    protected void reset_variables(){
        selectedPosOption = -1;
        selectedColorOption = -1;
    }

    protected void init(){
        //final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        this.hasSelectedOption = false;
        this.questionImage = (ImageView)findViewById(R.id.questionImage);
        this.continueTestBtn = (ImageButton)findViewById(R.id.questionPlayBtn);
        this.continueTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = optionsList.getCheckedItemPosition();
                if(pos > -1){
                    OptionClass aux = QuestionActivity.actualQuestion.getOpciones().get(pos);
                    MainActivity.scores.add(1);
                    Toast.makeText(getApplicationContext(),"Seleccionaste: "+aux.toString(),Toast.LENGTH_SHORT).show();
                    buildCustomDialog();
                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.optionsList = (ListView)findViewById(R.id.optionsList);
        this.adapterOptions = new ArrayAdapter<OptionClass>(this,android.R.layout.simple_list_item_single_choice,QuestionActivity.actualQuestion.getOpciones()){
            @Override
            public boolean isEnabled(int position) {
                return !hasSelectedOption;
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                if (position != selectedPosOption) {
                    view.setBackgroundColor(Color.WHITE);
                } else {
                    view.setBackgroundColor(selectedColorOption);
                }
                return view;
            }
        };
        this.optionsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.optionsList.setAdapter(this.adapterOptions);
        this.optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OptionClass op = (OptionClass) adapterView.getItemAtPosition(i);

                if(!hasSelectedOption){
                    optionsList.setItemChecked(i, true);
                    view.setSelected(true);
                    selectedPosOption = i;
                    if(view.isSelected() ){
                        if (op.getEs_correcta()){
                            selectedColorOption = Color.GREEN;
                        }else{
                            selectedColorOption = Color.RED;
                        }
                    }
                    hasSelectedOption = true;
                    adapterOptions.notifyDataSetChanged();
                }

            }
        });
        initialiceImage();
    }

    protected void initialiceImage(){
        byte[] decodedString = Base64.decode(QuestionActivity.actualQuestion.getPregunta_imagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.questionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));
    }


    public void buildCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View promptView = inflater.inflate(R.layout.custom_next_dialog, null);


        builder.setView(promptView);

        customDialog = builder.create();

        Button showSolutionBtn = (Button)promptView.findViewById(R.id.showSolutionBtn);
        showSolutionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), SolutionActivity.class));
//                finish();
            }
        });
        Button nextQuestionBtn = (Button)promptView.findViewById(R.id.nextQuestionBtn);
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.scores.size()==5){
                    scoreDialog = new AlertDialog.Builder(QuestionActivity.this).create();
                    scoreDialog.setTitle("Tu score es: " + MainActivity.sumScore());
                    scoreDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scoreDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                    customDialog.dismiss();
                    scoreDialog.show();
                }else{
                    String excludedId = QuestionActivity.actualQuestion.get_id().toString();
                    if(QuestionActivity.excludesQuestions != null && !QuestionActivity.excludesQuestions.isEmpty()){
                        QuestionActivity.excludesQuestions = QuestionActivity.excludesQuestions + "," + excludedId;
                    }else{
                        QuestionActivity.excludesQuestions = "?p=" + excludedId;
                    }
                    customDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                }
            }
        });

        customDialog.setTitle("");
        customDialog.show();

    }

    public ProgressDialog createDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return  progressDialog;
    }

    private class HttpGET_QuestionTask extends AsyncTask<String,Integer,QuestionClass> {
        String SERVER = "http://174.138.80.160/";
        String RESOURCE = "temas/"+QuestionActivity.actualTema.get_id()+"/pregunta/";

        public HttpGET_QuestionTask() {
            if(QuestionActivity.excludesQuestions != null){
                this.RESOURCE = this.RESOURCE + QuestionActivity.excludesQuestions;
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = createDialog();
        }

        @Override
        protected void onPostExecute(QuestionClass questionClass) {
            actualQuestion = questionClass;
            init();
            reset_variables();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected QuestionClass doInBackground(String... strings) {
            publishProgress(0);
            return getPregunta();
        }

        @Nullable
        private QuestionClass getPregunta() {

            ArrayList<OptionClass> opList = new ArrayList<OptionClass>();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(SERVER + RESOURCE);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = Util.getASCIIContentFromEntity(entity);

                JSONObject jsonObject = new JSONObject(text);
                QuestionClass auxQuestion = new QuestionClass(jsonObject.getInt("id"),jsonObject.getString("detalle"),jsonObject.getInt("tema_id"),
                        jsonObject.getJSONObject("pregunta_imagen").getString("bitmap"),jsonObject.getJSONObject("solucion_imagen").getString("bitmap"));
                JSONArray opcionesJSON = jsonObject.getJSONArray("respuestas");
                for(int i=0;i<opcionesJSON.length();i++){
                    JSONObject opJSON = opcionesJSON.getJSONObject(i);
                    OptionClass auxTema = new OptionClass(opJSON.getInt("id"),opJSON.getString("detalle"),opJSON.getInt("es_correcta"));
                    opList.add(auxTema);
                }
                auxQuestion.setOpciones(opList);
                return auxQuestion;

            } catch (JSONException e) {
                Log.e("JSONException",e.getLocalizedMessage());
                e.printStackTrace();
                System.out.println("JSONException:"+e.getLocalizedMessage());
            } catch (Exception e) {
                Log.i("Exception:",""+e.getMessage());
                e.printStackTrace();
                System.out.println("Exception::"+e.getLocalizedMessage());
            }
            return null;
        }
    }
}
