package com.oscardelvo.a2023_ruletabackofficemar;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    Button btnrclock, btnInventario, btnCancelarContrasena, btnEntrarContrasena, btnPrioridad03,
            btnPrioridad02, btnPrioridad01;
    ImageView img, imgLuzA, imgLuzB, imgPremio, imgCambioPrioridades;
    Handler handler, handler3;
    Runnable runnable, runnable3;
    CountDownTimer x;
    String numeroRandom, texto, textoGano, item;
    String strPrioridadJuego = "Igual";
    TextView txtPremioTexto, txtGanasteTexto;
    RelativeLayout rlJuego, rlContrasena;
    EditText txtContrasena;
    MediaPlayer mpJugar, mpR;
    List<String> premioCantidad = new ArrayList<>();
    List<String> premioNombre = new ArrayList<>();
    Random randomGenerator, randomPrioridades;
    int contadorVeces = 0;
    String fileName = "inventario.txt";
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RuletaBack/";
    String TAG = MainActivity.class.getName();

    String strOliveGarden, strJohnnyRockets, strUniversal, strOldNavy;
    String strOliveGardenN, strJohnnyRocketsN, strUniversalN, strOldNavyN;
    int oliveGarden, johnnyRockets, universal, oldNavy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        iniciarVariables();
        countdown();

        btnPrioridad03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCambioPrioridades.setImageResource(R.mipmap.img_baja);
               strPrioridadJuego = "Baja";
                Log.d("PrioridadJuego", strPrioridadJuego+"");
            }
        });

        btnPrioridad02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCambioPrioridades.setImageResource(R.mipmap.img_igual);
                strPrioridadJuego = "Igual";
                Log.d("PrioridadJuego", strPrioridadJuego+"");
            }
        });

        btnPrioridad01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCambioPrioridades.setImageResource(R.mipmap.img_alta);
                strPrioridadJuego = "Alta";
                Log.d("PrioridadJuego", strPrioridadJuego+"");
            }
        });

        btnInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlJuego.setVisibility(View.INVISIBLE);
                rlContrasena.setVisibility(View.VISIBLE);
            }
        });

        btnCancelarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlJuego.setVisibility(View.VISIBLE);
                rlContrasena.setVisibility(View.INVISIBLE);
            }
        });

        btnEntrarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contrasena = txtContrasena.getText().toString();

                if (contrasena.equals("12345")){
                    Intent pasar = new Intent(MainActivity.this, InventarioActivity.class);
                    startActivity(pasar);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnrclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leerTxt();

                mpJugar = MediaPlayer.create(getApplicationContext(),R.raw.game);
                mpJugar.start();

                premioCantidad.clear();
                premioNombre.clear();

                randomPrioridades = new Random();
                int tamanoPrioridad= 10;


                int indexPrioridad = randomPrioridades.nextInt(tamanoPrioridad-0)+0;
                Log.d("PrioridadJuego", strPrioridadJuego+"");
                Log.d("Prioridad", indexPrioridad+"");

                if(strPrioridadJuego.equals("Igual")){
                    if(indexPrioridad > 4){
                        perderSiempre();
                    }else{
                        ganarSiempre();
                    }
                } else if (strPrioridadJuego.equals("Baja")) {
                    if(indexPrioridad > 2){
                        perderSiempre();
                    }else{
                        ganarSiempre();
                    }
                }
                else if (strPrioridadJuego.equals("Alta")) {
                    if(indexPrioridad > 6){
                        perderSiempre();
                    }else{
                        ganarSiempre();
                    }
                }

                Log.d("premioCantidad", premioCantidad+"");
                Log.d("premioNombre", premioNombre+"");


                txtGanasteTexto.setText("");
                txtPremioTexto.setText("");
                btnrclock.setEnabled(false);
                imgPremio.setVisibility(View.INVISIBLE);
                img.setImageResource(R.mipmap.ruleta_girar);
                Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotar);
                img.startAnimation(aniRotate);

                randomGenerator = new Random();
                int tamano;

                tamano = premioNombre.size();

                int index = randomGenerator.nextInt(tamano-0)+0;
                item = premioNombre.get(index);
                numeroRandom = premioCantidad.get(index);

                Log.d("index", index+"");
                Log.d("item", item+"");
                Log.d("tamano", tamano+"");

                if (item.equals("Perdio01")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio01);
                    texto  = "";
                    textoGano = "SUERTE LA PRÓXIMA VEZ";
                }
                else if (item.equals("oldNavy\n")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio02);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    oldNavy--;
                }
                else if (item.equals("universal\n")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio03);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    universal--;
                }
                else if (item.equals("johnnyRockets\n")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio04);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    johnnyRockets--;
                }
                else if (item.equals("oliveGarden\n")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio05);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    oliveGarden--;
                }
                else if (item.equals("Perdio02")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio06);
                    texto  = "";
                    textoGano = "SUERTE LA PRÓXIMA VEZ";
                }
                else if (item.equals("OldNavy")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio07);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    oldNavy--;
                }
                else if (item.equals("Universal")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio08);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    universal--;
                }
                else if (item.equals("JohnnyRockets")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio09);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    johnnyRockets--;
                }
                else if (item.equals("OliveGarden")){
                    imgPremio.setImageResource(R.mipmap.ruleta_premio10);
                    texto = "1 tarjeta de regalo de 10.000";
                    textoGano = "GANASTE";
                    oliveGarden--;
                }

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        mpJugar.stop();

                        mpR = MediaPlayer.create(getApplicationContext(),R.raw.gano);
                        mpR.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mpR.start();
                            }
                        });

                        img.setVisibility(View.INVISIBLE);
                        imgPremio.setVisibility(View.VISIBLE);

                        handler3 = new Handler();
                        runnable3 = new Runnable() {
                            @Override
                            public void run() {
                                txtPremioTexto.setText(texto);
                                txtGanasteTexto.setText(textoGano);
                                btnrclock.setEnabled(true);
                                guardarTxt();

                                if(mpJugar.isPlaying()){
                                    mpJugar.stop();
                                    mpJugar.release();
                                }
                            }
                        };
                        handler3.postDelayed(runnable3,700);
                        if(mpR.isPlaying()){
                            mpR.stop();
                            mpR.release();
                        }

                    }
                };
                handler.postDelayed(runnable,6000);
            }
        });
    }
    public void iniciarVariables(){
        txtPremioTexto = findViewById(R.id.txtPremioTexto);
        txtGanasteTexto = findViewById(R.id.txtGanasteTexto);
        txtContrasena = findViewById(R.id.txtContrasena);
        rlJuego = findViewById(R.id.rlJuego);
        rlContrasena = findViewById(R.id.rlContrasena);
        btnInventario = findViewById(R.id.btnInventario);
        btnCancelarContrasena = findViewById(R.id.btnCancelarContrasena);
        btnrclock = findViewById(R.id.btnRClk);
        btnEntrarContrasena = findViewById(R.id.btnEntrarContrasena);
        btnPrioridad01 = findViewById(R.id.btnPrioridad01);
        btnPrioridad02 = findViewById(R.id.btnPrioridad02);
        btnPrioridad03 = findViewById(R.id.btnPrioridad03);

        imgLuzA = findViewById(R.id.imgAnimA);
        imgLuzB = findViewById(R.id.imgAnimB);
        imgCambioPrioridades = findViewById(R.id.imgCambioPrioridades);

        img = findViewById(R.id.imgvw);
        imgPremio= findViewById(R.id.imgPremio);
        imgPremio.setVisibility(View.INVISIBLE);
    }

    public void countdown(){
        if(x != null){
            x.cancel();
        }
        x = new CountDownTimer(400, 400) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (imgLuzA.getVisibility()== View.VISIBLE){
                    imgLuzA.setVisibility(View.INVISIBLE);
                    imgLuzB.setVisibility(View.VISIBLE);
                }else if (imgLuzA.getVisibility()== View.INVISIBLE){
                    imgLuzA.setVisibility(View.VISIBLE);
                    imgLuzB.setVisibility(View.INVISIBLE);
                }
                x.start();
            }
        };
    }

    public void leerTxt(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path+fileName));

            Log.d("Path", path+ fileName);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linea = bufferedReader.readLine();
            strOliveGardenN = linea + "\n";

            String linea2 = bufferedReader.readLine();
            strOliveGarden = linea2 + "\n";
            oliveGarden = Integer.valueOf(linea2);

            String linea3 = bufferedReader.readLine();
            strJohnnyRocketsN = linea3 + "\n";

            String linea4 = bufferedReader.readLine();
            strJohnnyRockets = linea4 + "\n";
            johnnyRockets = Integer.valueOf(linea4);

            String linea5 = bufferedReader.readLine();
            strUniversalN = linea5 + "\n";

            String linea6 = bufferedReader.readLine();
            strUniversal = linea6 + "\n";
            universal = Integer.valueOf(linea6);

            String linea7 = bufferedReader.readLine();
            strOldNavyN = linea7 + "\n";

            String linea8 = bufferedReader.readLine();
            strOldNavy = linea8 + "\n";
            oldNavy = Integer.valueOf(linea8);

            bufferedReader.close();
            inputStreamReader.close();

        }catch (FileNotFoundException ex){
            Log.d(TAG, ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarTxt(){
        try{
            new File(path).mkdir();
            File file = new File(path+fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(
                    "oliveGarden"
                    + Html.fromHtml("<br />") + oliveGarden
                            + Html.fromHtml("<br />") + "johnnyRockets"
                            + Html.fromHtml("<br />") + johnnyRockets
                            + Html.fromHtml("<br />") + "universal"
                            + Html.fromHtml("<br />") + universal
                            + Html.fromHtml("<br />") + "oldNavy"
                            + Html.fromHtml("<br />") + oldNavy
            );
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (FileNotFoundException ex){
            Log.d(TAG, ex.getMessage());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ganarSiempre(){

        if(oliveGarden==0 && johnnyRockets==0 && universal==0 && oldNavy==0){
            premioCantidad.add(String.valueOf(100));
            premioNombre.add(String.valueOf("Perdio01"));
        }else {
            if (oliveGarden == 0) {
            } else {
                premioCantidad.add(String.valueOf(oliveGarden));
                premioNombre.add(String.valueOf(strOliveGardenN));

                premioCantidad.add(String.valueOf(100));
                premioNombre.add(String.valueOf("OliveGarden"));
            }

            if (johnnyRockets == 0) {
            } else {
                premioCantidad.add(String.valueOf(johnnyRockets));
                premioNombre.add(String.valueOf(strJohnnyRocketsN));

                premioCantidad.add(String.valueOf(100));
                premioNombre.add(String.valueOf("JohnnyRockets"));
            }

            if (universal == 0) {
            } else {
                premioCantidad.add(String.valueOf(universal));
                premioNombre.add(String.valueOf(strUniversalN));

                premioCantidad.add(String.valueOf(100));
                premioNombre.add(String.valueOf("Universal"));
            }

            if (oldNavy == 0) {
            } else {
                premioCantidad.add(String.valueOf(oldNavy));
                premioNombre.add(String.valueOf(strOldNavyN));

                premioCantidad.add(String.valueOf(100));
                premioNombre.add(String.valueOf("OldNavy"));
            }
        }

    }

    public void perderSiempre(){
        premioCantidad.add(String.valueOf(100));
        premioNombre.add(String.valueOf("Perdio01"));

        premioCantidad.add(String.valueOf(100));
        premioNombre.add(String.valueOf("Perdio02"));
    }
}