package edu.neu.numad21su_osmansubasi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PressMeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_me);
    }

    public void onClick(View view){
        TextView pressedText = findViewById(R.id.pressText);
        String press = "Pressed:";
        switch (view.getId()) {
            case R.id.btnA:
                press += " A";
                pressedText.setText(press);
                break;
            case R.id.btnB:
                press += " B";
                pressedText.setText(press);
                break;
            case R.id.btnC:
                press += " C";
                pressedText.setText(press);
                break;
            case R.id.btnD:
                press += " D";
                pressedText.setText(press);
                break;
            case R.id.btnE:
                press += " E";
                pressedText.setText(press);
                break;
            case R.id.btnF:
                press += " F";
                pressedText.setText(press);
                break;
        }
    }
}