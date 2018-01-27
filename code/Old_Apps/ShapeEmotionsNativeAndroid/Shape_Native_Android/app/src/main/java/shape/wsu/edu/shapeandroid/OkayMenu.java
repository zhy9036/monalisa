package shape.wsu.edu.shapeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class OkayMenu extends AppCompatActivity {

    public Button okayButton, revertButton;
    public TextView confirmMessageTextView;
    public String emotionValue;

    public void init(){

        okayButton = (Button) findViewById(R.id.okayButton);
        revertButton = (Button) findViewById(R.id.revertButton);

        okayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent changeActivity = new Intent(OkayMenu.this, MainActivity.class);
                final int emotion = Integer.parseInt(emotionValue);
                try {
                    postToSHAPE(emotion);
                } catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(changeActivity);
            }
        });

        revertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent changeActivity = new Intent(OkayMenu.this, MainActivity.class);
                startActivity(changeActivity);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okay_menu);

        // This array should be based on the options that we have given the user.
        // STRANGE is option 0, which does not currently exist.
        // Basically this code is pretty hacky so we probably want to change it in the future.
        String[] emotions = {"STRANGE", "Angry", "Happy", "Sad", "Scared"};

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        emotionValue = intent.getStringExtra(MainActivity.EMOTION_VALUE);

        confirmMessageTextView = (TextView) findViewById(R.id.confirmMessage);
        int emotion = Integer.parseInt(emotionValue);
        confirmMessageTextView.setText("Are You Sure That You Are " + emotions[emotion]+ "?");

        init();
    }

    private void writeStream(OutputStream out, JSONObject emotion) throws IOException {

        String output = emotion.toString();
        out.write(output.getBytes());
        out.flush();
    }

    private void postToSHAPE(final int emotion) throws IOException {
        //URL url = new URL("http://andrewlewis.pythonanywhere.com/currentWall/");
        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject wall = new JSONObject();
                try {
                    wall.put("currentWall", emotion);
                    System.out.println("json: " + wall.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //String wallParameters  = wall.toString();

//                URL url = new URL("http://www.android.com/");

                // Sources:
                //
                // https://stackoverflow.com/questions/31611480/how-do-i-post-in-java-to-a-server
                try {
                    String IPPORT = "andrewlewis.pythonanywhere.com/currentWall";
                    URL url = new URL("http://"+IPPORT+"/");

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setDoOutput(true);
                        urlConnection.setChunkedStreamingMode(0);

                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        writeStream(out, wall);

//                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                        readStream(in);
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }


                




//                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
//                int    postDataLength = postData.length;

//                byte[] out = urlParameters.getBytes(StandardCharsets.UTF_8);
//                int length = out.length;
//                try {
//                    URL url = new URL("http://andrewlewis.pythonanywhere.com/currentWall/");
//                    URLConnection con = url.openConnection();
//                    HttpURLConnection http = (HttpURLConnection)con;
//                    http.setRequestMethod("POST"); // PUT is another valid option
//                    http.setDoOutput(true);
//
//                    http.setFixedLengthStreamingMode(length);
//                    http.setRequestProperty("Content-Type", "application/json");
//                    http.connect();
//                    try(OutputStream os = http.getOutputStream()) {
//                        os.write(out);
//                    }
//
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }

//                try {
//
//                    //URL url = new URL("http://andrewlewis.pythonanywhere.com/currentWall/");
//
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//
//                    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));
//
//                    writer.write(wallParameters);
//
//                    writer.flush();
//
//                    writer.close();
//
//                    out.close();
//
//                    urlConnection.connect();
//
//
//                } catch (Exception e) {
//
//                    System.out.println(e.getMessage());
//                }


                String request        = "http://andrewlewis.pythonanywhere.com/currentWall/";

//                try {
//                    URL url = new URL(request);
//                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//                    conn.setDoOutput( true );
//                    conn.setInstanceFollowRedirects( false );
//                    conn.setRequestMethod( "POST" );
//                    conn.setRequestProperty( "Content-Type", "application/json");
//                    conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
//                    conn.setUseCaches( false );
//                    try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//                        wr.write( postData );
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }

//                try {
//                    URL url = new URL(urlString);
//                    InputStream response = url.openStream();
//                }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                }
            }
        }).start();
    }
}
