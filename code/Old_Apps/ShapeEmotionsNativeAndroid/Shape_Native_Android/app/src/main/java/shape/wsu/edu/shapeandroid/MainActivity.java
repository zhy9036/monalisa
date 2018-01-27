package shape.wsu.edu.shapeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String EMOTION_VALUE = "shape.wsu.edu.shapeandroid.EMOTION";
    public Button happyButton,
                    sadButton,
                    angryButton,
                    scaredButton;

    public void init(){
        happyButton = (Button) findViewById(R.id.happyButton);
        sadButton = (Button) findViewById(R.id.sadButton);
        angryButton = (Button) findViewById(R.id.angryButton);
        scaredButton = (Button) findViewById(R.id.scaredButton);


        angryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OkayMenu.class);
                intent.putExtra(EMOTION_VALUE, "1");
                startActivity(intent);

            }

        });

        happyButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OkayMenu.class);
                intent.putExtra(EMOTION_VALUE, "2");
                startActivity(intent);

            }

        });

        sadButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OkayMenu.class);
                intent.putExtra(EMOTION_VALUE, "3");
                startActivity(intent);

            }

        });

        scaredButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OkayMenu.class);
                intent.putExtra(EMOTION_VALUE, "4");
                startActivity(intent);

            }

        });


//        sadButton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent changeActivity = new Intent(MainActivity.this, OkayMenu.class);
//                //changeActivity.putExtra("Emotion", "mad");
//                startActivity(changeActivity);
//            }
//
//        });
//
//        angryButton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent changeActivity = new Intent(MainActivity.this, OkayMenu.class);
//                //changeActivity.putExtra("Emotion", "happy");
//                startActivity(changeActivity);
//            }
//
//        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
