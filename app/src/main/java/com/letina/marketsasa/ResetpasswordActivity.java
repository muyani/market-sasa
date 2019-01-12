package com.letina.marketsasa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetpasswordActivity extends AppCompatActivity {
    EditText email;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        email = findViewById(R.id.edtMail);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
    }

    public void reset(View view) {
        String mail = email.getText().toString().trim();
        if (mail.isEmpty()){
            Toast.makeText(this, "Field Empty", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(ResetpasswordActivity.this, "Password Reset Email sent", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                    else{
                        Toast.makeText(ResetpasswordActivity.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
