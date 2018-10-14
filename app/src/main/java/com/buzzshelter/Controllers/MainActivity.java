package com.buzzshelter.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tonyzhang.buzzshelter.R;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logout = findViewById(R.id.logout);

        Button enterShelter = findViewById(R.id.enterShelter);

        Button viewShelter = findViewById(R.id.viewShelter);

        viewShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ViewShelterActivity.class);
                startActivity(intent);
            }
        });
        enterShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EnterShelterActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }




}
