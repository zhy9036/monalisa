package edu.wsu.eecs.shape.shapewallapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static SeekBar seekBarX;
    private static TextView textViewX;

    private static SeekBar seekBarY;
    private static TextView textViewY;

    private static SeekBar seekBarZ;
    private static TextView textViewZ;

    public static int xCoordinate = 0;
    public static int yCoordinate = 0;
    public static int height = 0;
    public static int width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeSeekBarX();
        changeSeekBarY();
        //changeSeekBarZ();
        //URLConnection

        final TextView textView = (TextView)findViewById(R.id.xyTextView);
        // this is the view on which you will listen for touch events
        final View touchView = findViewById(R.id.touchView);
        final TextView touchViewsText = (TextView)findViewById(R.id.touchView);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xCoordinate = (int)event.getX();
                yCoordinate = (int)event.getY();
                textView.setText("X: " +
                        String.valueOf(xCoordinate) + "\nY: " + String.valueOf(yCoordinate));
                touchViewsText.setText("");
                return true;
            }
        });


        // Submit Button, where the web server logic should go
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Perform action on click
                String message = "X: " + xCoordinate + "\nY: " + yCoordinate + "\nHeight: " + height + "\nWidth: " + width;
                Toast.makeText(MainActivity.this, message , Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String xmlString = "<xml><x>" + Integer.toString(xCoordinate) + "</x><y>"
                                + Integer.toString(yCoordinate) + "</y><width>"
                                + Integer.toString(width) + "</width><height>"
                                + Integer.toString(height) + "</height></xml>";

                        String urlString = "http://shapetest.loganstrong.com/recieveData.php?value=" + xmlString;

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
        });
    }

    public void changeSeekBarX(){
        seekBarX = (SeekBar)findViewById(R.id.seekBar2);
        textViewX = (TextView)findViewById(R.id.x_text);

        seekBarX.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        width = progress;
                        textViewX.setText("Width: " + width);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewX.setText("Width: " + width/*seekBar.getProgress()*/);
                    }
                }
        );
    }

    public void changeSeekBarY(){
        seekBarY = (SeekBar)findViewById(R.id.seekBar);
        textViewY = (TextView)findViewById(R.id.y_text);

        seekBarY.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        height = progress;
                        textViewY.setText("Height: " + height);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewY.setText("Height: " + height/*seekBar.getProgress()*/);
                    }
                }
        );
    }

//    public void changeSeekBarZ(){
//        seekBarZ = (SeekBar)findViewById(R.id.seekBar3);
//        textViewZ = (TextView)findViewById(R.id.z_text);
//
//        seekBarZ.setOnSeekBarChangeListener(
//                new SeekBar.OnSeekBarChangeListener() {
//                    private int progressValue;
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        progressValue = progress;
//                        textViewZ.setText("Z: " + progressValue);
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        textViewZ.setText("Z: " + progressValue/*seekBar.getProgress()*/);
//                    }
//                }
//        );
//    }





}
