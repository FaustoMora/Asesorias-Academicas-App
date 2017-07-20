package com.passeapp.dark_legion.asacapp;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;


public class QuestionActivity extends AppCompatActivity {

    protected Button continueTestBtn;
    protected ListView optionsList;
    public ArrayAdapter<OptionClass> adapterOptions;
    public AlertDialog scoreDialog;
    private ImageView questionImage;
    public static int selectedPosOption;
    public static int selectedColorOption;
    public boolean hasSelectedOption = false;
    public ProgressDialog progressDialog;
    public Dialog customDialog;
    private String density;
    private ImageView downPDF;
    private TextView lblTema;
    private TextView lblIndex;
    private ProgressDialog progressPDFDialog;
    private TextView lblTest;
    String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;


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
        VariablesActivity.actualQuestion = VariablesActivity.lstQuestions.get(VariablesActivity.actualIndexPregunta);
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
        this.continueTestBtn = (Button)findViewById(R.id.questionContinueBtn);
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

        this.optionsList = (ListView)findViewById(R.id.optionsList);
        this.adapterOptions = new ArrayAdapter<OptionClass>(this,android.R.layout.simple_list_item_single_choice,VariablesActivity.actualQuestion.getOpciones()){
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
                    VariablesActivity.actualQuestion.setSelectedOP(i);
                    if(view.isSelected() ){
                        if (op.getEs_correcta()){
                            selectedColorOption = Color.CYAN;
                        }else{
                            selectedColorOption = Color.CYAN;
                        }
                        VariablesActivity.actualQuestion.setSelectedColorOption(selectedColorOption);
                    }
                    adapterOptions.notifyDataSetChanged();
                }

            }
        });
        initialiceImage();

        this.downPDF = (ImageView)findViewById(R.id.downPDF);
        this.downPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasPermissions()){
                    downloadQuestionPDF();
                }else{
                    requestPerm();
                }
            }
        });
        PhotoViewAttacher photoView = new PhotoViewAttacher(questionImage);
        photoView.update();
    }

    public void downloadQuestionPDF(){
        try{
            new QuestionActivity.PDFTest().execute();
        }catch (Exception e){
            Log.e("PDF",e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(),"OCURRIO Un ERROR En LA DESCARGA",Toast.LENGTH_LONG).show();
        }
    }


    public boolean hasPermissions(){
        int res=0;

        for(String perms : permissions){
            res = this.checkCallingOrSelfPermission(perms);
            if(!(res== PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }

        return true;
    }

    public void requestPerm(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                for(int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }

        if(allowed){
            downloadQuestionPDF();
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout,"Se requiere permiso para descargar",Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }

    private String getIndexText(){
        return "Pregunta " + String.valueOf(VariablesActivity.actualIndexPregunta  + 1 ) + "/" + String.valueOf(VariablesActivity.lstQuestions.size());
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
        this.questionImage.setImageBitmap(decodedByte);
    }

    public void redirectNextQuestion(){
        hasSelectedOption = true;
        VariablesActivity.actualQuestion.setHasSelected(true);
        if(VariablesActivity.lstQuestions.size() == (VariablesActivity.actualIndexPregunta + 1)){
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
                if(VariablesActivity.lstQuestions.size() == (VariablesActivity.actualIndexPregunta + 1)){
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
                reset_variables();
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
                VariablesActivity.lstQuestions = questionClass;
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
                    QuestionClass auxQuestion = new QuestionClass(pregJSON.getInt("id"),pregJSON.getString("detalle"),pregJSON.getString("link_youtube") ,pregJSON.getJSONObject("pregunta_imagen").getString("bitmap"),pregJSON.getJSONObject("solucion_imagen").getString("bitmap"));
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


    public ProgressDialog createProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Descargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return  progressDialog;
    }


    public class PDFTest extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return createPdf();
        }

        @Override
        protected void onPreExecute() {
            progressPDFDialog = createProgressDialog();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressPDFDialog.dismiss();
            if(aBoolean){
                Toast.makeText(getApplicationContext(),"DESCARGA EXITOSA",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"OCURRIO Un ERROR En LA DESCARGA",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        private Boolean createPdf(){

            Font titleDoc = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK);
            Font title = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
            Font options = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL, BaseColor.DARK_GRAY);

            try{
                File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "pdfTest");
                if (!pdfFolder.exists()) {
                    pdfFolder.mkdir();
                    Log.i("PDF", "Pdf Directory created");
                }

                //Create time stamp
                Date date = new Date() ;
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(date);

                File myFile = new File(pdfFolder + "/" + timeStamp + ".pdf");
                OutputStream output = new FileOutputStream(myFile);

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, output);
                document.open();


                // get input stream
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.espol);
                //InputStream ims = getAssets().open("espol.png");
                //Bitmap bmpIcon = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream streamIcon = new ByteArrayOutputStream();
                largeIcon.compress(Bitmap.CompressFormat.PNG, 100, streamIcon);
                Image icon = Image.getInstance(streamIcon.toByteArray());
                icon.scaleToFit(40f, 40f);
                document.add(icon);

                Chunk titleMateria = new Chunk(VariablesActivity.actualMateria.getNombre(), titleDoc);
                Chunk titleTema = new Chunk(VariablesActivity.actualTema.getDescription(), title);
                Chunk titleTest = new Chunk(VariablesActivity.actualTest.getNombre(), title);
                Chunk tittleQuestion = new Chunk(VariablesActivity.actualQuestion.getActualIndex(), options);
                document.add(new Paragraph(titleMateria));
                document.add(new Paragraph(titleTema));
                document.add(new Paragraph(titleTest));
                document.add(new Paragraph(tittleQuestion));
                document.add(new Paragraph(""));

                QuestionClass aux = VariablesActivity.actualQuestion;
                // get input stream
                byte[] decodedString = Base64.decode(aux.getPregunta_imagen(), Base64.DEFAULT);
                byte[] decodedString2 = Base64.decode(aux.getSolucion_imagen(), Base64.DEFAULT);

                // se debe usar para el tamano de las imgs, por defualt el decode base64 renderiza tamano original
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap bmp = Bitmap.createBitmap(decodedByte);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //----------------------------------
                Image image = Image.getInstance(decodedString);
                image.scaleToFit(PageSize.A4.getWidth()-65f, 250f);
                //Image image = Image.getInstance(stream.toByteArray());
                Image image2 = Image.getInstance(decodedString2);
                image2.scaleToFit(PageSize.A4.getWidth()-65f, 420f);

                //document.add(new Paragraph(""));
                //Chunk text = new Chunk("Pregunta No" + i, title);
                //document.add(new Paragraph(text));
                document.add(image);
                for (OptionClass op: aux.getOpciones()) {
                    Chunk optionText = new Chunk(op.getDetalle(),options);
                    document.add(new Paragraph(optionText));
                }
                document.add(image2);
                if(aux.getLink_youtube() != "null"){
                    document.add(new Paragraph("Link del video: "+aux.getLink_youtube()));
                }
                //document.newPage();
                document.close();

                return true;
            }catch (Exception e){
                Log.e("PDF",e.getLocalizedMessage());
                return false;
            }

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
