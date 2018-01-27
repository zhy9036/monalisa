package shape.wsu.edu.shapeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DrillDownEmotion extends AppCompatActivity {

    public Button
            levelButton1,
            levelButton2,
            levelButton3;

    public void init(){
        levelButton1 = (Button) findViewById(R.id.level1);
        levelButton2 = (Button) findViewById(R.id.level2);
        levelButton3 = (Button) findViewById(R.id.level3);

        levelButton1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent changeActivity = new Intent(DrillDownEmotion.this, MainActivity.class);
                //changeActivity.putExtra("Level", "1");
                startActivity(changeActivity);
            }

        });

        levelButton2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent changeActivity = new Intent(DrillDownEmotion.this, MainActivity.class);
                //changeActivity.putExtra("Level", "2");
                startActivity(changeActivity);
            }

        });

        levelButton3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent changeActivity = new Intent(DrillDownEmotion.this, MainActivity.class);
                //changeActivity.putExtra("Level", "3");
                startActivity(changeActivity);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_down_emotion);
        init();
    }
}
