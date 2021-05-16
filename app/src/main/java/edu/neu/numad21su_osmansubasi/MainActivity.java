package edu.neu.numad21su_osmansubasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        aboutBtn= findViewById(R.id.about_btn);
        infoText = findViewById(R.id.info_text);
        aboutBtn.setOnClickListener(v -> infoText.setText(info));

    }


}