package com.example.mymdc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView headline, register_text, forgot_text;
    EditText email_text, password_text;
    Button login_button;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headline = (TextView)findViewById(R.id.MDC_Welcome_headline);
        headline.setOnClickListener(this);

        email_text = (EditText)findViewById(R.id.Main_Email);
        password_text = (EditText)findViewById(R.id.Main_Password);

        login_button = (Button)findViewById(R.id.Main_btn_Login);
        login_button.setOnClickListener(this);

        forgot_text = (TextView)findViewById(R.id.Main_Forgot);
        forgot_text.setOnClickListener(this);

        register_text = (TextView)findViewById(R.id.Main_Register);
        register_text.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.Main_Register:

                startActivity(new Intent(MainActivity.this,Register.class));
                break;

            case R.id.Main_btn_Login:

                String Login_Email = email_text.getText().toString();
                String Login_Password = password_text.getText().toString();

                mAuth.signInWithEmailAndPassword(Login_Email,Login_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this,Welcome.class));
                        }

                        else {

                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });
        }

    }
}
