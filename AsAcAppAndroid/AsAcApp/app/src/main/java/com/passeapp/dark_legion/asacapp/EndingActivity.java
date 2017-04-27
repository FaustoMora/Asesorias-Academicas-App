package com.passeapp.dark_legion.asacapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EndingActivity extends AppCompatActivity {

    private TextView lblScore;
    private Button restartBtn;
    private Button pdfDown;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        init();
    }

    public void init(){
        lblScore = (TextView)findViewById(R.id.lblScore);
        lblScore.setText(VariablesActivity.displayedScore());
        restartBtn = (Button)findViewById(R.id.repeatBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
            }
        });
        pdfDown = (Button)findViewById(R.id.btnPDF);
        pdfDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    new PDFTest().execute();
                }catch (Exception e){
                    Log.e("PDF",e.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(),"OCURRIO Un ERROR En LA DESCARGA",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public ProgressDialog createDialog(){
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
            progressDialog = createDialog();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean){
                finish();
                Toast.makeText(getApplicationContext(),"DESCARGA EXITOSA",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
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

                File myFile = new File(pdfFolder + timeStamp + ".pdf");
                OutputStream output = new FileOutputStream(myFile);

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, output);
                document.open();

                Chunk textTitle = new Chunk(VariablesActivity.actualTema.getDescription(), titleDoc);
                document.add(new Paragraph(textTitle));
                document.add(new Paragraph(""));
                int i = 0;
                for (QuestionClass aux: VariablesActivity.lstQuestions) {
                    i++;
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
                    image.scaleToFit(PageSize.A4.getWidth(), 250f);
                    //Image image = Image.getInstance(stream.toByteArray());
                    Image image2 = Image.getInstance(decodedString2);
                    image2.scaleToFit(PageSize.A4.getWidth()-65f, 420f);

                    document.add(new Paragraph(""));
                    Chunk text = new Chunk("Pregunta No" + i, title);
                    document.add(new Paragraph(text));
                    document.add(image);
                    for (OptionClass op: aux.getOpciones()) {
                        Chunk optionText = new Chunk(op.getDetalle(),options);
                        document.add(new Paragraph(optionText));
                    }
                    document.add(image2);
                    document.newPage();
                }
                document.close();

                return true;
            }catch (Exception e){
                Log.e("PDF",e.getLocalizedMessage());
                return false;
            }

        }
    }

    @Override
    public void onBackPressed() {

    }
}
