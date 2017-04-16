package com.passeapp.dark_legion.asacapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

public class MateriaActivity extends AppCompatActivity {

    GridView gridView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        try{
            new HttpGET_MateriaTask().execute();
        }catch (Exception e){
            Log.e("error api",e.getLocalizedMessage());
            Toast.makeText(getApplicationContext(),"CONEXION A INTERNET NO DISPONIBLE",Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), InitActivity.class));
        }
    }

    public void init(){
        this.gridView = (GridView)findViewById(R.id.gridViewMaterias);
        this.gridView.setAdapter(new GridAdapter());
        this.gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(OnlineConnect.isOnline(getApplicationContext())){
                    MateriaClass aux = VariablesActivity.lstMaterias.get(i);
                    if(aux != null){
                        VariablesActivity.actualMateria = aux;
                        startActivity(new Intent(getApplicationContext(), TemaActivity.class));
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "CONEXION A INTERNET NO DISPONIBLE", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    public void reset_variables(){
        VariablesActivity.actualMateria = null;
        VariablesActivity.lstTemas.clear();
    }

    public ProgressDialog createDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return  progressDialog;
    }


    private class HttpGET_MateriaTask extends AsyncTask<Void,Integer,ArrayList<MateriaClass>> {
        String SERVER = "http://174.138.80.160";
        String RESOURCE = "/materias";

        @Override
        protected ArrayList<MateriaClass> doInBackground(Void... voids) {
            publishProgress(0);
            return getMaterias();
        }

        @Override
        protected void onPostExecute(ArrayList<MateriaClass> materiaClasses) {
            VariablesActivity.lstMaterias = materiaClasses;
            init();
            reset_variables();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = createDialog();
        }

        private ArrayList<MateriaClass> getMaterias() {

            ArrayList<MateriaClass> materiasList = new ArrayList<MateriaClass>();
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
                    MateriaClass aux = new MateriaClass(temaJSON.getInt("id"),temaJSON.getString("nombre_materia"),temaJSON.getString("icono_materia"));
                    materiasList.add(aux);
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
            return materiasList;
        }
    }


    class GridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return VariablesActivity.lstMaterias.size();
        }

        @Override
        public Object getItem(int i) {
            return VariablesActivity.lstMaterias.get(i);
        }

        @Override
        public long getItemId(int i) {
            return VariablesActivity.lstMaterias.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_grid_element,viewGroup,false);

            ImageView icon = (ImageView)view.findViewById(R.id.iconoMateria);
            MateriaClass aux = (MateriaClass) getItem(i);
            byte[] decodedString = Base64.decode(aux.getIcono(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 150, 150, false));

            return view;
        }
    }

    @Override
    public void onBackPressed() {
        VariablesActivity.resetAllvariables();
        VariablesActivity.reset_list();
        finish();
    }
}
