package com.example.collegeapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText regName,regEmail,regPass;
    private Button register;
    private FirebaseAuth auth;
    private DatabaseReference reference,dbref;
    private ProgressDialog pd;
    private TextView openLog;

    String name,email,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register);
        getSupportActionBar ().hide ();


        reference = FirebaseDatabase.getInstance ().getReference ();
        auth = FirebaseAuth.getInstance ();
        pd = new ProgressDialog ( this );


        regName = findViewById ( R.id.regName );
        regEmail = findViewById ( R.id.regEmail );
        regPass = findViewById ( R.id.regPass );
        register = findViewById ( R.id.register );
        openLog = findViewById ( R.id.openLog );

        openLog.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        } );

        register.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );
    }

    private void openLogin() {
        startActivity ( new Intent (RegisterActivity.this, LoginActivity.class ) );
        finish ();
    }

    private void validateData() {
        name = regName.getText ().toString ();
        email = regEmail.getText ().toString ();
        pass = regPass.getText ().toString ();

        if (name.isEmpty ()){
            regName.setError ( "Empty" );
            regName.requestFocus ();
        }else if(email.isEmpty ()){
            regEmail.setError ( "Empty" );
            regEmail.requestFocus ();
        }else if(pass.isEmpty ()){
            regPass.setError ( "Empty" );
            regPass.requestFocus ();
        }else{
            createUser();
        }
    }

    @Override
    protected void onStart() {
        super.onStart ();
        if (auth.getCurrentUser () !=null){
            openMain();
        }
    }

    private void openMain() {
        startActivity ( new Intent (RegisterActivity.this, MainActivity.class ) );
        finish ();
    }


    private void createUser() {
        pd.setMessage ( "Wait a while" );
        pd.show ();

        auth.createUserWithEmailAndPassword ( email,pass ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful ()){
                    uploadData();

                }else {
                    Toast.makeText ( RegisterActivity.this, "Error :"+task.getException ().getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
                }

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( RegisterActivity.this, "Error :"+e.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void uploadData() {
        dbref = reference.child ( "users" );
        String key;
        key = dbref.push ().getKey ();

        HashMap<String,String> user = new HashMap<> ();
         user.put ( "key",key );
         user.put ( "name", name );
         user.put ( "email",email );
         user.put ( "status","no" );
         dbref.child ( key ).setValue ( user )
                 .addOnCompleteListener ( new OnCompleteListener<Void> () {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful ()) {
                             pd.dismiss ();
                             Toast.makeText ( RegisterActivity.this, "User Created succuessfully", Toast.LENGTH_SHORT ).show ();
                             openMain ();
                         } else {
                             Toast.makeText ( RegisterActivity.this, "Error", Toast.LENGTH_SHORT ).show ();
                         }
                     }

                 } ).addOnFailureListener ( new OnFailureListener () {
             @Override
             public void onFailure(@NonNull Exception e) {
                 pd.dismiss ();
                 Toast.makeText ( RegisterActivity.this, "Error"+e.getLocalizedMessage (), Toast.LENGTH_SHORT ).show ();
             }
         } );

    }
}