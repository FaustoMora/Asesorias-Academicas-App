package com.passeapp.dark_legion.asacapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class TestActivity extends AppCompatActivity {

    protected Button startTestBtn;
    protected ListView listTests;
    protected TextView lblTemaTest;
    protected ImageView formulaIcon;
    public ArrayAdapter<TestClass> adapterTests;
    private ProgressDialog progressDialog;
    public static int selectedListPos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        try {
            new TestActivity.HttpGET_TestTask().execute();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"CONEXION A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), TemaActivity.class));
        }
    }

    protected void reset_variables(){
        selectedListPos = -1;
        VariablesActivity.actualTema = null;
        VariablesActivity.actualIndexTema = null;
    }

    protected void init(){

        this.formulaIcon = (ImageView) findViewById(R.id.formulaIcon);
        if(!VariablesActivity.actualTema.getFormulas().isEmpty() && !VariablesActivity.actualTema.getFormulas().equals("null")){
            this.formulaIcon.setVisibility(View.VISIBLE);
            this.formulaIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://174.138.80.160/"+VariablesActivity.actualTema.getFormulas())));
                }
            });
        }

        this.lblTemaTest = (TextView)findViewById(R.id.lblTemaTest);
        this.lblTemaTest.setText(VariablesActivity.actualTema.getNombre());

        this.startTestBtn = (Button)findViewById(R.id.startTestBtn);
        this.startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnlineConnect.isOnline(getApplicationContext())){
                    TestClass auxTest = (TestClass) listTests.getItemAtPosition(selectedListPos);
                    if(auxTest != null ){
                        VariablesActivity.actualTest = auxTest;
                        VariablesActivity.actualIndexTest = selectedListPos;
                        finish();
                        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Seleccione un tema para continuar",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"CONEXION A INTERNET  NO DISPONIBLE",Toast.LENGTH_LONG).show();
                }
            }
        });

        this.listTests = (ListView)findViewById(R.id.listTest);
        this.adapterTests = new TestCustomAdapter(this,VariablesActivity.lstTests);
        this.listTests.setAdapter(this.adapterTests);
        this.listTests.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.listTests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelected(true);
                view.setSelected(true);
                listTests.setSelection(i);
                listTests.setItemChecked(i,true);
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


    private class HttpGET_TestTask extends AsyncTask<Void,Integer,ArrayList<TestClass>> {
        String SERVER = "http://174.138.80.160";
        String RESOURCE = "/temas/"+VariablesActivity.actualTema.get_id().toString();

        @Override
        protected void onPreExecute() {
            progressDialog = createDialog();
        }

        @Override
        protected void onPostExecute(ArrayList<TestClass> lstTests) {
            if(lstTests != null){
                VariablesActivity.lstTests = lstTests;
                VariablesActivity.actualTema.setLstTest(lstTests);
                VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema).setLstTest(lstTests);
                init();
                reset_variables();
                progressDialog.dismiss();
            }else{
                reset_variables();
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(TestActivity.this, R.style.myDialogStyle));
                builder.setMessage("NO EXISTEN DATOS QUE PRESENTAR")
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), TemaActivity.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected ArrayList<TestClass> doInBackground(Void... voids) {
            publishProgress(0);
            if(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema).getLstTest().isEmpty()){
                return getTests();
            }else{
                return VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema).getLstTest();
            }
        }

        private ArrayList<TestClass> getTests() {

            ArrayList<TestClass> list = new ArrayList<TestClass>();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(SERVER + RESOURCE);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = Util.getASCIIContentFromEntity(entity);

                JSONObject tema = new JSONObject(text);
                JSONArray objects = tema.getJSONArray("tests");
                for(int i=0;i<objects.length();i++){
                    JSONObject temaJSON = objects.getJSONObject(i);
                    TestClass aux = new TestClass(temaJSON.getInt("id"),temaJSON.getString("nombre"));
                    list.add(aux);
                }

                return list;
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

    @Override
    public void onBackPressed() {
        reset_variables();
        finish();
        startActivity(new Intent(getApplicationContext(), TemaActivity.class));
    }
}
