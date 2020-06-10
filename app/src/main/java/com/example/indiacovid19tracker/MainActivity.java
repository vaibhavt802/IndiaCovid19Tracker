package com.example.indiacovid19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

//import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {

    TextView tvCases, tvTodayCases, tvDeaths, tvTodayDeaths, tvRecovered, tvTodayRecovered, tvActive;
    TextView tvCritical, tvCasesPerOneMillion, tvDeathsPerOneMillion, tvTests, tvTestsPerOneMillion;
    SimpleArcLoader mSimpleArcLoader;
    ScrollView mScrollView;
    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases = findViewById(R.id.tvcases);
        tvTodayCases = findViewById(R.id.tvtodaycases);
        tvDeaths = findViewById(R.id.tvdeaths);
        tvTodayDeaths = findViewById(R.id.tvtodaydeaths);
        tvRecovered = findViewById(R.id.tvrecovered);
        tvTodayRecovered = findViewById(R.id.tvtodayrecovered);
        tvActive = findViewById(R.id.tvactive);
        tvCritical = findViewById(R.id.tvcritical);
        tvCasesPerOneMillion = findViewById(R.id.tvcasesperonemillion);
        tvDeathsPerOneMillion = findViewById(R.id.tvdeathsperonemillion);
        tvTests = findViewById(R.id.tvtests);
        tvTestsPerOneMillion = findViewById(R.id.tvtestsperonemillion);
        mSimpleArcLoader = findViewById(R.id.loader);
        mScrollView = findViewById(R.id.scrollStats);
        mPieChart = findViewById(R.id.piechart);

        fetchData ();

    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/countries/india?yesterday=true&strict=true&query";

        mSimpleArcLoader.start();

        StringRequest mRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject mJsonObject = new JSONObject(response.toString());

                            tvCases.setText(mJsonObject.getString("cases"));
                            tvTodayCases.setText(mJsonObject.getString("todayCases"));
                            tvDeaths.setText(mJsonObject.getString("deaths"));
                            tvTodayDeaths.setText(mJsonObject.getString("todayDeaths"));
                            tvRecovered.setText(mJsonObject.getString("recovered"));
                            tvTodayRecovered.setText(mJsonObject.getString("todayRecovered"));
                            tvActive.setText(mJsonObject.getString("active"));
                            tvCritical.setText(mJsonObject.getString("critical"));
                            tvCasesPerOneMillion.setText(mJsonObject.getString("casesPerOneMillion"));
                            tvDeathsPerOneMillion.setText(mJsonObject.getString("deathsPerOneMillion"));
                            tvTests.setText(mJsonObject.getString("tests"));
                            tvTestsPerOneMillion.setText(mJsonObject.getString("testsPerOneMillion"));

                            mPieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                            mPieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            mPieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            mPieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));

                            mPieChart.startAnimation();
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSimpleArcLoader.stop();
                mSimpleArcLoader.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mRequest);

    }

    public void btntrackclick(View view) {

        startActivity(new Intent(getApplicationContext(),AffectedStates.class));

    }
}
