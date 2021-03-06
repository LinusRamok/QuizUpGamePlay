package com.alias.quizone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;
import com.quiz.up.RankingPagePOJO.Top5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by RAJA on 12-01-2018.
 */

public class RankingPageActivity extends AppCompatActivity {
    StringBuffer response;
    RecyclerView rankinglistss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_page);

        //Creating recycler view object
        rankinglistss = (RecyclerView) findViewById(R.id.recycler);
        rankinglistss.setLayoutManager(new LinearLayoutManager(this));
            StartSmartAnimation.startAnimation(findViewById(R.id.recycler) , AnimationType.SlideInUp , 2000 , 0 , true );
        String Topic_name=getIntent().getStringExtra("topicname");


//volley.......................................................
//putting entered value into URL
        String PID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String TopicKey = null;
        try {

            TopicKey = URLEncoder.encode(Topic_name, "UTF-8").replaceAll("\\+", "%20");
            System.out.println("here is encoded key :" + TopicKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        String URL = LoginActivity.URLprefix+"ranking?Topic="+TopicKey+"&PID="+PID;
        System.out.println(URL);
//Data Downloader-Volley
        final StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
if(data!=null) {
    ProgressBar progressBar=findViewById(R.id.progress);
    progressBar.setVisibility(View.GONE);
    JSONObject object = null;
    try {
        object = new JSONObject(data);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    JSONObject obj = null;
    try {
        obj = object.getJSONObject("message");
    } catch (JSONException e) {
        e.printStackTrace();
    }
    JSONArray array = null;
    try {
        array = obj.getJSONArray("top5");
    } catch (JSONException e) {
        e.printStackTrace();
    }

    Top5[] rankinglists = new Gson().fromJson(String.valueOf(array), Top5[].class);
    rankinglistss.setAdapter(new RankinglistAdapter(getApplicationContext(), rankinglists));
}
else {
    ProgressBar progressBar=findViewById(R.id.progress);
    progressBar.setVisibility(View.VISIBLE);
}
            }
        },
//Error listener:Server error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
//Adding request Queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        //     StartSmartAnimation.startAnimation( findViewById(R.id.test) , AnimationType.ZoomIn,1000, 0 , true );
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

