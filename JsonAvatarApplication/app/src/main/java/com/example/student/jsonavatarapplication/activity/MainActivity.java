package com.example.student.jsonavatarapplication.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.student.jsonavatarapplication.Adapter.MyAdapter;
import com.example.student.jsonavatarapplication.R;
import com.example.student.jsonavatarapplication.models.JsonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<JsonModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyc);
    }

    private void setRecyclerView(){
        MyAdapter myAdapter = new MyAdapter(list, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(myAdapter);
        MyJsonAsyncTask myJsonAsyncTask = new MyJsonAsyncTask();
        myJsonAsyncTask.execute();

    }

    class MyJsonAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {


            try {
                JSONArray jsonArray = new JSONArray("https://jsonplaceholder.typicode.com/photos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JsonModel jsonModel =  new JsonModel();
                    jsonModel.setAlbumId(Integer.valueOf(jsonObject.getString("albumId")));
                    jsonModel.setId(Integer.valueOf(getString(Integer.parseInt("id"))));
                    jsonModel.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));
                    jsonModel.setTitle(jsonObject.getString("title"));
                    jsonModel.setUrl(jsonObject.getString("url"));
                    list.add(jsonModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setRecyclerView();
        }
    }

}
