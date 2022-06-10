package com.example.rukon.informaljobnetwork;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private JSONArray mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView first, second;

        public MyViewHolder(View v) {
            super(v);
            first = v.findViewById(R.id.first);
            second = v.findViewById(R.id.second);
        }
    }

    public MyAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
                JSONObject jsonObject = mDataset.getJSONObject(position);
                holder.first.setText( "Title: \n" + jsonObject.getString("job_title"));
                holder.second.setText("Skill Required: \n " + jsonObject.getString("skill_required"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length();
    }

}