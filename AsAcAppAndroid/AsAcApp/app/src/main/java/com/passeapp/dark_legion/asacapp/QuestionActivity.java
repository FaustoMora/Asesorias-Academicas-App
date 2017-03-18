package com.passeapp.dark_legion.asacapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionActivity extends AppCompatActivity {

    protected ImageButton continueTestBtn;
    protected ListView optionsList;
    public ArrayAdapter<OptionClass> adapterOptions;
    public static TemaClass actualTema;
    public static QuestionClass actualQuestion;
    public static String excludesQuestions;
    public static OptionClass exampleArray[] = {new OptionClass(1,"Opcion 1",true,1), new OptionClass(2,"Opcion 2",false,1), new OptionClass(3,"Opcion 3",false,1), new OptionClass(4,"Opcion 4",false,1)};
    public static ArrayList<OptionClass> arrayList = new ArrayList<OptionClass>(Arrays.asList(exampleArray));
    public Dialog customDialog;
    private ImageView questionImage;
    public static int selectedPosOption;
    public boolean hasSelectedOption = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        init();
    }

    protected void init(){
        this.questionImage = (ImageView)findViewById(R.id.questionImage);
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
        this.adapterOptions = new ArrayAdapter<OptionClass>(this,android.R.layout.simple_list_item_single_choice,QuestionActivity.arrayList){
            @Override
            public boolean isEnabled(int position) {
                return !hasSelectedOption;
            }
        };

        this.optionsList.setAdapter(this.adapterOptions);
        this.optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OptionClass op = (OptionClass) adapterView.getItemAtPosition(i);
                hasSelectedOption = true;
                if (op.getEs_correcta()){
                    view.setBackgroundColor(Color.GREEN);
                }else{
                    view.setBackgroundColor(Color.RED);
                    selectedPosOption = i;
                }
            }
        });
        initialiceImage();
    }

    protected void initialiceImage(){

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
                startActivity(new Intent(getApplicationContext(), SolutionActivity.class));
                finish();
            }
        });
        Button nextQuestionBtn = (Button)promptView.findViewById(R.id.nextQuestionBtn);
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String excludedId = QuestionActivity.actualQuestion.get_id().toString();
                if(QuestionActivity.excludesQuestions != null && !QuestionActivity.excludesQuestions.isEmpty()){
                    QuestionActivity.excludesQuestions = QuestionActivity.excludesQuestions + "," + excludedId;
                }else{
                    QuestionActivity.excludesQuestions = "?p=" + excludedId;
                }
                customDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), QuestionClass.class));
                finish();
            }
        });

        customDialog.setTitle("");
        customDialog.show();

    }

    private class HttpGET_QuestionTask extends AsyncTask<String,Integer,QuestionClass> {
        String SERVER = "http://174.138.80.160/";
        String RESOURCE = "temas/"+QuestionActivity.actualTema.get_id()+"pregunta/"+QuestionActivity.excludesQuestions;

        @Override
        protected QuestionClass doInBackground(String... strings) {
            return getPregunta();
        }

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
                    OptionClass auxTema = new OptionClass(opJSON.getInt("id"),opJSON.getString("detalle"),opJSON.getBoolean("es_correcta"));
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
