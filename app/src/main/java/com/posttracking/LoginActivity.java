package com.posttracking;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.posttracking.Boundaries.CustomerDAO;
import com.posttracking.Boundaries.Database;
import com.posttracking.Entities.Customer;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private com.posttracking.Boundaries.CustomerDAO db;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ListView lv = findViewById(R.id.lvLogins);

        db = new CustomerDAO(this);
        final ArrayList<Customer> customers = db.getAllCustomers();
        final Intent goHome = new Intent(this,HomeActivity.class);

        ArrayAdapter adap;
        if (customers.size() == 0) {
            adap = new ArrayAdapter<String>(this, R.layout.login_list_view, new String[] {"No Customer saved\n, Please SignUp"});
        } else {
            adap = new ArrayAdapter<Customer>(this, R.layout.login_list_view, customers);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Customer c = customers.get(position);
                    emailText.setText(c.getEmail());
                }
            });
        }

        lv.setAdapter(adap);

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signUpLink = (Button) findViewById(R.id.link_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("email", emailText.getText().toString());
                Log.d("pass", passwordText.getText().toString());

                boolean loginOK = db.checkLogin(emailText.getText().toString(), passwordText.getText().toString());
                Log.d("login status", String.valueOf(loginOK));
                if(loginOK) {
                    Customer c = db.getCustomer(emailText.getText().toString());
                    goHome.putExtra("customerId", c.getCustomer_id());
                    startActivity(goHome);
                    finish();
                    Log.d("Click!!!!", c.getFirstName());

                } else {
                    Toast.makeText(getApplicationContext(),"Wrong email and/or password.",Toast.LENGTH_LONG);
                }
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