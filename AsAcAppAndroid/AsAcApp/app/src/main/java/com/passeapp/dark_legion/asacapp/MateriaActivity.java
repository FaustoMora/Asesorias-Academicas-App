package com.passeapp.dark_legion.asacapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
    private Float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        density = getResources().getDisplayMetrics().density;


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
    }

    public void reset_variables(){
        VariablesActivity.actualMateria = null;
        VariablesActivity.actualTema = null;
        VariablesActivity.actualTest = null;
        VariablesActivity.actualQuestion = null;
        VariablesActivity.actualIndexMateria = null;
        VariablesActivity.actualIndexTema = null;
        VariablesActivity.actualIndexTest = null;
        VariablesActivity.actualIndexPregunta = null;
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
            if(VariablesActivity.lstMaterias.isEmpty()){
                return getMaterias();
            }else{
                return VariablesActivity.lstMaterias;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MateriaClass> materiaClasses) {
            if(materiaClasses != null) {
                VariablesActivity.lstMaterias = materiaClasses;
                init();
                reset_variables();
                progressDialog.dismiss();
            }else{
                reset_variables();
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MateriaActivity.this, R.style.myDialogStyle));
                builder.setMessage("NO EXISTEN DATOS QUE PRESENTAR")
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), InitActivity.class));
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
                    MateriaClass aux = new MateriaClass(temaJSON.getInt("id"),temaJSON.getString("nombre"),temaJSON.getString("icono"));
                    materiasList.add(aux);
                }

                return materiasList;
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            View view = convertView;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.custom_grid_element,null);
            }

            ImageView icon = (ImageView)view.findViewById(R.id.iconoMateria);
            MateriaClass aux = (MateriaClass) getItem(i);
            byte[] decodedString = Base64.decode(aux.getIcono(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // 0.75 if it's LDPI
            // 1.0 if it's MDPI
            // 1.5 if it's HDPI
            // 2.0 if it's XHDPI
            // 3.0 if it's XXHDPI
            // 4.0 if it's XXXHDPI
            int iconSizeHeight;
            int iconSizeWidth;
            if(density <= 0.75f){
                iconSizeHeight = 170;
                iconSizeWidth = 100;
            }else if( density > 0.75f && density <= 1.0f){
                iconSizeHeight = 200;
                iconSizeWidth = 150;
            }else if( density > 1.0f && density <= 1.5f){
                iconSizeHeight = 220;
                iconSizeWidth = 165;
            }else if( density > 1.5f && density <= 2.0f){
                iconSizeHeight = 340;
                iconSizeWidth = 260;
            }else if( density > 2.0f && density <= 3.0f){
                iconSizeHeight = 440;
                iconSizeWidth = 350;
            }else if( density > 3.0f && density <= 4.0f){
                iconSizeHeight = 500;
                iconSizeWidth = 400;
            }else{
                iconSizeHeight = 450;
                iconSizeWidth = 345;
            }

            final int index = i;

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(OnlineConnect.isOnline(getApplicationContext())){
                        MateriaClass aux = VariablesActivity.lstMaterias.get(index);
                        if(aux != null){
                            VariablesActivity.actualMateria = aux;
                            VariablesActivity.actualIndexMateria = index;
                            finish();
                            startActivity(new Intent(getApplicationContext(), TemaActivity.class));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "CONEXION A INTERNET NO DISPONIBLE", Toast.LENGTH_LONG).show();
                    }
                }
            });

            icon.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, iconSizeWidth, iconSizeHeight, false));
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        reset_variables();
        finish();
        startActivity(new Intent(getApplicationContext(), InitActivity.class));
    }
}
