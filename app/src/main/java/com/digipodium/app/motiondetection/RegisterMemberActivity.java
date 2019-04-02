package com.digipodium.app.motiondetection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterMemberActivity extends AppCompatActivity {

        private EditText email1;
        private FirebaseAuth auth;
        private TextView statusText;
        private EditText email;
        private EditText user;
        private EditText pass;
        private EditText pass1;
        private Button btn;
        private ViewGroup wrapper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member);

            email = findViewById(R.id.email);
            user = findViewById(R.id.username);
            pass = findViewById(R.id.password);
            pass1 = findViewById(R.id.cpassword);
            statusText = findViewById(R.id.statusText);
            btn = findViewById(R.id.reg);
            wrapper = findViewById(R.id.wrapper);
            wrapper.setVisibility(View.GONE);
            auth = FirebaseAuth.getInstance();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user1 = user.getText().toString();
                    String password = pass.getText().toString();
                    String email1 = email.getText().toString();
                    String conpass = pass1.getText().toString();
                    if (user1.isEmpty()) {
                        user.setError("cannot be empty");
                        return;
                    }
                    if (email1.isEmpty()) {
                        email.setError("cannot be empty");
                        return;
                    }
                    if (password.isEmpty()) {
                        pass.setError("cannot be empty");
                        return;
                    }
                    if (!(password.equals(conpass))) {
                        pass1.setError("invalid");
                        return;
                    }
                    wrapper.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email1, password).addOnCompleteListener(com.digipodium.app.motiondetection.RegisterMemberActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                statusText.setText("Success");
                                statusText.setTextColor(Color.GREEN);
                                updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(com.digipodium.app.motiondetection.RegisterMemberActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                statusText.setText(task.getException().getMessage());
                                statusText.setTextColor(Color.RED);
                                updateUI(null);
                            }

                            // ...
                        }
                    });


                }


            });
        }

        public void updateUI(FirebaseUser user) {
            wrapper.setVisibility(View.GONE);
            if (user != null) {

                Intent i = new Intent(com.digipodium.app.motiondetection.RegisterMemberActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }
}
