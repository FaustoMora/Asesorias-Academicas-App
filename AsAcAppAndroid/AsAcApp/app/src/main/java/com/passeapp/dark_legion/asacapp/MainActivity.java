package com.passeapp.dark_legion.asacapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected ImageButton startTestBtn;
    protected ListView listTemas;
    public ArrayAdapter<TemaClass> adapterTemas;

    public TemaClass exampleArray[] = {new TemaClass(1,"Tema 1","Tema 1"), new TemaClass(2,"Tema DOS","Tema DOS"), new TemaClass(2,"Tema Tres","Tema Tres")};
    public ArrayList<TemaClass> arrayList = new ArrayList<TemaClass>(Arrays.asList(exampleArray));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            this.arrayList = new HttpGET_TemasTask().execute().get();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"CONEXION A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
        }
        init();

    }

    protected void init(){

        this.startTestBtn = (ImageButton)findViewById(R.id.startTestBtn);
        this.startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posTema = listTemas.getCheckedItemPosition();
                if(posTema > -1){
                    TemaClass auxTema = arrayList.get(posTema);
                    QuestionActivity.actualTema = auxTema;
                    Toast.makeText(getApplicationContext(),"Seleccionaste: "+auxTema.toString(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.listTemas = (ListView)findViewById(R.id.listTemas);
        this.adapterTemas = new ArrayAdapter<TemaClass>(this,android.R.layout.simple_list_item_single_choice,this.arrayList);
        this.listTemas.setAdapter(this.adapterTemas);
    }



    private class HttpGET_TemasTask extends AsyncTask<Void,Integer,ArrayList<TemaClass>>{
        String SERVER = "http://174.138.80.160/";
        String RESOURCE = "temas/";

        @Override
        protected ArrayList<TemaClass> doInBackground(Void... voids) {
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

                JSONArray objects = new JSONArray(text);
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
}
