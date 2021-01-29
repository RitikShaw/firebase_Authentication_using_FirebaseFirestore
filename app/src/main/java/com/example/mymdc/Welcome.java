package com.example.mymdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Welcome extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String UserId;

    TextView name_view, email_view,id_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        name_view = (TextView)findViewById(R.id.Welcome_Name);
        email_view = (TextView)findViewById(R.id.Welcome_Email);
        id_view = (TextView)findViewById(R.id.Welcome_UserId);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        UserId = mAuth.getCurrentUser().getUid();

        DocumentReference reference = firestore.collection("users").document(UserId);

        reference.addSnapshotListener(Welcome.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()){

                    name_view.setText(documentSnapshot.getString("name"));
                    email_view.setText(documentSnapshot.getString("email"));
                    id_view.setText(documentSnapshot.getId().toString());

                }
            }
        });

    }
}
