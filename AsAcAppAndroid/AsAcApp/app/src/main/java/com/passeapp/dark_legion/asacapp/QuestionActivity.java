package com.passeapp.dark_legion.asacapp;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
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
    protected ExpandableHeightListView optionsList;
    public ArrayAdapter<OptionClass> adapterOptions;
    public AlertDialog scoreDialog;
    private ImageView questionImage;
    public static int selectedPosOption;
    public static int selectedColorOption;
    public boolean hasSelectedOption = false;
    public ProgressDialog progressDialog;
    public Dialog customDialog;
    private String density;
    private TextView lblTema;
    private TextView lblIndex;
    private TextView lblTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        density = String.valueOf(getResources().getDisplayMetrics().density);

        //recreate();
        try{
            new HttpGET_QuestionTask().execute();
        }catch (Exception e){
            Log.e("error api",e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(),"CONEXIÓN A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), TestActivity.class));
        }
    }

    /*
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    protected void reset_variables(){
        selectedPosOption = -1;
        selectedColorOption = -1;
    }

    protected void init(){

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if(getIntent().hasExtra("nextIndex")){
                int nextIndex =  extras.getInt("nextIndex");
                if(nextIndex > 0){
                    VariablesActivity.pastIndexPregunta = VariablesActivity.actualIndexPregunta;
                    VariablesActivity.actualIndexPregunta = nextIndex + VariablesActivity.actualIndexPregunta;
                }else{
                    VariablesActivity.pastIndexPregunta = VariablesActivity.pastIndexPregunta + nextIndex;
                    VariablesActivity.actualIndexPregunta = nextIndex + VariablesActivity.actualIndexPregunta;
                }
            }
        }else{
            VariablesActivity.actualIndexPregunta = 0;
            VariablesActivity.pastIndexPregunta = 0;
        }
        VariablesActivity.actualQuestion = VariablesActivity.getQuestionForIndexList(VariablesActivity.actualIndexPregunta);
        VariablesActivity.actualQuestion.setActualIndex(getIndexText());
        selectedPosOption = VariablesActivity.actualQuestion.getSelectedOP();
        hasSelectedOption = VariablesActivity.actualQuestion.isHasSelected();
        selectedColorOption = VariablesActivity.actualQuestion.getSelectedColorOption();

        this.lblTema = (TextView)findViewById(R.id.lblTema);
        this.lblTest = (TextView)findViewById(R.id.lblTest);
        this.lblIndex = (TextView)findViewById(R.id.lblIndexPregunta);

        this.lblTema.setText(VariablesActivity.actualTema.getNombre());
        this.lblTest.setText(VariablesActivity.actualTest.getNombre());
        this.lblIndex.setText(getIndexText());

        //final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        this.questionImage = (ImageView)findViewById(R.id.questionImage);
        this.continueTestBtn = (ImageButton)findViewById(R.id.questionContinueBtn);
        this.continueTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VariablesActivity.actualQuestion.getSelectedOP() >= 0 && (optionsList.getCheckedItemPosition() < 0)){
                    //buildCustomDialog();
                    redirectNextQuestion();
                }else{
                    int pos = optionsList.getCheckedItemPosition();
                    if(pos > -1){
                        OptionClass aux = VariablesActivity.actualQuestion.getOpciones().get(pos);
                        if(aux.getEs_correcta()){
                            VariablesActivity.actualTest.setScore(1);
                        }
                        //buildCustomDialog();
                        redirectNextQuestion();
                    }else{
                        Toast.makeText(getApplicationContext(),"Seleccione una respuesta para continuar",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.optionsList = (ExpandableHeightListView)findViewById(R.id.optionsList);
        this.adapterOptions = new ArrayAdapter<OptionClass>(this,R.layout.custom_row,VariablesActivity.actualQuestion.getOpciones()){

            @NonNull
            @Override
            public View getView(int position, View view, ViewGroup parent) {

                OptionClass op = getItem(position);

                if(view == null){
                    view = getLayoutInflater().inflate(R.layout.custom_row,parent,false);
                }

                //View view = super.getView(position, convertView, parent);

                MyTextView textView = (MyTextView)view.findViewById(R.id.lblText);
                ImageView imageView = (ImageView)view.findViewById(R.id.iconFolder);

                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Mermaid1001.ttf");
                textView.setTypeface(tf);
                textView.setText(op.getDetalle());
                //textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                //textView.setPadding(10,15,10,10);

                imageView.setImageResource(R.drawable.indicador_de_respuesta);


                /*if (view instanceof TextView) {
                    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Mermaid1001.ttf");
                    ((TextView)view).setTypeface(tf);
                    //((CheckedTextView) view).setSingleLine(false);
                    view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    view.setPadding(10,10,10,10);
                }*/

                if (position != selectedPosOption) {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    //view.setPressed(false);
                } else {
                    //((TextView)view).setPressed(true);
                    //((TextView)view).setChecked(true);
                    view.setBackgroundColor(ContextCompat.getColor(QuestionActivity.this, R.color.answer_select_highlight));
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
                    //optionsList.setItemChecked(i, true);
                    //view.setSelected(true);
                    selectedPosOption = i;
                    VariablesActivity.actualQuestion.setSelectedOP(i);
                    if(view.isSelected() ){
                        if (op.getEs_correcta()){
                            selectedColorOption = Color.CYAN;
                        }else{
                            selectedColorOption = Color.CYAN;
                        }
                        VariablesActivity.actualQuestion.setSelectedColorOption(selectedColorOption);
                    }
                    //adapterOptions.notifyDataSetChanged();
                }

            }
        });
        optionsList.setExpanded(true);

        initialiceImage();
    }

    private String getIndexText(){
        return "Pregunta " + String.valueOf(VariablesActivity.actualIndexPregunta  + 1 ) + "/" + String.valueOf(VariablesActivity.getQuestionListSize());
    }

    protected void initialiceImage(){
        byte[] decodedString = Base64.decode(VariablesActivity.actualQuestion.getPregunta_imagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        int iconHeight;
        int iconWidth;
        switch (density){
            case "0.75":
                iconHeight = 280;
                iconWidth = 300;
                break;
            case "1.0":
                iconHeight = 300;
                iconWidth = 400;
                break;
            case "1.5":
                iconHeight = 320;
                iconWidth = 500;
                break;
            case "2.0":
                iconHeight = 380;
                iconWidth = 600;
                break;
            case "3.0":
                iconHeight = 420;
                iconWidth = 700;
                break;
            case "4.0":
                iconHeight = 480;
                iconWidth = 800;
                break;
            default:
                iconHeight = 420;
                iconWidth = 700;
                break;
        }
        //this.questionImage.setImageBitmap(decodedByte);

        PhotoView photoView = (PhotoView) findViewById(R.id.questionImage);
        photoView.setImageBitmap(decodedByte);
    }

    public void redirectNextQuestion(){
        hasSelectedOption = true;
        VariablesActivity.actualQuestion.setHasSelected(true);
        if(VariablesActivity.getQuestionListSize() == (VariablesActivity.actualIndexPregunta + 1)){
            String score = VariablesActivity.actualTest.displayedScore();
            finish();
            Intent solution = new Intent(QuestionActivity.this, EndingActivity.class);
            solution.putExtra("score",score);
            startActivity(solution);
        }else{
            Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
            intent.putExtra("nextIndex",1);
            finish();
            startActivity(intent);
        }
    }


    public void buildCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View promptView = inflater.inflate(R.layout.custom_next_dialog, null);
        /*ImageView solutionImage = (ImageView)promptView.findViewById(R.id.solutionImage);
        byte[] decodedString = Base64.decode(VariablesActivity.actualQuestion.getSolucion_imagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        solutionImage.setImageBitmap(decodedByte);*/

        builder.setView(promptView);

        customDialog = builder.create();

        Button nextQuestionBtn = (Button)promptView.findViewById(R.id.nextQuestionBtn);
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasSelectedOption = true;
                VariablesActivity.actualQuestion.setHasSelected(true);
                if(VariablesActivity.getQuestionListSize() == (VariablesActivity.actualIndexPregunta + 1)){
                    /*
                    scoreDialog = new AlertDialog.Builder(QuestionActivity.this).create();
                    scoreDialog.setTitle("Tu score es: " + VariablesActivity.sumScore());
                    scoreDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scoreDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), TemaActivity.class));
                        }
                    });
                    customDialog.dismiss();
                    scoreDialog.show();*/
                    customDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(), EndingActivity.class));
                }else{
                    Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                    intent.putExtra("nextIndex",1);
                    customDialog.dismiss();
                    finish();
                    startActivity(intent);
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

    private class HttpGET_QuestionTask extends AsyncTask<String,Integer,ArrayList<QuestionClass>> {
        String SERVER = "http://174.138.80.160";
        String RESOURCE = "/tests/"+VariablesActivity.actualTest.get_id();


        @Override
        protected void onPreExecute() {
            progressDialog = createDialog();
        }

        @Override
        protected void onPostExecute(ArrayList<QuestionClass> questionClass) {
            reset_variables();
            if (questionClass == null){
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(QuestionActivity.this, R.style.myDialogStyle));
                builder.setMessage("NO EXISTEN DATOS QUE PRESENTAR")
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), TestActivity.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else {
                VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                        .getLstTest().get(VariablesActivity.actualIndexTest).setLstPreguntas(questionClass);
                init();
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected ArrayList<QuestionClass> doInBackground(String... strings) {
            publishProgress(0);
            if(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                    .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().isEmpty()){
                return getPreguntas();
            }else{
                return VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                        .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas();
            }
        }

        @Nullable
        private ArrayList<QuestionClass> getPreguntas() {

            ArrayList<QuestionClass> qList = new ArrayList<QuestionClass>();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(SERVER + RESOURCE);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = Util.getASCIIContentFromEntity(entity);

                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonPreguntas = jsonObject.getJSONArray("preguntas");

                for(int i=0;i<jsonPreguntas.length();i++){
                    JSONObject pregJSON = jsonPreguntas.getJSONObject(i);
                    QuestionClass auxQuestion = new QuestionClass(pregJSON.getInt("id"),pregJSON.getString("detalle"),pregJSON.getString("link_youtube") ,pregJSON.getJSONObject("pregunta_imagen").getString("bitmap"),pregJSON.getJSONObject("solucion_imagen").getString("bitmap"),pregJSON.getString("pdf"));
                    JSONArray opcionesJSON = pregJSON.getJSONArray("respuestas");
                    ArrayList<OptionClass> opList = new ArrayList<OptionClass>();
                    for(int j=0;j<opcionesJSON.length();j++){
                        JSONObject opJSON = opcionesJSON.getJSONObject(j);
                        OptionClass auxTema = new OptionClass(opJSON.getInt("id"),opJSON.getString("detalle"),opJSON.getInt("es_correcta"));
                        opList.add(auxTema);
                    }
                    auxQuestion.setOpciones(opList);
                    qList.add(auxQuestion);
                }

                return qList;

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


    protected void resetGlobalVariables(){
        VariablesActivity.actualTest = null;
        VariablesActivity.actualIndexTest = null;
    }

    @Override
    public void onBackPressed() {
        reset_variables();
        if(VariablesActivity.actualIndexPregunta == 0){
            for (QuestionClass q :VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                    .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas()) {
                q.resetQuestionVariables();
            }
            resetGlobalVariables();
            finish();
            startActivity(new Intent(getApplicationContext(), TestActivity.class));
        }else if (VariablesActivity.actualIndexPregunta > 0){
            Intent intent = new Intent(this,QuestionActivity.class);
            intent.putExtra("nextIndex",-1);
            finish();
            startActivity(intent);
        }
        //startActivity(new Intent(getApplicationContext(), TestActivity.class));
    }
}
