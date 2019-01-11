package com.letina.marketsasa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    EditText mMail,pass1,pass2;
    TextView login;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mMail =  findViewById(R.id.edtMail);
        pass1 = findViewById(R.id.editPass1);
        pass2 = findViewById(R.id.edtPass2);
        login = findViewById(R.id.tvlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");

    }

    private boolean validate(){
        boolean result = false;
        String mail = mMail.getText().toString().trim();
        String pass = pass1.getText().toString().trim();
        if (mail.isEmpty() ||  pass.isEmpty()){
            Toast.makeText(this, "Fill All Inputs", Toast.LENGTH_SHORT).show();

        }
        else if (pass.length()<6){
            Toast.makeText(this, "Password is less than 6 characters", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;



        }
        return result;
    }
    public void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Registered Sucessfully,Please check your email", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    public void signing(View view) {
        if (validate()){
            //register user
            String user_email = mMail.getText().toString().trim();
            String user_pass = pass1.getText().toString().trim();
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        sendEmailVerification();

                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
}
