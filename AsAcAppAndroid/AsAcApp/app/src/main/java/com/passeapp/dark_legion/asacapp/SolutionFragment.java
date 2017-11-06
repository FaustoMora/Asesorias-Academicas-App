package com.passeapp.dark_legion.asacapp;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.io.File;


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
    private AdView mAdView;


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt,"DESCARGA COMPLETA",Toast.LENGTH_SHORT).show();
        }
    };

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

        getContext().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solution_tabeb, container, false);
        if(fragmentQuestion != null) {
            init(view, fragmentQuestion);
        }

        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("admob","finish loading");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("admob","fails loading loading " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("admob","add opened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("admob","add left app");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("admob","add closed");
            }
        });


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
        this.downPDF = (ImageButton)fragmentView.findViewById(R.id.downPDF);
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

        if(actualQuestion.getLink_youtube() != null){
            this.solutionYoutube.setVisibility(View.VISIBLE);
        }else{
            this.solutionYoutube.setVisibility(View.GONE);
        }

        if(actualQuestion.getPdf() != null){
            this.downPDF.setVisibility(View.VISIBLE);
        }else{
            this.downPDF.setVisibility(View.GONE);
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


    public void downloadQuestionPDFWithDownManager(){
        if(this.fragmentQuestion.getPdf()!= null){
            Toast.makeText(getContext(),"INICIANDO DESCARGA",Toast.LENGTH_SHORT).show();
            File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "TeachersAid");
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
                Log.i("PDF", "Pdf Directory created");
            }

            String httpUrl = VariablesActivity.SERVER + this.fragmentQuestion.getPdf();
            String nameOfFile = URLUtil.guessFileName(httpUrl,null, MimeTypeMap.getFileExtensionFromUrl(httpUrl));

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(httpUrl));
            request.setTitle(nameOfFile);
            request.setDescription("El archivo se esta descargando...");

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/TeachersAid/", nameOfFile);
            DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
    }


    public void downloadQuestionPDF(){
        try {
            this.downloadQuestionPDFWithDownManager();
        }catch (Exception e){
            Log.e("pdf down ERROR",e.getLocalizedMessage());
            Toast.makeText(getContext(),"OCURRIO UN ERROR AL DESCARGAR EL PDF",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(onComplete);
        super.onDestroy();
    }
}
