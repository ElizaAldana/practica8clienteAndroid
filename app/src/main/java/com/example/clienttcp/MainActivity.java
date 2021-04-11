package com.example.clienttcp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Button upBtn, rightBtn, leftBtn, downBtn, fireBtn;
    private BufferedWriter bwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upBtn = findViewById(R.id.upBtn);
        rightBtn = findViewById(R.id.rightBtn);
        leftBtn = findViewById(R.id.leftBtn);
        downBtn = findViewById(R.id.downBtn);
        fireBtn = findViewById(R.id.fireBtn);


        upBtn.setOnTouchListener(this);
        rightBtn.setOnTouchListener(this);
        leftBtn.setOnTouchListener(this);
        downBtn.setOnTouchListener(this);
        fireBtn.setOnTouchListener(this);

        new Thread(
                ()-> {
                    // Ponemos la IP del server y el puerto donde el servidor escucha
                    //Direccion del PC
                    try {
                        Socket socket = new Socket("192.168.0.5", 6000);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        //Adquirimos la referencia
                        bwriter = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Gson gson = new Gson();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                switch(v.getId()){
                    case R.id.upBtn:
                        Coordenada up = new Coordenada("UPSTART");
                        String jsonup = gson.toJson(up);
                        enviar(jsonup);
                        break;
                    case R.id.downBtn:
                        Coordenada down = new Coordenada("DOWNSTART");
                        String jsondown = gson.toJson(down);
                        enviar(jsondown);
                        break;
                    case R.id.rightBtn:
                        Coordenada right = new Coordenada("RIGHTSTART");
                        String jsonright = gson.toJson(right);
                        enviar(jsonright);
                        break;
                    case R.id.leftBtn:
                        Coordenada left = new Coordenada("LEFTSTART");
                        String jsonleft = gson.toJson(left);
                        enviar(jsonleft);
                        break;
                       //Disparo
                    case R.id.fireBtn:
                        Coordenada bala = new Coordenada("FIRE");
                        String bjason = gson.toJson(bala);
                        enviar(bjason);

                }

            case MotionEvent.ACTION_UP:
                switch (v.getId()){
                    case R.id.upBtn:
                        Coordenada upstop = new Coordenada("UPSTOP");
                        String jsonupstop = gson.toJson(upstop);
                        enviar(jsonupstop);
                        break;
                    case R.id.downBtn:
                        Coordenada downstop = new Coordenada("DOWNSTOP");
                        String jsondownstop = gson.toJson(downstop);
                        enviar(jsondownstop);
                        break;
                    case R.id.rightBtn:
                        Coordenada rightstop = new Coordenada("RIGHTSTOP");
                        String jsonrightstop = gson.toJson(rightstop);
                        enviar(jsonrightstop);
                        break;
                    case R.id.leftBtn:
                        Coordenada leftstop = new Coordenada("LEFTSTOP");
                        String jsonleftstop = gson.toJson(leftstop);
                        enviar(jsonleftstop);
                        break;
                }

        }
        return true;
    }

   /* public void onClick(View v){
        Gson gson = new Gson();
        switch(v.getId()){
            case R.id.upBtn:
                Coordenada up = new Coordenada("UP");
                String jsonup = gson.toJson(up);
                enviar(jsonup);
                break;
            case R.id.downBtn:
                Coordenada down = new Coordenada("DOWN");
                String jsondown = gson.toJson(down);
                enviar(jsondown);
                break;
            case R.id.leftBtn:
                Coordenada left = new Coordenada("LEFT");
                String jsonleft = gson.toJson(left);
                enviar(jsonleft);
                break;
            case R.id.rightBtn:
                Coordenada right = new Coordenada("RIGHT");
                String jsonright = gson.toJson(right);
                enviar(jsonright);
                break;
        }
}*/

    public void enviar(String msg){
        new Thread(()-> {
            try {
                bwriter.write(msg+"\n");
                bwriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

