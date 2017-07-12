package com.passeapp.dark_legion.asacapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    Button initBtn;
    ImageView icono;
    ImageView showDownBtn;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        init();

        if (playServicesAvailable()) {
            PusherAndroid pusher = new PusherAndroid("e3c974561ec35f9b5b88");
            PushNotificationRegistration nativePusher = pusher.nativePusher();
            String defaultSenderId = getString(R.string.gcm_defaultSenderId); // fetched from your google-services.json

            try {
                nativePusher.registerGCM(this, defaultSenderId);
            } catch (ManifestValidator.InvalidManifestException e) {
                e.printStackTrace();
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
    }

    public void init(){
        this.icono = (ImageView)findViewById(R.id.iconoApp);
        this.initBtn = (Button)findViewById(R.id.startAppBtn);
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
    }

    private boolean playServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("ERROR PUSHER", "This device is not supported.");
            }
            return false;
        }
        return true;
    }
}
