package com.myproject.dawachat;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;
import com.myproject.dawachat.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    Button signinBtn;
    EditText emailInput;
    EditText passwordInput;
    AlertDialog dialog;
    AlertDialog.Builder builder  ;
    AlertDialog.Builder err_builder;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        emailInput = activityLoginBinding.inputEmailSignin;
        passwordInput =activityLoginBinding.inputPasswordSignin;
        signinBtn = activityLoginBinding.signinBtn;
        builder = new AlertDialog.Builder(this);
        err_builder = new AlertDialog.Builder(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        signupLink = activityLoginBinding.signupLink;
        getSupportActionBar().hide();
        // sign in click btn
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(true);
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                signin(email , password);
            }
        });
        // click on signup
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , SignUpActitivty.class));

            }
        });
        // if user is already logined in so redirect to main activity
        isLogged();

    }

    private void isLogged() {
        if(firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(LoginActivity.this , MainActivity.class));
            finish();
        }
    }


    private void signin(String email , String password) {

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                showDialog(false);
                showErrorDialog("sorry invalid data");
            }else
            {
              firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      showDialog(false);
                      if(!task.isSuccessful())
                      {
                          if(task.getException() instanceof FirebaseNetworkException)
                          {
                              showErrorDialog("sorry no intenet connection");
                          }
                          else if (task.getException() instanceof FirebaseAuthInvalidUserException)
                          {
                              showErrorDialog("sorry this email is not found");
                          }
                          else if(task.getException() instanceof FirebaseAuthEmailException)
                          {
                              showErrorDialog("sorry this email is invalid");
                          }
                          else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                          {
                              showErrorDialog("sorry password is invalid");
                          }else
                          {
                              showErrorDialog(task.getException().toString());
                          }

                      }else
                      {
                          // here if succsfull login
                          startActivity(new Intent(LoginActivity.this, MainActivity.class));
                          finish();
                      }
                  }
              });
            }
    }
    private void  showDialog(boolean show) {
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