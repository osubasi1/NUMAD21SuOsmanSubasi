package edu.neu.numad21su_osmansubasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button aboutBtn;
    TextView infoText;
    String email = "subasi.o@northeastern.edu";
    String name = "Osman Subasi";
    String info = name + "\n" + email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Week one
        aboutBtn= findViewById(R.id.about_btn);
        infoText = findViewById(R.id.info_text);
        aboutBtn.setOnClickListener(v -> infoText.setText(info));

        // Week two
        Button pressMe = findViewById(R.id.pressMeBtn);
        pressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activityToIntent = new Intent(getApplicationContext(), PressMeActivity.class);
                startActivity(activityToIntent);
            }
        });

        // Week 4
        Button linkCollector = findViewById(R.id.linkCollector);
        linkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent linkCollectorIntent = new Intent(getApplicationContext(),
                        LinkCollectorActivity.class);
                startActivity(linkCollectorIntent);
            }
        });

        // Week 5 - Sensors
        Button locationBtn = findViewById(R.id.locationBtn);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(getApplicationContext(),
                       LocationActivity.class);
                startActivity(locationIntent);
            }
        });

    }



}