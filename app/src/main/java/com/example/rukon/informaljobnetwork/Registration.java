package com.example.rukon.informaljobnetwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText name_et, email_et, username_et, password_et, address_et;
    Button reg_submit_btn;
    String name, email, username, password, address;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        builder = new AlertDialog.Builder(Registration.this);
        name_et = findViewById(R.id.name);
        email_et = findViewById(R.id.email);
        username_et = findViewById(R.id.username);
        password_et = findViewById(R.id.password);
        address_et = findViewById(R.id.address);
        reg_submit_btn = findViewById(R.id.reg_btn);

        reg_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getText().toString();
                email = email_et.getText().toString();
                username = username_et.getText().toString();
                password = password_et.getText().toString();
                address = address_et.getText().toString();
                if (name.equals("") || email.equals("") || username.equals("") || password.equals("") || address.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please provide all information!!!", Toast.LENGTH_SHORT).show();
                } else if( !email.endsWith(".com") || !email.contains("@")){
                    Toast.makeText(getApplicationContext(), "Please Enter valid Email!!!", Toast.LENGTH_SHORT).show();
                }else {
                    insert_data();
                }

            }
        });

    }

    /// insertng data into database
    void insert_data()
    {
        String url = "http://rukon.ourcuet.com/ijn_app/registration.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                builder.setMessage(response);
                builder.setTitle("Registration Status:");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Intent intent=new Intent(Registration.this,adding_new_job.class);
                       startActivity(intent);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Registration.this, "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map <String, String> reg_map  = new HashMap<String, String>();
                reg_map.put("name", name);
                reg_map.put("email", email);
                reg_map.put("username", username);
                reg_map.put("password", password);
                reg_map.put("address", address);

                return reg_map;
            }
        };

        MySingleton.getmInstance(Registration.this).AddToRequestQueue(stringRequest);
    }
}
