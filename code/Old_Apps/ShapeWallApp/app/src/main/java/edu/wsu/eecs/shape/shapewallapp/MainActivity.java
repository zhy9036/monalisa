package edu.wsu.eecs.shape.shapewallapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendData();
    }


    protected void sendData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String xmlString = "<xml><x>10</x><y>15</y><width>20</width><height>50</height></xml>";
                String urlString = "http://pullmanwater.com/shapetemp/recieveData.php?value=" + xmlString;

                try {
                    URL url = new URL(urlString);
                    InputStream response = url.openStream();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
