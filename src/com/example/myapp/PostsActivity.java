package com.example.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsActivity extends Activity {
    // Log tag
    private static final String TAG =  "[posts response]";

    // Movies json url
   // private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String url = ApiHelper.POSTS_URL;
    private static final String imagesUrl = ApiHelper.IMAGES_URL;
    private ProgressDialog pDialog;
    private List<Post> postList = new ArrayList<Post>();
    Map<String , String> images = new HashMap<String, String>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, postList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj

        JsonArrayRequest imagesReq = new JsonArrayRequest(imagesUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                images.put(obj.getString("id"), ApiHelper.MEDIA_URL + obj.getString("original_image"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

/*
        JsonArrayRequest postReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Post post = new Post();
                                post.setTitle(obj.getString("content"));
                                post.setYear(obj.getString("id"));

                                // Genre is json array
                               // JSONArray imArry = obj.getJSONArray("images");
                               // String id_image = imArry.getJSONObject(0).toString();
                                 post.setThumbnailUrl("http://api.androidhive.info/json/movies/1.jpg");

                                // adding movie to movies array
                                postList.add(post);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });*/
        JsonArrayRequest postReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Post movie = new Post();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl("https://mozan.market/media/post_images/Beauty-of-nature-random-4884759-1280-800.jpg");
                                movie.setRating(8.3);
                                movie.setYear("1222");

                                // Genre is json array

                                ArrayList<String> genre = new ArrayList<String>();
                                genre.add("title");
                                movie.setGenre(genre);
                                // adding movie to movies array
                                postList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        // Adding request to request queue

        AppController appcon = AppController.getInstance();
        //appcon.addToRequestQueue(imagesReq);
        appcon.addToRequestQueue(postReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

}
