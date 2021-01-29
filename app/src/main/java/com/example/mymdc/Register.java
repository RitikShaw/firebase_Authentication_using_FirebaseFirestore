package com.example.mymdc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    String UserId;


    EditText name_text, email_text, password_text, conpass_text;
    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_text = (EditText)findViewById(R.id.Register_Name);
        email_text= (EditText)findViewById(R.id.Register_Email);
        password_text = (EditText)findViewById(R.id.Register_Password);
        conpass_text = (EditText)findViewById(R.id.Register_Conpass);

        register_button = (Button)findViewById(R.id.Register_Button);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseDatabase.getInstance().getReference("Users");

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();

            }
        });

    }

    private void registerUser() {

        final String Email = email_text.getText().toString().trim();
        final String Name = name_text.getText().toString().trim();
        String Password = password_text.getText().toString().trim();
        String Conpass = conpass_text.getText().toString().trim();

        if (Name.isEmpty()){

            name_text.setError("Provide name");
            name_text.requestFocus();
            return;

        }

        if (Email.isEmpty()){

            email_text.setError("Provide email");
            email_text.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){

            email_text.setError("Provide valid id");
            email_text.requestFocus();
            return;

        }

        if (Password.isEmpty() || Password.length()<6){

            password_text.setError("password must be of 6 characters");
            password_text.requestFocus();
            return;

        }

        if (!Conpass.equals(Password)){

            conpass_text.setError("provide same password");
            conpass_text.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            UserId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users")
                                    .document(UserId);

                            Map<String,Object> map_user = new HashMap<>();
                            map_user.put("name",Name);
                            map_user.put("email",Email);

                            documentReference.set(map_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"success",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(Register.this,"Failed",Toast.LENGTH_LONG).show();
                                }
                            });




                        }

                        else {

                            Toast.makeText(Register.this,"Failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}