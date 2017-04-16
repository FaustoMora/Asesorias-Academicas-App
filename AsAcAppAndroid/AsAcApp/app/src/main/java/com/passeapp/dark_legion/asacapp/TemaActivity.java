package com.passeapp.dark_legion.asacapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;

public class TemaActivity extends AppCompatActivity {

    protected ImageButton startTestBtn;
    protected ListView listTemas;
    public ArrayAdapter<TemaClass> adapterTemas;
    private ProgressDialog progressDialog;
    public static int selectedListPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new HttpGET_TemasTask().execute();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"CONEXION A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
        }

    }

    protected void reset_variables(){
        selectedListPos = -1;
        VariablesActivity.excludesQuestions = null;
    }

    protected void init(){

        this.startTestBtn = (ImageButton)findViewById(R.id.startTestBtn);
        this.startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnlineConnect.isOnline(getApplicationContext())){
                    TemaClass aux = (TemaClass) listTemas.getSelectedItem();
                    TemaClass auxTema = (TemaClass) listTemas.getItemAtPosition(selectedListPos);
                    if(auxTema != null ){
                        //TemaClass auxTema = arrayList.get(posTema);
                        VariablesActivity.actualTema = auxTema;
                        VariablesActivity.lstQuestions.clear();
                        //Toast.makeText(getApplicationContext(),"Seleccionaste: "+auxTema.toString(),Toast.LENGTH_LONG).show();
                        VariablesActivity.scores.clear();
                        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"CONEXION A INTERNET  NO DISPONIBLE",Toast.LENGTH_LONG).show();
                }
            }
        });

        this.listTemas = (ListView)findViewById(R.id.listTemas);
        this.adapterTemas = new TemaCustomAdapter(this,VariablesActivity.lstTemas);
        this.listTemas.setAdapter(this.adapterTemas);
        this.listTemas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.listTemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelected(true);
                view.setSelected(true);

                listTemas.setSelection(i);
                listTemas.setItemChecked(i,true);
                selectedListPos = i;
            }
        });
    }

    public ProgressDialog createDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return  progressDialog;
    }


    private class HttpGET_TemasTask extends AsyncTask<Void,Integer,ArrayList<TemaClass>>{
        String SERVER = "http://174.138.80.160";
        String RESOURCE = "/materias/"+VariablesActivity.actualMateria.getId().toString();

        @Override
        protected void onPreExecute() {
            progressDialog = createDialog();
        }

        @Override
        protected void onPostExecute(ArrayList<TemaClass> temaClasses) {
            VariablesActivity.lstTemas = temaClasses;
            init();
            reset_variables();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected ArrayList<TemaClass> doInBackground(Void... voids) {
            publishProgress(0);
            return getTemas();
        }

        private ArrayList<TemaClass> getTemas() {

            ArrayList<TemaClass> temasList = new ArrayList<TemaClass>();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(SERVER + RESOURCE);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = Util.getASCIIContentFromEntity(entity);

                JSONObject materia = new JSONObject(text);
                JSONArray objects = materia.getJSONArray("temas");
                for(int i=0;i<objects.length();i++){
                    JSONObject temaJSON = objects.getJSONObject(i);
                    TemaClass auxTema = new TemaClass(temaJSON.getInt("id"),temaJSON.getString("nombre"),temaJSON.getString("descripcion"));
                    temasList.add(auxTema);
                }

            } catch (JSONException e) {
                Log.e("JSONException",e.getLocalizedMessage());
                e.printStackTrace();
                System.out.println("JSONException:"+e.getLocalizedMessage());
            } catch (Exception e) {
                Log.i("Exception:",""+e.getMessage());
                e.printStackTrace();
                System.out.println("Exception::"+e.getLocalizedMessage());
            }
            return temasList;
        }
    }

    @Override
    public void onBackPressed() {
        VariablesActivity.reset_variables();
        VariablesActivity.lstTemas.clear();
        VariablesActivity.lstQuestions.clear();
        finish();
    }


}
