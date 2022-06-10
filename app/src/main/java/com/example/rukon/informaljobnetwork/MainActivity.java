package com.example.rukon.informaljobnetwork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username_et, password_et;
    TextView signup_txt;
    Button login_btn;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_et = (EditText) findViewById(R.id.username);
        password_et = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_b);
        signup_txt = (TextView) findViewById(R.id.signup_txt);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = username_et.getText().toString();
                password = password_et.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Please provide required info...",Toast.LENGTH_LONG ).show();
                }else {
                    /// job list activity
                    //startActivity(new Intent(MainActivity.this, JobList.class));

                    ///joblist activity
                    ///startActivity(new Intent(MainActivity.this, JobList.class));
                     checking_login();

                }


            }
        });

        signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);

            }
        });
    }

    void checking_login()
    {
        String url;
        url = "http://rukon.ourcuet.com/ijn_app/login.php";

        StringRequest srq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ///checking the response
                //System.out.println("stat:"+response+".");

                if( response.equals("\nOK")) {
                    Toast.makeText(getApplicationContext(), "You've been successfully logged in!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, JobList.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sorry! User not found!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Sorry! Something went wrong!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> check_login = new HashMap<String, String>();
                check_login.put("username", username);
                check_login.put("password", password);


                return check_login;
            }
        };

        MySingleton.getmInstance(MainActivity.this).AddToRequestQueue(srq);
    }

}