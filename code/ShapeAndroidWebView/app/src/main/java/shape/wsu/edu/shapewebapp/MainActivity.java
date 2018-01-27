package shape.wsu.edu.shapewebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById returns an instance of View ,which is casted to target class
        WebView webView= (WebView)findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        //This statement is used to enable the execution of JavaScript.
        webView.getSettings().setJavaScriptEnabled(true);

        //This statement hides the Vertical scroll bar and does not remove it.
        webView.setVerticalScrollBarEnabled(false);

        //This statement hides the Horizontal scroll bar and does not remove it.
        webView.setHorizontalScrollBarEnabled(false);

        //This statement is used to load the web page that our mobile app is running on
        webView.loadUrl("http://andrewlewis.pythonanywhere.com/static/shapemobile");
    }
}
