package com.example.recuperacionprogramacionmultimedia1trimestre;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int SELECCIONAR_IMAGEN=0;
    private static final int PETICION_CAMARA = 1;
    private static final int VENGO_DE_LA_CAMARA_CON_FICHERO = 2;
    private static final int PEDI_PERMISO_DE_ESCRITURA = 3;
    private static final String FOTOAGUARDAR="FOTOAGUARDAR";
    Button buttonAccesoMenu,buttonHacerFoto,buttonGaleria;
    ImageView imageView;
  public Uri mImageUri;
    File fichero = null;

    EditText editTextNick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        editTextNick=findViewById(R.id.editTextNick);
        buttonHacerFoto=findViewById(R.id.buttonCamara);
        buttonGaleria=findViewById(R.id.buttonGaleria);

        imageView=findViewById(R.id.imageView);


        if(editTextNick.getText().toString()!=""){
            SharedPreferences prefe=getSharedPreferences("datosnick",Context.MODE_PRIVATE);
            String valor=prefe.getString("nombre","");
            Toast.makeText(getApplicationContext(),"Hola "+valor,Toast.LENGTH_SHORT).show();
        }

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preference.getString("image", null);

        if (mImageUri != null) {
            imageView.setImageURI(Uri.parse(mImageUri));
        }/* else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        */

        SharedPreferences preferencamara = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUricamara = preferencamara.getString("imagencamara", null);

        if(mImageUricamara!=null){
            imageView.setImageURI(Uri.parse(mImageUricamara));
        }

        buttonHacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermisoParaEscribirYhacerFoto();

                guardarnick();

            }
        });

        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seleccionarimagengaleria();
                guardarnick();

            }
        });

        buttonAccesoMenu=findViewById(R.id.buttonAccesoMenu);
        buttonAccesoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenu=new Intent(getApplicationContext(),Menu.class);
                startActivity(intentMenu);
            }
        });
    }

    public void guardarnick(){


        SharedPreferences prefenick=getSharedPreferences("datosnick", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefenick.edit();
        editor.putString("nombre", editTextNick.getText().toString());
        editor.commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PETICION_CAMARA && resultCode == RESULT_OK ) {

            Bitmap foto = (Bitmap) data.getExtras().get("data"); //es una imagen previa a baja resolución
            imageView.setImageBitmap(foto);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));


        } else if (requestCode == VENGO_DE_LA_CAMARA_CON_FICHERO && resultCode == RESULT_OK) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(fichero.getAbsolutePath()));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", fichero.getAbsolutePath());
            editor.commit();


        }
        else if(requestCode==SELECCIONAR_IMAGEN && resultCode==RESULT_OK && data!=null){


            // This is the key line item, URI specifies the name of the data
            mImageUri = data.getData();
            //System.out.println("URI "+mImageUri);
            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", String.valueOf(mImageUri));
            editor.commit();

            // Sets the ImageView with the Image URI
            imageView.setImageURI(mImageUri);
            imageView.invalidate();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PEDI_PERMISO_DE_ESCRITURA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    capturarFoto();


                } else {
                    Toast.makeText(this, "Sin permiso de escritura no puedo hacer foto en alta resolución.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    void pedirPermisoParaEscribirYhacerFoto() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PEDI_PERMISO_DE_ESCRITURA);
            }
        }else{
            capturarFoto();

        }

    }

    private void capturarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        try {
            fichero = crearFicheroImagen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fichero));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, VENGO_DE_LA_CAMARA_CON_FICHERO);
        } else {
            Toast.makeText(this, "No tengo programa para hacer fotos.", Toast.LENGTH_SHORT).show();
        }
    }

    private File crearFicheroImagen() throws IOException {
        Calendar calendar =Calendar.getInstance();
        String fechaYHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(calendar.getTime());
        String nombreFichero = "misFotos_" + fechaYHora;
        File carpetaDeFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreFichero, ".jpg", carpetaDeFotos);
        return imagen;

    }

    public void seleccionarimagengaleria() {
        verificarpermisoimagenesgaleria();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECCIONAR_IMAGEN);
    }

    public void verificarpermisoimagenesgaleria() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
            return;
        }
    }

}
