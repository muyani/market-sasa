package com.letina.marketsasa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class PostingActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText mDescription;
    Button buttonUpload;
    ImageView mImageView;
    ProgressBar mProgress;
    Uri mImageUri;
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        mDescription = findViewById(R.id.edtdesc);
        buttonUpload = findViewById(R.id.button);
        mImageView = findViewById(R.id.imgPic);
        mProgress = findViewById(R.id.progressBar);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("uploads");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_product) {
            Toast.makeText(this, "products clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }
}
