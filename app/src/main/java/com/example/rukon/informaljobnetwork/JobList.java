package com.example.rukon.informaljobnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class JobList extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    private String filter_jobs_hint = "";
    Spinner spinner;
    EditText search_et;
    Button search_btn;
    Button search_job_btn;
    FloatingActionButton floatingActionButton;

    private Job[] job = new Job[5000];
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public JobList() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        sharedPreferences = getSharedPreferences("search", MODE_PRIVATE);



        fetching_data();

        /// without filtering..
        /*if (sharedPreferences.getInt("option", 0) == 0) {*//*

          }
        */
        /// filtering by Title
/*
        else if (sharedPreferences.getInt("option", 0) == 1) {
*/


        ///filtering by Skill
        /*else if( filter_jobs_opt == 2 ){
            //filter_by_skill(filter_jobs_hint);
        }
        else if( filter_jobs_opt == 3 ){
            //filter_by_location(filter_jobs_hint);
        }
*/
        ///for adding new job notice
        floatingActionButton = (FloatingActionButton) findViewById(R.id.joblist_new);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobList.this, adding_new_job.class);
                startActivity(intent);
            }
        });

        ///button for searching jobs
        search_job_btn = (Button) findViewById(R.id.search);
        search_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///going to the Search functin
                searchFunction();

            }
        });
    }



    void fetching_data() {
        mRecyclerView = (RecyclerView) findViewById(R.id.joblist_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String url = "http://rukon.ourcuet.com/ijn_app/sending_jobs_from_database.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                mAdapter = new MyAdapter(response);
                mRecyclerView.setAdapter(mAdapter);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        job[i] = new Job();
                        job[i].setJob_title(jsonObject.getString("job_title"));
                        job[i].setJob_description(jsonObject.getString("job_description"));
                        job[i].setSkill_required(jsonObject.getString("skill_required"));
                        job[i].setAddress(jsonObject.getString("address"));
                        job[i].setJob_provider_name(jsonObject.getString("post_uploader_name"));
                        System.out.println(job[i].getJob_title());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });
        MySingleton.getmInstance(this).AddToRequestQueue(jsonArrayRequest);
    }

    void filter_by_title(String what_to_search, String url) {
        final String what_ts = what_to_search;  Map<String, String> s_request = new HashMap<String, String>();
        s_request.put("search_this", what_ts);
        CustomJsonArrayRequest jsonArrayRequest = new CustomJsonArrayRequest(Request.Method.POST,url,s_request, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setContentView(R.layout.activity_job_list);

                mRecyclerView = (RecyclerView) findViewById(R.id.joblist_view);
                mLayoutManager = new LinearLayoutManager(JobList.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new MyAdapter(response);
                mRecyclerView.setAdapter(mAdapter);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        job[i] = new Job();
                        job[i].setJob_title(jsonObject.getString("job_title"));
                        job[i].setJob_description(jsonObject.getString("job_description"));
                        job[i].setSkill_required(jsonObject.getString("skill_required"));
                        job[i].setAddress(jsonObject.getString("address"));
                        job[i].setJob_provider_name(jsonObject.getString("post_uploader_name"));
                        System.out.println("filter: "+jsonObject.getString("job_title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log: ", error);
            }
        });

        MySingleton.getmInstance(getApplicationContext()).AddToRequestQueue(jsonArrayRequest);
    }

    public void searchFunction() {

        setContentView(R.layout.activity_search);

        ///get the spinner,search_btn & edit_text from xml
        spinner = (Spinner) findViewById(R.id.spinner_search);
        search_et = (EditText) findViewById(R.id.job_search);
        search_btn = (Button) findViewById(R.id.search_btn);

        //create list of items for spinner
        String[] items = new String[]{"Search by Title", "Search by Skill", "Search by Location"};
        //create adapter to describe how items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one
        spinner.setAdapter(adapter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_item;
                String spinner_text;
                /// copying the selected item to string variable
                spinner_text = spinner.getSelectedItem().toString();
                // toasting the spinner text(for checking)
                //Toast.makeText(getApplicationContext(), spinner_text, Toast.LENGTH_SHORT).show();
                search_item = search_et.getText().toString();

                if (search_item.equals("")) {
                    // edit_text is empty
                    Toast.makeText(getApplicationContext(), "Error! Search field is empty !!!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Searching...", Toast.LENGTH_SHORT).show();
                    ///1 -> search by title
                    ///2 -> search by skill
                    ///3 -> search by location
                    if (spinner_text.equals("Search by Title")) {
                        filter_by_title(search_item, "http://rukon.ourcuet.com/ijn_app/search_by_title.php");
                    } else if (spinner_text.equals("Search by Skill")) {
                        filter_by_title(search_item, "http://rukon.ourcuet.com/ijn_app/search_by_skill.php");
                    } else if (spinner_text.equals("Search by Location")) {
                        filter_by_title(search_item, "http://rukon.ourcuet.com/ijn_app/search_by_location.php");
                    }
                }
            }
        });


    }
}
