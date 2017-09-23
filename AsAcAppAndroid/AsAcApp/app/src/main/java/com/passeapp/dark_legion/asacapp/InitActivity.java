package com.passeapp.dark_legion.asacapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.ManifestValidator;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.gcm.GCMPushNotificationReceivedListener;
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener;

public class InitActivity extends AppCompatActivity {

    ImageButton initBtn;
    ImageView icono;
    ImageView showDownBtn;
    ImageView btnTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        init();

        if (playServicesAvailable()) {
            try{
                if(Build.VERSION.SDK_INT != Build.VERSION_CODES.M){
                    PusherAndroid pusher = new PusherAndroid("e3c974561ec35f9b5b88");
                    PushNotificationRegistration nativePusher = pusher.nativePusher();
                    String defaultSenderId = getString(R.string.gcm_defaultSenderId); // fetched from your google-services.json

                    try {
                        nativePusher.registerGCM(this, defaultSenderId);
                        Log.d("Pusher", "Success register GCM");
                    } catch (ManifestValidator.InvalidManifestException e) {
                        e.printStackTrace();
                        Log.d("Pusher", e.getLocalizedMessage());
                    }


                    nativePusher.subscribe("asacapp", new InterestSubscriptionChangeListener() {
                        @Override
                        public void onSubscriptionChangeSucceeded() {
                            System.out.println("Success! I love donuts!");
                        }

                        @Override
                        public void onSubscriptionChangeFailed(int statusCode, String response) {
                            System.out.println(":(: received " + statusCode + " with" + response);
                        }
                    });

                    nativePusher.setGCMListener(new GCMPushNotificationReceivedListener() {
                        @Override
                        public void onMessageReceived(String from, Bundle data) {
                            // do something magical 🔮
                            String message = data.getString("tittle");
                            Log.d("GCMListener", "Received push notification from: " + from);
                            Log.d("GCMListener", "Message: " + message);
                        }
                    });
                }
            }catch (Exception e){
                Log.e("init pusher error",e.getLocalizedMessage());
            }
        }
    }

    public void init(){
        this.icono = (ImageView)findViewById(R.id.iconoApp);
        this.btnTutors = (ImageView)findViewById(R.id.btnTutors);
        this.initBtn = (ImageButton)findViewById(R.id.startAppBtn);
        this.initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnlineConnect.isOnline(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(), MateriaActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"No dispones de conexion a internet",Toast.LENGTH_LONG).show();
                }
            }
        });

        this.showDownBtn = (ImageView) findViewById(R.id.showDownBtn);
        this.showDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri selectedUri;
                if(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) != null ){
                    selectedUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
                    intent.setData(selectedUri);
                    intent.setType("*/*");
                    startActivityForResult(intent, 7);
                }else{
                    startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                }
            }
        });

        this.btnTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(VariablesActivity.TUTORS_URL));
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("visit us","error abrir link en google");

                }
            }

        });
    }

    private boolean playServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                //apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Log.i("playservices", "apiAvailability.getErrorDialog");
            } else {
                Log.i("playservices", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

}
