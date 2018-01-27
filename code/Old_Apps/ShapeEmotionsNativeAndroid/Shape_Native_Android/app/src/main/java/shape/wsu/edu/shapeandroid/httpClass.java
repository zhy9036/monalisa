package shape.wsu.edu.shapeandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class httpClass extends
        AsyncTask<Void, Void, Boolean> {

    String urlString = "http://www.andrewlewis.pythonanywhere.com/currentWall/";

    private final String TAG = "post json example";
    private Context context;

    private int wallConfig;

    public httpClass(int wallConfig) {

        this.wallConfig = wallConfig;
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "1 - RequestVoteTask is about to start...");

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean status = false;

        String response = "";
        Log.e(TAG, "2 - pre Request to response...");

        try {
            response = performPostCall(urlString, new HashMap<String, String>() {

                private static final long serialVersionUID = 1L;

                {
                    put("Accept", "application/json");
                    put("Content-Type", "application/json");
                }
            });
        } catch (Exception e) {

        }
        Log.e(TAG, "5 - after Response...");

        if (!response.equalsIgnoreCase("")) {
            try {
                //
                JSONObject jRoot = new JSONObject(response);
                JSONObject d = jRoot.getJSONObject("d");

                int ResultType = d.getInt("ResultType");
                Log.e("ResultType", ResultType + "");

                if (ResultType == 1) {

                    status = true;

                }

            } catch (JSONException e) {
                // displayLoding(false);
                // e.printStackTrace();
                Log.e(TAG, "Error " + e.getMessage());
            } finally {

            }
        } else {
            Log.e(TAG, "6 - response is empty...");

            status = false;
        }

        return status;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //
        Log.e(TAG, "7 - onPostExecute ...");

        if (result) {
            Log.e(TAG, "8 - Update UI ...");

            // setUpdateUI(adv);
        } else {
            Log.e(TAG, "8 - Finish ...");

            // displayLoding(false);
            // finish();
        }

    }

    public String performPostCall(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            Log.e(TAG, "11 - url : " + requestURL);

            /*
             * JSON
             */

            JSONObject root = new JSONObject();
            //

            root.put("currentWall", this.wallConfig);


            String str = root.toString();
            byte[] outputBytes = str.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}