package com.digipodium.app.motiondetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private EditText email1;
    private FirebaseAuth auth;
    private TextView statusText;
    private ViewGroup wrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email1 = findViewById(R.id.email);
        final EditText pass = findViewById(R.id.password);
        statusText = findViewById(R.id.statusText);
        final Button btn = findViewById(R.id.login);
        final Button btn1 = findViewById(R.id.register);
        wrapper = findViewById(R.id.progress_wrapper);
        wrapper.setVisibility(View.GONE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, AddMembersActivity.class);
                startActivity(i);
            }
        });
        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String email = email1.getText().toString();
                String password = pass.getText().toString();
                if (email.isEmpty()) {
                    email1.setError("Email cannot be empty");
                    return;
                }
                if (password.isEmpty()) {
                    pass.setError("password cannot be empty");
                    return;
                }
                wrapper.setVisibility(View.VISIBLE);
                login(email, password);

            }
        });
    }

    @NonNull
    private Task<AuthResult> login(String email, final String password) {
        return auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = auth.getCurrentUser();
                    statusText.setText("Success");
                    statusText.setTextColor(Color.GREEN);
                    savePassword(password);
                    updateUI(user);

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    statusText.setText(task.getException().getMessage());
                    statusText.setTextColor(Color.RED);
                    updateUI(null);
                }

                // ...
            }
        });
    }

    private void savePassword(String password) {

        SharedPreferences pref = getSharedPreferences("SECURITY",MODE_PRIVATE);
        pref.edit().putString("password",password).apply();

    }

    public void updateUI(FirebaseUser user) {
        wrapper.setVisibility(View.GONE);
        if (user != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }
}
