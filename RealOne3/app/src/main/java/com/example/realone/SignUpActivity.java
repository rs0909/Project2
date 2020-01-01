package com.example.realone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreference;
    private Uri photoUri = null;
    private String name = null;
    private String phoneNumber = null;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;
        final TextView nameTextView = (TextView) findViewById(R.id.textViewForName);
        final TextView phoneTextView = (TextView) findViewById(R.id.textViewForPhone);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 900);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoUri == null || nameTextView.getText() == null || phoneTextView.getText() == null || checkExist(phoneTextView.getText().toString())){
                    Toast.makeText(context,"다시 해와", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = sharedPreference.edit();
                    editor.putString("name", nameTextView.getText().toString());
                    editor.putString("phoneNumber", phoneTextView.getText().toString());
                    editor.putString("uri", photoUri.toString());
                    editor.commit();
                    Toast.makeText(context, "가입됬다 다시 로그인해라", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public boolean checkExist(String phoneNumber){
        sharedPreference = getSharedPreferences("Info", MODE_PRIVATE);
        if(phoneNumber != null && phoneNumber.equals(sharedPreference.getString("phoneNumber", "notFound"))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 900){
            if(resultCode == RESULT_OK){
                photoUri = data.getData();
                imageButton.setImageURI(photoUri);
            }
        }
    }
}
