package com.example.collegeapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView openReg,logEmail,logPass,openForgotPass;
    private Button login;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    private String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        getSupportActionBar ().hide ();


        auth = FirebaseAuth.getInstance ();
        pd = new ProgressDialog (  this);

        openReg = findViewById ( R.id.openReg );
        logEmail = findViewById ( R.id.logEmail );
        logPass = findViewById ( R.id.logPass );
        openForgotPass = findViewById ( R.id.openForgotPass );

        login = findViewById ( R.id.login );



        login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );


        openForgotPass.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (LoginActivity.this,ForgetPasswordActivity.class));
            }
        } );




        openReg.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        } );
    }

    private void validateData() {
        email = logEmail.getText ().toString ();
        pass = logPass.getText ().toString ();

        if(email.isEmpty ()){
            logEmail.setError ( "Empty" );
            logEmail.requestFocus ();
        }else if(pass.isEmpty ()){
            logPass.setError ( "Empty" );
            logPass.requestFocus ();
        }else{
            loginUser();
        }
    }

    private void loginUser() {
        pd.setMessage ( "Wait a while.." );
        pd.show();

        auth.signInWithEmailAndPassword ( email,pass ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful ()){
                    pd.dismiss ();
                    openMain ();
                }else {
                    Toast.makeText ( LoginActivity.this, "Error :"+task.getException ().getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
                }
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss ();
                Toast.makeText ( LoginActivity.this, "Error :"+e.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void openMain() {
        startActivity ( new Intent(LoginActivity.this, MainActivity.class ));
        finish ();
    }

    private void openRegister() {
        startActivity ( new Intent (LoginActivity.this,RegisterActivity.class));
        finish ();
    }
}