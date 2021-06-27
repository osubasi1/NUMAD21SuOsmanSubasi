package edu.neu.numad21su_osmansubasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AtYourServiceActivity extends AppCompatActivity  {



    final static String nowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=eea1a7fc0d5c72b36736e248dc5e2693&language=en-US";
    final static String upComing = "https://api.themoviedb.org/3/movie/upcoming?api_key=eea1a7fc0d5c72b36736e248dc5e2693&language=en-US";
    final static String popular = "https://api.themoviedb.org/3/movie/popular?api_key=eea1a7fc0d5c72b36736e248dc5e2693&language=en-US";
    private String choice;

    private JSONArray jsonData;
    private JSONObject jObject;
    private RecyclerView mRecyclerView;
    private List<Object> viewItems = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "activity_at_your_service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        choice = nowPlaying;
                        break;
                    case 1:
                        choice = popular;
                        break;
                    case 2:
                        choice = upComing;
                        break;
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });


    }


    private void createRecyclerView(){

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new RecyclerAdapterMovie(this, viewItems);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void addItemsFromJSON(JSONObject jObject) {
        try {

            jsonData = jObject.getJSONArray("results");
            for (int i=0; i<10; ++i) {

                JSONObject itemObj = jsonData.getJSONObject(i);

                String name = itemObj.getString("original_title");
                String date = itemObj.getString("release_date");

                Movie movie = new Movie(name, date);
                viewItems.add(movie);
            }

        } catch (JSONException e) {
            Log.d(TAG, "addItemsFromJSON: ", e);
        }
    }




    /////////////////////////////////////////////////////////////////////////////////
    private class ListWebServiceTask extends AsyncTask<String, Integer, JSONObject>{


        @Override
        protected void onProgressUpdate(Integer... values){

            Log.i(TAG, "Making Progress");

        }
        @Override
        protected JSONObject doInBackground(String... params) {;

//            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                String resp = NetworkUtil.httpResponse(url);
                jObject = new JSONObject(resp);
                return jObject;
            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }

            return jObject;
        }
        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            createRecyclerView();
            // We need to clear the data otherwise it keeps adding it instead of re-creating it when
            // user changes their choice
            viewItems.clear();
            addItemsFromJSON(jObject);


        }
    }


    public void callWebserviceButtonHandler(View view){
        ListWebServiceTask task = new ListWebServiceTask();
        System.out.println("choice is: " + choice);
        try {
            String url = NetworkUtil.validInput(choice);
            task.execute(url);

        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


}