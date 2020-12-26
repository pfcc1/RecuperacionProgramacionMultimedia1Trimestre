package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sonido extends AppCompatActivity {
Button buttonSilenciarSonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonido);

        buttonSilenciarSonido=findViewById(R.id.buttonSilenciarSonido);

        startService(new Intent(getApplicationContext(),ServicioMusica.class));
        buttonSilenciarSonido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), ServicioMusica.class));

            }
        });
    }
}
