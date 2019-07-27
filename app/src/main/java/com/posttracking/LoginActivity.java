package com.posttracking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.posttracking.Boundaries.CustomerDAO;
import com.posttracking.Entities.Customer;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private com.posttracking.Boundaries.CustomerDAO db;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    Button deleteButton;
    TextView signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ListView lv = findViewById(R.id.lvLogins);

        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        deleteButton = findViewById(R.id.btn_delete);
        signUpLink = findViewById(R.id.link_signup);

        db = new CustomerDAO(this);
        final ArrayList<Customer> customers = db.getAllCustomers();
        final Intent goHome = new Intent(this,HomeActivity.class);
        final Intent refresh = new Intent(this,LoginActivity.class);

        final ArrayAdapter adap;
        if (customers.size() == 0) {
            adap = new ArrayAdapter<String>(this, R.layout.login_list_view, new String[] {"No Customer saved.\nPlease Sign Up"});
            loginButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            emailText.setVisibility(View.GONE);
            passwordText.setVisibility(View.GONE);
        } else {
            adap = new ArrayAdapter<Customer>(this, R.layout.login_list_view, customers);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Customer c = customers.get(position);
                    emailText.setText(c.getEmailAddress());
                    passwordText.requestFocus();
                }
            });
        }

        lv.setAdapter(adap);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean loginOK = db.checkLogin(emailText.getText().toString(), passwordText.getText().toString());
                if(loginOK) {
                    Customer c = db.getCustomer(emailText.getText().toString());
                    goHome.putExtra("customerId", c.getId());
                    startActivity(goHome);
                    finish();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Wrong email and/or password.",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer c = db.getCustomer(emailText.getText().toString());
                db.deleteCustomer(c.getId());

                startActivity(refresh);
                finish();
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}