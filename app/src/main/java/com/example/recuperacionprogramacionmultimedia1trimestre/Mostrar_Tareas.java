package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Mostrar_Tareas extends AppCompatActivity {
ManejadorBD manejadorBD;
    ListView lista;
    RadioButton radioButtonRealizadas,radioButtonNorealizadas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__tareas);

        lista=findViewById(R.id.lista);
        manejadorBD=new ManejadorBD(this);
        radioButtonRealizadas=findViewById(R.id.radioButtonRealizadas);
        radioButtonNorealizadas=findViewById(R.id.radioButtonNorealizadas);

        radioButtonRealizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = manejadorBD.listarTareasRealizadas();

                ArrayAdapter<String> adapter;
                List<String> list = new ArrayList<String>();

                if ((cursor != null) && (cursor.getCount() > 0)) {
                    while (cursor.moveToNext()) {
                        String fila = "";
                        fila += "ID: " + cursor.getString(0);
                        fila += " FECHA: " + cursor.getString(1);
                        fila += " DESCRIPCIÓN: " + cursor.getString(2);
                        fila += " ESTADO: " + cursor.getString(3);
                        list.add(fila);

                    }

                    adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                    lista.setAdapter(adapter);
                    cursor.close();
                } else {
                    Toast.makeText(Mostrar_Tareas.this, "Nada que mostarr", Toast.LENGTH_SHORT).show();
                }
            }
        });



        radioButtonNorealizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = manejadorBD.listarTareasNoRealizadas();

                ArrayAdapter<String> adapter;
                List<String> list = new ArrayList<String>();

                if ((cursor != null) && (cursor.getCount() > 0)) {
                    while (cursor.moveToNext()) {
                        String fila = "";
                        fila += "ID: " + cursor.getString(0);
                        fila += " FECHA: " + cursor.getString(1);
                        fila += " DESCRIPCIÓN: " + cursor.getString(2);
                        fila += " ESTADO: " + cursor.getString(3);
                        list.add(fila);

                    }

                    adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                    lista.setAdapter(adapter);
                    cursor.close();
                } else {
                    Toast.makeText(Mostrar_Tareas.this, "Nada que mostarr", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*if(radioButtonRealizadas.isChecked()){
            Cursor cursor = manejadorBD.listarTareasRealizadas();

            ArrayAdapter<String> adapter;
            List<String> list = new ArrayList<String>();

            if ((cursor != null) && (cursor.getCount() > 0)) {
                while (cursor.moveToNext()) {
                    String fila = "";
                    fila += "ID: " + cursor.getString(0);
                    fila += " FECHA: " + cursor.getString(1);
                    fila += " DESCRIPCIÓN: " + cursor.getString(2);
                    fila += " ESTADO: " + cursor.getString(3);
                    list.add(fila);

                }

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                lista.setAdapter(adapter);
                cursor.close();
            } else {
                Toast.makeText(Mostrar_Tareas.this, "Nada que mostarr", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Cursor cursor = manejadorBD.listarTareasNoRealizadas();

            ArrayAdapter<String> adapter;
            List<String> list = new ArrayList<String>();

            if ((cursor != null) && (cursor.getCount() > 0)) {
                while (cursor.moveToNext()) {
                    String fila = "";
                    fila += "ID: " + cursor.getString(0);
                    fila += " FECHA: " + cursor.getString(1);
                    fila += " DESCRIPCIÓN: " + cursor.getString(2);
                    fila += " ESTADO: " + cursor.getString(3);
                    list.add(fila);

                }

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
                lista.setAdapter(adapter);
                cursor.close();
            } else {
                Toast.makeText(Mostrar_Tareas.this, "Nada que mostarr", Toast.LENGTH_SHORT).show();
            }
        }*/
    }
}
