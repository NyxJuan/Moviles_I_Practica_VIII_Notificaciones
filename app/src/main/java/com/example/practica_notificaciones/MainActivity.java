package com.example.practica_notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    private Button btnNotificar;
    private TextInputEditText mensaje;
    private MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = MediaPlayer.create(this,R.raw.sound);
        mensaje = findViewById(R.id.txt_mensaje);
        btnNotificar = findViewById(R.id.notify);

        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contenido = mensaje.getText().toString();

                if (!contenido.equals("")){
                    sendNotification();
                    Toast.makeText(getApplicationContext(),"La notificacion fue enviada", Toast.LENGTH_SHORT).show();
                    EjecutarSonido();
                    EjecutarVibracion();
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor Ingrese su mensaje",Toast.LENGTH_SHORT).show();
                }
            }
        });
        createNotificationChannel();
    }

    private void EjecutarSonido(){
        sound.start();
    }

    private void EjecutarVibracion(){
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }


    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Nueva Notificacion Recibida")
                .setContentText(mensaje.getText().toString())
                .setSmallIcon(R.drawable.ic_notification);
        return notifyBuilder;

    }

    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "UPT Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from UPT");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

}