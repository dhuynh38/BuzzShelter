package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.buzzshelter.Model.AccountType;
import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.User;
import com.example.tonyzhang.buzzshelter.R;

/**
 * Registration Activity
 */
public class RegistrationActivity extends AppCompatActivity {


    private EditText name;
    private EditText id;
    private EditText password;
    private Spinner accountType;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button cancel_button = findViewById(R.id.registration_cancel_button);
        Button register_button = findViewById(R.id.registration_register_button);
        name = findViewById(R.id.registration_name_field);
        id = findViewById(R.id.registration_id_field);
        password = findViewById(R.id.registration_password_field);
        accountType = findViewById(R.id.registration_accountType_spinner);

        ArrayAdapter<AccountType> accountAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, AccountType.values());
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType.setAdapter(accountAdapter);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringId = id.getText().toString();
                String stringName = name.getText().toString();
                String stringPassword = password.getText().toString();
                AccountType accountTypeAccountType = (AccountType) accountType.getSelectedItem();

                user = new User(stringId, stringName, stringPassword, accountTypeAccountType);
                if (!Model.getInstance().addUser(user, getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),
                            "Failure: Either field is null OR id is taken",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //was successful login
                    Toast.makeText(getApplicationContext(),
                            "Registration SUCCESSFULL!!!!!!!!!1!!!1!!1111!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
