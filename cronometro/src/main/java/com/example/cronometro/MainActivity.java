package com.example.cronometro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int min=0,seg=0,mili=0;
    Thread hilo;
    Boolean encendido = false;

    public void iniciarCrono(View view) {
        Button iniciar = (Button)findViewById(R.id.iniciar);
        Button reiniciar = (Button)findViewById(R.id.buttonReiniciar);

        if (iniciar.getText().equals("Iniciar"))
        {
            //-----------------------INICIAMOS EL CRONO
            encendido = true;
            hiloCrono(view);

            iniciar.setText("Parar");
        }
        else
        {
            //-----------------------PARAMOS EL CRONO
            encendido = false;

            reiniciar.setEnabled(true);
            iniciar.setEnabled(false);
        }

    }

    public void reiniciarCrono(View view)
    {
        TextView crono = (TextView)findViewById(R.id.crono);

        Button iniciar = (Button)findViewById(R.id.iniciar);
        Button reiniciar = (Button)findViewById(R.id.buttonReiniciar);

        //-----------------------INICIAMOS EL CRONO
        encendido = true;

        min=0;
        seg=0;
        mili=0;
        crono.setText("00:00:000");

        hiloCrono(view);
        iniciar.setEnabled(true); //text en modo PARAR
        reiniciar.setEnabled(false);
    }

    Handler h = new Handler(); // sirve para modificar el textview

    private void hiloCrono(View view) {
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){ // while infinito
                    if(encendido){ // se activa la variable encendido si se presiona el boton iniciar
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        mili++;
                        if(mili >= 999){
                            seg++;
                            mili=0;
                        }if(seg>=59){
                            min++;
                            seg=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView crono = (TextView)findViewById(R.id.crono);
                                String m="",s="",mi="";
                                if(mili<10){ //Modificar la variacion de los 0
                                    m="00"+mili;
                                }else if (mili<=100){
                                    m="0"+mili;
                                }else{
                                    m=""+mili;
                                }
                                if (seg<=10){
                                    s="0"+seg;
                                }else{
                                    s=""+seg;
                                }
                                if(min<=10){
                                    mi="0"+min;
                                }else{
                                    mi=""+min;
                                }
                                crono.setText(mi+":"+s+":"+m);
                            }
                        });
                    }
                }
            }
        });
        hilo.start();
    }

}
