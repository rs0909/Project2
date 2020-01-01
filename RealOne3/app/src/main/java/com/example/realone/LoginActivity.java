package com.example.realone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Uri photoUri = null;
    private String name;
    private String phoneNumber;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedPreferences = getSharedPreferences("Info", MODE_PRIVATE);

        Log.d("이름", sharedPreferences.getString("name", "abcd"));

        final TextView nameView = (TextView) findViewById(R.id.nameTextview);
        final TextView phoneNumberview = (TextView) findViewById(R.id.phoneNumberTextView);
        Button signInButton = (Button) findViewById(R.id.signInButton);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameView.getText().toString().equals(sharedPreferences.getString("name", "notFound"))  && phoneNumberview.getText().toString().equals(sharedPreferences.getString("phoneNumber", "notFound"))){
                    setResult(1);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "틀렸다", Toast.LENGTH_LONG).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
