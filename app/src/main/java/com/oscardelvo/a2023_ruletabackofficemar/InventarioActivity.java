package com.oscardelvo.a2023_ruletabackofficemar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InventarioActivity extends Activity {
    Button btnCancelarInv,btnGuardarInv;

    EditText txtInvOliveGarden,txtInvJohnnyRockets,txtInvOldNavy,txtInvUniversal;

    final static String fileName = "inventario.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RuletaBack/" ;
    final static String TAG = MainActivity.class.getName();

    String strOliveGarden, strJohnnyRockets, strUniversal, strOldNavy;
    int oliveGarden, johnnyRockets, universal, oldNavy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventario);

        btnCancelarInv= findViewById(R.id.btnCancelarInv);
        btnGuardarInv= findViewById(R.id.btnGuardarInv);

        txtInvUniversal= findViewById(R.id.txtInvUniversal);
        txtInvOldNavy= findViewById(R.id.txtInvOldNavy);
        txtInvJohnnyRockets= findViewById(R.id.txtInvJohnnyRockets);
        txtInvOliveGarden= findViewById(R.id.txtInvOliveGarden);

        leerTxt();

        btnGuardarInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTxt();

            }
        });

        btnCancelarInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irJuego();
            }
        });
    }


    public void leerTxt(){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName));

            Log.d("path", path + fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // TODO: NOMBRE ENTRADA
            String linea = bufferedReader.readLine();
            // TODO: CANT ENTRADA
            String linea2 = bufferedReader.readLine();
            strOliveGarden = linea2 + "\n";
            oliveGarden = Integer.valueOf(linea2);
            Log.d("todo2", strOliveGarden);

            // TODO: NOMBRE GINTONIC
            String linea3 = bufferedReader.readLine();
            // TODO: CANT GINTONIC
            String linea4 = bufferedReader.readLine();
            strJohnnyRockets = linea4 + "\n";
            johnnyRockets = Integer.valueOf(linea4);

            // TODO: NOMBRE CORTE01
            String linea5 = bufferedReader.readLine();
            // TODO: CANT CORTE01
            String linea6 = bufferedReader.readLine();
            strUniversal = linea6 + "\n";
            universal = Integer.valueOf(linea6);

            // TODO: NOMBRE SORPRESA
            String linea7 = bufferedReader.readLine();
            // TODO: CANT SORPRESA
            String linea8 = bufferedReader.readLine();
            strOldNavy = linea8 + "\n";
            oldNavy = Integer.valueOf(linea8);


            bufferedReader.close();
            inputStreamReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }


        txtInvOliveGarden.setText(strOliveGarden.trim());
        txtInvJohnnyRockets.setText(strJohnnyRockets.trim());
        txtInvUniversal.setText(strUniversal.trim());
        txtInvOldNavy.setText(strOldNavy.trim());

    }

    public void guardarTxt(){

        String NuevastrOliveGarden = txtInvOliveGarden.getText().toString().trim();
        String NuevastrJohnnyRockets= txtInvJohnnyRockets.getText().toString().trim();
        String NuevastrUniversal = txtInvUniversal.getText().toString().trim();
        String NuevastrOldNavy= txtInvOldNavy.getText().toString().trim();


        if (isValidMobile(NuevastrOliveGarden) &&isValidMobile(NuevastrJohnnyRockets)
                &&isValidMobile(NuevastrUniversal) && isValidMobile(NuevastrOldNavy)) {

            txtInvOliveGarden.setError(null);
            txtInvJohnnyRockets.setError(null);
            txtInvOldNavy.setError(null);
            txtInvUniversal.setError(null);

            try {
                new File(path  ).mkdir();
                File file = new File(path+ fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
                osw.write("oliveGarden"
                        + Html.fromHtml("<br />") + NuevastrOliveGarden
                        + Html.fromHtml("<br />") + "johnnyRockets"
                        + Html.fromHtml("<br />") + NuevastrJohnnyRockets
                        + Html.fromHtml("<br />") + "universal"
                        + Html.fromHtml("<br />") + NuevastrUniversal
                        + Html.fromHtml("<br />") + "oldNavy"
                        + Html.fromHtml("<br />") + NuevastrOldNavy
                );

                osw.flush();
                osw.close();
                Toast.makeText(this, "Los datos fueron grabados correctamente", Toast.LENGTH_SHORT).show();
            }  catch(FileNotFoundException ex) {
                Log.d(TAG, ex.getMessage());
            }  catch(IOException ex) {
                Log.d(TAG, ex.getMessage());
            }
            irJuego();
        }

        else if (!isValidMobile(NuevastrOliveGarden)) {
            txtInvOliveGarden.setError("Debe llenar el espacio");
            txtInvOliveGarden.requestFocus();
        }
        else if (!isValidMobile(NuevastrJohnnyRockets)) {
            txtInvJohnnyRockets.setError("Debe llenar el espacio");
            txtInvJohnnyRockets.requestFocus();
        }
        else if (!isValidMobile(NuevastrUniversal)) {
            txtInvUniversal.setError("Debe llenar el espacio");
            txtInvUniversal.requestFocus();
        }
        else if (!isValidMobile(NuevastrOldNavy)) {
            txtInvOldNavy.setError("Debe llenar el espacio");
            txtInvOldNavy.requestFocus();
        }
    }

    public void irJuego(){
        Intent cat = new Intent(InventarioActivity.this, MainActivity.class);
        startActivity(cat);
        finish();
    }

    private boolean isValidMobile(String phone2) {
        boolean check = false;
        if (phone2.length() > 0) {
            check = true;
        }
        return check;
    }

}