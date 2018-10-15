package com.example.tonyngeno.hairsalke;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText firstnametextinput;
    private EditText lastnametextinput;
    private EditText usernametextinput;
    private EditText emailtextinput;
    private EditText passwordtextinput;
    private Button buttonsubmit;
    private Spinner spin;
    private ProgressDialog dialogload;
    private FirebaseAuth firebaseauth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        firebaseauth = FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference().child("Users");


        if (firebaseauth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplication(), home.class));
        }


        dialogload = new ProgressDialog(this);

        firstnametextinput = (EditText) findViewById(R.id.firstnametext);
        lastnametextinput = (EditText) findViewById(R.id.lastnametext);
        usernametextinput = (EditText) findViewById(R.id.usernametext);
        emailtextinput = (EditText) findViewById(R.id.emailtext);
        passwordtextinput = (EditText) findViewById(R.id.passwordtext);
        buttonsubmit = (Button) findViewById(R.id.buttonregister);

        buttonsubmit.setOnClickListener(this);


    }





    @Override
    public void onClick(View v) {
        if (v == buttonsubmit){
            registerUser();
        }
    }

    private void registerUser() {
        final String firstname = firstnametextinput.getText().toString().trim();
        final String lastname = lastnametextinput.getText().toString().trim();
        final String email = emailtextinput.getText().toString().trim();
        final String username = usernametextinput.getText().toString().trim();
        final String password = passwordtextinput.getText().toString().trim();

        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "Please enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(this, "Please enter Last Name", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialogload.setMessage("Signing Up...");
        dialogload.show();

        firebaseauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            String user_id = firebaseauth.getCurrentUser().getUid();
                            DatabaseReference mRef = myRef.child(user_id);
                            mRef.child("firstname").setValue(firstname);
                            mRef.child("lastname").setValue(lastname);
                            mRef.child("email").setValue(email);
                            mRef.child("username").setValue(username);
                            mRef.child("password").setValue(password);




                            Toast.makeText(Register.this, "Welcome", Toast.LENGTH_SHORT).show();

                            final Intent mainIntent = new Intent(Register.this, home.class);
                            Register.this.startActivity(mainIntent);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Register.this.finish();
                        }
                        else {
                            Toast.makeText(Register.this, "Error in Signing Up.Please try again later...",Toast.LENGTH_SHORT).show();
                        dialogload.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


