package com.example.android.cricketmatches;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;



public class Main2Activity extends AppCompatActivity {


    private static final String CRICKET_SCORE_URL=
            "http://cricapi.com/api/cricketScore?apikey=Rqzvnj7JsTa4FjHwt2bxKzD1up92&unique_id=" ;

    String s;
    String sdate;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        s= getIntent().getStringExtra("unique_id");
        sdate = getIntent().getStringExtra("sdate");
        title=getIntent().getStringExtra("title");
        String url = CRICKET_SCORE_URL+s;
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText("Match Title : "+" "+title);
        TextView scoreView = (TextView) findViewById(R.id.score);
        TextView typeView = (TextView) findViewById(R.id.type);
        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText("DATE :"+" "+sdate);
        scoreView.setText("Score: "+" "+"Loading... or Match not yet Started");
        typeView.setText("Match Type:Loading or Match not yet Started");
        CricketScoreAsync task = new CricketScoreAsync();
        task.execute(url);

    }

    private class CricketScoreAsync extends AsyncTask<String, Void, CricketScoreData> {

        @Override
        protected CricketScoreData doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            CricketScoreData result = QueryUtils.fetchCricketScoreData(urls[0]);
            return result;
        }


        @Override
        protected void onPostExecute(CricketScoreData result) {
            if (result == null) {
                return;
            }

            updateUi(result);
        }
    }

    void updateUi(CricketScoreData data)
    {


        TextView score = (TextView) findViewById(R.id.score);
        score.setText("Match Details :"+" "+data.getmComment()+"\n\n"+data.getmScore());


        TextView type = (TextView) findViewById(R.id.type);
        type.setText("Match Type :"+" "+data.getmType());


        TextView date = (TextView) findViewById(R.id.date);
        date.setText("Date&Time :"+" "+data.getmDatetimeGmt());

    }
}