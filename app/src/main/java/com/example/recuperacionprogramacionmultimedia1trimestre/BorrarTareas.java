package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BorrarTareas extends AppCompatActivity {

    EditText editTextBorrarTarea;
    Button buttonBorrarTarea;
    ManejadorBD manejadorBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_tareas);

        editTextBorrarTarea=findViewById(R.id.editTextIdTareaBorrar);
        buttonBorrarTarea=findViewById(R.id.buttonBorrarTarea);
        manejadorBD=new ManejadorBD(this);
        buttonBorrarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=manejadorBD.borrar(editTextBorrarTarea.getText().toString());
                if (res==true){
                    Toast.makeText(getApplicationContext(),"Tarea Borrada correctamente",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Tarea no borrada",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
