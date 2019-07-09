package com.posttracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.posttracking.Boundaries.CustomerDAO;
import com.posttracking.Entities.Customer;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signUpButton;
    TextView loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = (EditText) findViewById(R.id.input_firstName);
        lastNameText = (EditText) findViewById(R.id.input_lastName);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        signUpButton = (Button) findViewById(R.id.btn_signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Finish the registration screen and return to the Login activity
        navigateToLogin();
    }

    private void navigateToLogin() {
        // Finish the registration screen and return to the Login activity
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup() {
        Log.d(TAG, "Signup");

        signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String fName = firstNameText.getText().toString();
        String lName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        CustomerDAO custDAO = new CustomerDAO(this);
        boolean emailExist = custDAO.checkEmail(emailText.getText().toString());

        if(emailExist) {
            Toast.makeText(getApplicationContext(),"This email already exists.",Toast.LENGTH_LONG);
            emailText.setText("");
        } else {
            Customer cust = new Customer();
            cust.setFirstName(fName);
            cust.setLastName(lName);
            cust.setEmail(email);
            cust.setPassword(password);

            custDAO.addCustomer(cust);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            progressDialog.dismiss();
                        }
                    }, 1500);
            }
        }


    /*
    public boolean validate() {
        boolean valid = true;

        String fName = firstNameText.getText().toString();
        String lName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (fName.isEmpty() || fName.length() < 3) {
            firstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            firstNameText.setError(null);
        }

        if (lName.isEmpty() || lName.length() < 3) {
            lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            lastNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
    */
}