package com.example.rukon.informaljobnetwork;

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

public class adding_new_job extends AppCompatActivity {

    EditText job_title_et, job_descrip_et, skill_req_et, address_et, job_providers_name_et;
    String  job_title, job_descrip, skill_req, address, job_providers_name;
    Button job_submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_new_job);

        job_title_et =(EditText) findViewById(R.id.job_title);
        job_descrip_et = (EditText)findViewById(R.id.job_description);
        skill_req_et = (EditText)findViewById(R.id.skill_required);
        address_et = (EditText)findViewById(R.id.address);
        job_providers_name_et =(EditText)findViewById(R.id.job_providers_name);
        job_submit_btn = (Button) findViewById(R.id.job_submit_btn);

        job_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job_title = job_title_et.getText().toString();
                job_descrip = job_descrip_et.getText().toString();
                skill_req = skill_req_et.getText().toString();
                address = address_et.getText().toString();
                job_providers_name = job_providers_name_et.getText().toString();

                if(job_title.equals("") || job_descrip.equals("") || skill_req.equals("") || address.equals("") || job_providers_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Fill in all the textfields and try again!", Toast.LENGTH_SHORT).show();
                }else {
                    add_new_job_into_database();
                }
            }
        });
    }

    void add_new_job_into_database()
    {
        String url;
        url = "http://rukon.ourcuet.com/ijn_app/new_job_notice.php";
        StringRequest srq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response;
                if( res.equals("lllllll")) {







                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(adding_new_job.this, "Sorry! Something went wrong!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> new_job_notice = new HashMap<String, String>();
                new_job_notice.put("job_title", job_title);
                new_job_notice.put("job_descrip", job_descrip);
                new_job_notice.put("skill_required", skill_req);
                new_job_notice.put("address", address);
                new_job_notice.put("job_providers_name", job_providers_name);

                return new_job_notice;
            }
        };

        MySingleton.getmInstance(adding_new_job.this).AddToRequestQueue(srq);
    }
}
