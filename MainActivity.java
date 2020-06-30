package com.example.zaika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Porder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Porder = (Button) findViewById(R.id.Porder);

        Porder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensecActivity();

            }
        });
    }

    public void opensecActivity(){
        Intent intent = new Intent(this, Sec2.class);
        startActivity(intent);

    }
}
