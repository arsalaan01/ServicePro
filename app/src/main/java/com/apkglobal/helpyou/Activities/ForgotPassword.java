package com.apkglobal.helpyou.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apkglobal.helpyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{
    private static EditText emailId;
    private static TextView submit, back;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        back = findViewById(R.id.back_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        back.setTextColor(getResources().getColorStateList(R.color.textview_selector));
        submit.setTextColor(getResources().getColorStateList(R.color.textview_selector));
        setListeners();
    }
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str;
        switch (v.getId()) {
            case R.id.back_button:
                Intent login=new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(login);
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String getEmailId = emailId.getText().toString();

       /* // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);*/

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

        Toasty.error(getApplicationContext(),"Please enter your Email Id.",Toast.LENGTH_SHORT,true).show();

            // Check if email id is valid or not
        else if (!getEmailId.matches(emailPattern))
        Toasty.error(getApplicationContext(),"Your Email Id is Invalid.",Toast.LENGTH_SHORT,true).show();


        // Else submit email id and fetch passwod or do your stuff
        else {
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(getEmailId)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }
}

