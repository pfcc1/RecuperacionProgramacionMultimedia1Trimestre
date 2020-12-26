package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agregar_Tarea extends AppCompatActivity {

    Button buttonInsertar;
    EditText editTextDescripcion;
    ManejadorBD manejadorBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__tarea);
        manejadorBD = new ManejadorBD(this);
        buttonInsertar=findViewById(R.id.buttonInsertar);
        editTextDescripcion=findViewById(R.id.editTextDescripcion);

        buttonInsertar.setOnClickListener(new View.OnClickListener() {
           String estado="No realizada";
            Date date=new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            //System.out.println("Hora y fecha: "+hourdateFormat.format(date));

            @Override
            public void onClick(View v) {
                boolean resultado = manejadorBD.insertar(hourdateFormat.format(date),editTextDescripcion.getText().toString(),estado);

                if (resultado) {
                    Toast.makeText(Agregar_Tarea.this, "Insertado registro correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Agregar_Tarea.this, "Hubo un error en la inserción. ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
