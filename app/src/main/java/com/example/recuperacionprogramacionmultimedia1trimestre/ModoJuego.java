package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Thread.sleep;

public class ModoJuego extends AppCompatActivity implements SensorEventListener {

public int randomNumber;
public int rojo=0;
    //public int segundoanterior,segundoactual;
    SensorManager sensorManager;
    public ConstraintLayout gl;
    Cronometro cronometro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_juego);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), sensorManager.SENSOR_DELAY_NORMAL);
        gl = findViewById(R.id.layjuego);
        Random aleatorio = new Random();
        randomNumber =aleatorio.nextInt (6 - 1) +1;

        if(cronometro==null){
            cronometro=new Cronometro(0,randomNumber);
            cronometro.execute();
        }

        gl.setBackgroundColor(Color.BLUE);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


            if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){

                if(sensorEvent.values[0]==0){


                      Toast.makeText(getApplicationContext(),"Has Ganado",Toast.LENGTH_SHORT).show();

                }



            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class Cronometro extends AsyncTask<String,String,String>{
        int rojo=0;
        int contador=0;
        int random;
        int segundoanterior;
        int segundoactual;

        public Cronometro(int cont,int random) {
            this.contador=cont;
            this.random=random;
        }



        @Override
        protected String doInBackground(String... strings) {

            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                contador++;
                segundoanterior=(contador%60)-1;
                segundoactual = contador % 60;


                if(segundoactual==random){

                   cambiarcolor();


                }


            }

        }


    }

    private void cambiarcolor(){
        gl.setBackgroundColor(Color.RED);
    }



}




