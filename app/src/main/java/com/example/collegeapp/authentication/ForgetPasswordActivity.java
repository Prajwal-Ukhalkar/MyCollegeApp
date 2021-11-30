package com.example.collegeapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button forgotBtn;
    private EditText forEmail;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_forget_password );
        getSupportActionBar ().hide ();

        auth = FirebaseAuth.getInstance ();

        forEmail = findViewById ( R.id.forEmail );
        forgotBtn = findViewById (R.id.forgotBtn);

        forgotBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );
    }

    private void validateData() {
        email = forEmail.getText ().toString ();
        if (email.isEmpty ()){
            forEmail.setError ( "Required" );
            forEmail.requestFocus ();
        }else{
            forgetPass();
        }
    }

    private void forgetPass() {
        auth.sendPasswordResetEmail ( email )
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText ( ForgetPasswordActivity.this, " Password reset link has been sent to your Email accont", Toast.LENGTH_SHORT ).show ();
                            startActivity ( new Intent (ForgetPasswordActivity.this,LoginActivity.class) );
                            finish ();
                        }else{
                            Toast.makeText ( ForgetPasswordActivity.this, "Error :"+task.getException ().getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( ForgetPasswordActivity.this, "Error :"+ e.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}