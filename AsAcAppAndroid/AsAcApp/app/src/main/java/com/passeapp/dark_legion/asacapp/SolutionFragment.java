package com.passeapp.dark_legion.asacapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SolutionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SolutionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolutionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionClass fragmentQuestion;
    protected ExpandableHeightListView solutionList;
    private ImageView solutionImage;
    private ImageView solutionQuestionImage;
    private ImageButton solutionYoutube;
    private ImageButton downPDF;
    private ProgressDialog progressPDFDialog;
    String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public SolutionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolutionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolutionFragment newInstance(String param1, String param2) {
        SolutionFragment fragment = new SolutionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setActualQuestion(QuestionClass object){
        this.fragmentQuestion = object;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solution_tabeb, container, false);
        if(fragmentQuestion != null) {
            init(view, fragmentQuestion);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void init(View fragmentView, final QuestionClass actualQuestion){

        this.solutionQuestionImage = (ImageView)fragmentView.findViewById(R.id.solutionQuestionImage);
        this.solutionImage = (ImageView)fragmentView.findViewById(R.id.solutionImage);
        this.solutionYoutube = (ImageButton) fragmentView.findViewById(R.id.youtube);
        this.solutionList = (ExpandableHeightListView)fragmentView.findViewById(R.id.solutionList);

        this.solutionList.setAdapter(new ArrayAdapter<OptionClass>(getContext(), android.R.layout.simple_list_item_1, actualQuestion.getOpciones()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                OptionClass op = getItem(position);

                if (row instanceof TextView) {
                    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Mermaid1001.ttf");
                    ((TextView)row).setTypeface(tf);
                    ((TextView) row).setSingleLine(false);
                    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    row.setPadding(10,10,10,10);
                }

                if(op.getEs_correcta())
                {
                    ((TextView)row).setTypeface(((TextView)row).getTypeface(), Typeface.BOLD);
                    ((TextView)row).setTextColor(ContextCompat.getColor(getContext(), R.color.correct_answers_green));
                    // do something change color
                    //row.setBackgroundColor (Color.GREEN); // some color
                }
                else
                {
                    if(position == actualQuestion.getSelectedOP()){
                        //row.setBackgroundColor (Color.RED);
                        ((TextView)row).setTypeface(((TextView)row).getTypeface(), Typeface.NORMAL);
                        ((TextView)row).setPaintFlags(((TextView)row).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ((TextView)row).setTextColor(ContextCompat.getColor(getContext(), R.color.wrong_answer_red));
                    }else{
                        //row.setBackgroundColor (Color.TRANSPARENT);
                        ((TextView)row).setPaintFlags( ((TextView)row).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        ((TextView)row).setTypeface(((TextView)row).getTypeface(), Typeface.NORMAL);
                        ((TextView)row).setTextColor(Color.BLACK);
                    }
                }
                return row;
            }
        });

        solutionList.setExpanded(true);


        initialiceImage(actualQuestion,fragmentView);
        this.solutionYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkYoutube = actualQuestion.getLink_youtube();

                if(linkYoutube != "null"){
                    try{
                        Intent videoIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube://" + linkYoutube.replace("https://www.youtube.com/watch?v=","")));
                        startActivity(videoIntent);
                    }catch (Exception e){
                        Toast.makeText(getContext(),"NO TIENES INSTALADO YOUTUBE APP",Toast.LENGTH_LONG).show();
                        Intent videoIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(linkYoutube));
                        startActivity(videoIntent);
                        Log.e("youtube error", e.getLocalizedMessage());
                    }
                }
            }
        });



        this.downPDF = (ImageButton)fragmentView.findViewById(R.id.downPDF);
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
        /*
        PhotoViewAttacher photoView = new PhotoViewAttacher(solutionImage);
        photoView.update();

        PhotoViewAttacher photoView2 = new PhotoViewAttacher(solutionQuestionImage);
        photoView2.update();*/
    }

    protected void initialiceImage(QuestionClass actualQuestion, View fragmentView){
        if(!actualQuestion.getPregunta_imagen().isEmpty() && actualQuestion.getPregunta_imagen() != null){
            byte[] decodedString = Base64.decode(actualQuestion.getPregunta_imagen(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //this.solutionQuestionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));
            PhotoView photoView = (PhotoView) fragmentView.findViewById(R.id.solutionQuestionImage);
            photoView.setImageBitmap(decodedByte);
        }

        if(!actualQuestion.getSolucion_imagen().isEmpty() && actualQuestion.getSolucion_imagen() != null) {
            byte[] decodedString = Base64.decode(actualQuestion.getSolucion_imagen(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //this.solutionImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 700, 420, false));
            PhotoView photoView2 = (PhotoView) fragmentView.findViewById(R.id.solutionImage);
            photoView2.setImageBitmap(decodedByte);
        }

        String linkYoutube = actualQuestion.getLink_youtube();

        if(linkYoutube != null && linkYoutube != "null"){
            this.solutionYoutube.setVisibility(View.VISIBLE);
        }else{
            this.solutionYoutube.setVisibility(View.INVISIBLE);
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
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(getContext(),"Se requiere permiso para descargar",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public ProgressDialog createProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Descargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return  progressDialog;
    }


    public boolean hasPermissions(){
        int res=0;

        for(String perms : permissions){
            res = getContext().checkCallingOrSelfPermission(perms);
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


    public void downloadQuestionPDF(){
        try{
            new SolutionFragment.PDFTest().execute();
        }catch (Exception e){
            Log.e("PDF",e.getLocalizedMessage());
            Toast.makeText(getContext(),"OCURRIO Un ERROR En LA DESCARGA",Toast.LENGTH_LONG).show();
        }
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
                Toast.makeText(getContext(),"DESCARGA EXITOSA",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"OCURRIO Un ERROR En LA DESCARGA",Toast.LENGTH_LONG).show();
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
                        Environment.DIRECTORY_DOWNLOADS), "TeachersAid");
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
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.encabezado_de_pdf);
                //InputStream ims = getAssets().open("espol.png");
                //Bitmap bmpIcon = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream streamIcon = new ByteArrayOutputStream();
                largeIcon.compress(Bitmap.CompressFormat.PNG, 100, streamIcon);
                Image icon = Image.getInstance(streamIcon.toByteArray());
                icon.scaleToFit(100f,50f);
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

                try{
                    manipulatePdf(myFile.getAbsolutePath(),myFile.getAbsolutePath());
                }catch (Exception e){
                    Log.e("error watermark",e.getLocalizedMessage());
                }

                return true;
            }catch (Exception e){
                Log.e("PDF",e.getLocalizedMessage());
                return false;
            }

        }


        public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
            PdfReader reader = new PdfReader(src);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            stamper.setRotateContents(false);
            // image watermark
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fondopdf);
            ByteArrayOutputStream streamIcon = new ByteArrayOutputStream();
            largeIcon.compress(Bitmap.CompressFormat.PNG, 100, streamIcon);

            Image img = Image.getInstance(streamIcon.toByteArray());
            float w = img.getScaledWidth();
            float h = img.getScaledHeight();
            // transparency
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            // properties
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            // loop over every page
            for (int i = 1; i <= n; i++) {
                pagesize = reader.getPageSize(i);
                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                over = stamper.getUnderContent(i);
                over.saveState();
                over.setGState(gs1);
                over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                over.restoreState();
            }
            stamper.close();
            reader.close();
        }
    }
}
