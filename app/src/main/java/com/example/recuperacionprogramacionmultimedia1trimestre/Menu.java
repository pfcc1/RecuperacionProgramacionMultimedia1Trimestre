package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

Button buttonSonido,buttonAgregarTarea,buttonMostrarTareas,buttonRealizarTarea,buttonBorrarTareas,buttonSalir,buttonJuego;
MediaPlayer mediaPlayer;
ManejadorBD manejadorBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mediaPlayer = MediaPlayer.create(this, R.raw.vuelve);
        buttonSonido = findViewById(R.id.buttonVentanaSonido);
        buttonAgregarTarea = findViewById(R.id.buttonAgregarTareas);
        buttonMostrarTareas = findViewById(R.id.buttonMostrarTareas);
        buttonRealizarTarea=findViewById(R.id.buttonRealizarTarea);
        buttonBorrarTareas=findViewById(R.id.buttonBorrarTareas);
        buttonJuego=findViewById(R.id.buttonModoJuego);
        buttonSalir=findViewById(R.id.buttonSalir);

        manejadorBD=new ManejadorBD(this);
        buttonAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgregarTarea = new Intent(getApplicationContext(), Agregar_Tarea.class);
                startActivity(intentAgregarTarea);
            }
        });


        buttonMostrarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMostrarTareas = new Intent(getApplicationContext(), Mostrar_Tareas.class);
                startActivity(intentMostrarTareas);
            }
        });

        buttonRealizarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intentRealizarTarea=new Intent(getApplicationContext(),RealizarTarea.class);
            startActivity(intentRealizarTarea);
            }
        });

        buttonBorrarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBorrarTarea=new Intent(getApplicationContext(),BorrarTareas.class);
                startActivity(intentBorrarTarea);
            }
        });


        buttonSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentSonido = new Intent(getApplicationContext(), Sonido.class);
                startActivity(intentSonido);

            }
        });

        buttonJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego=new Intent(getApplicationContext(),ModoJuego.class);
                startActivity(intentJuego);
            }
        });

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Cursor cursor=manejadorBD.obtenerNumerodeTareasNoRealizadas();
                int numero=0;

                   while (cursor.moveToNext()){
                          numero= cursor.getInt(0);

                    }

                    cursor.close();



              Toast.makeText(getApplicationContext(),"Numero de Tareas no Realizadas: "+numero,Toast.LENGTH_LONG).show();

             // finish();

                finishAffinity();
            }
        });
    }



    }

