package com.myproject.dawachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.myproject.dawachat.Models.User;
import com.myproject.dawachat.databinding.ActivitySignUpActitivtyBinding;

public class SignUpActitivty extends AppCompatActivity {

    ActivitySignUpActitivtyBinding activitySignUpActitivtyBinding;
    Button signupbtn ;
    EditText userName;
    EditText userEmail;
    EditText userPassword;
    FirebaseAuth firebaseAuth;
    AlertDialog dialog;
    AlertDialog.Builder builder  ;
    AlertDialog.Builder err_builder;
    TextView signinBtn;
    public  static  String TAG = "signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpActitivtyBinding = ActivitySignUpActitivtyBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpActitivtyBinding.getRoot());
        getSupportActionBar().hide();
        signupbtn = activitySignUpActitivtyBinding.signupBtn;
        userEmail = activitySignUpActitivtyBinding.inputEmailSignup;
        userName  = activitySignUpActitivtyBinding.inputUsernameSignup;
        userPassword = activitySignUpActitivtyBinding.inputPasswordSignup;
        firebaseAuth = FirebaseAuth.getInstance();
        builder  = new AlertDialog.Builder(this);
        err_builder = new AlertDialog.Builder(this);
        signinBtn = activitySignUpActitivtyBinding.signinBtn;
        // signup
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                signUp(name , email , password);
            }
        });

        // signin btn
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActitivty.this , LoginActivity.class));
            }
        });
    }


    private void signUp(String username, String email, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "sorry data is invalid", Toast.LENGTH_SHORT).show();
        }else if(password.length() <= 4)
        {
            Toast.makeText(this, "sorry password must not less than 2", Toast.LENGTH_SHORT).show();
        }else
        {
             showDialog(true);
            firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    showDialog(false);
                    if(!task.isSuccessful()) {

                        if(task.getException() instanceof FirebaseNetworkException)
                        {
                            //   Toast.makeText(SignUpActitivty.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            showErrorDialog("no internet connection");;
                        }
                        else if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            //   Toast.makeText(SignUpActitivty.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            showErrorDialog("sorry this user is exists");;
                        }else
                        {
                            //   Toast.makeText(SignUpActitivty.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            showErrorDialog(task.getException().toString());
                            Log.d(TAG,   task.getException().toString());
                        }


                    }else
                    {
                        String id = task.getResult().getUser().getUid();
                        User newUser = new User(username , email , password);
                        FirebaseDatabase.getInstance().getReference().child("users").child(id).setValue(newUser);
                        startActivity(new Intent(getBaseContext() , MainActivity.class));
                        finish();
                    }
                }
            });
        }
    }


    private void   showDialog(boolean show) {
        if(show)
        {
            builder.setView(R.layout.progressbar);
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
        }else
        {
            dialog.dismiss();
        }
    }
    private  void  showErrorDialog(String err)
    {
        int ms= 1;
        err_builder.setTitle("Error");
        err_builder.setMessage(err);
        err_builder.setCancelable(true);
        dialog = err_builder.create();
        dialog.show();


    }
    
}