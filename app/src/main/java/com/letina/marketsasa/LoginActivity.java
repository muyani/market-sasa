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

public class LoginActivity extends AppCompatActivity {
    EditText name,password;
    TextView info,signup,forgotPass;
    int counter = 5;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.edtMail);
        password = findViewById(R.id.edtPass);
        info = findViewById(R.id.tvInfo);
        signup = findViewById(R.id.tvSignup);
        forgotPass = findViewById(R.id.tvForgot);
        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }

        });
        info.setText("Number of Attempts remaining "+counter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),PostingActivity.class));

        }
    }
    public void validate(String userName,String userPassword){
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    emailVerification();

                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("Number Of Attempts Remaining :" + counter);

                }
            }
        });
    }
    public void emailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        boolean emailFlag = firebaseUser.isEmailVerified();
        if (emailFlag){
            finish();
            startActivity(new Intent(getApplicationContext(),PostingActivity.class));
        }
        else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    public void logingin(View view) {
        String uname = name.getText().toString().trim();
        String upass = password.getText().toString().trim();
        if (uname.isEmpty() || upass.isEmpty()){
            Toast.makeText(this, "Field empty", Toast.LENGTH_SHORT).show();
        }
        else{
            validate(uname,upass);
        }


    }
}
