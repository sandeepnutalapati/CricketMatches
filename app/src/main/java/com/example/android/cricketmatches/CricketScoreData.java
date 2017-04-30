package com.example.android.cricketmatches;

import static android.R.attr.name;

/**
 * Created by mounideep on 1/20/2017.
 */

public class CricketScoreData {
    private String mDatetimeGmt;
    private String mType;
    private String mComment;
    private String mScore;



    public CricketScoreData(String datetimeGmt,String type,String comment,String score){
        mDatetimeGmt=datetimeGmt;
        mType=type;
        mComment=comment;
        mScore=score;

    }

    public String getmDatetimeGmt() {return mDatetimeGmt;}
    public String getmComment() {return mComment;}
    public String getmType() {return mType;}
    public String getmScore(){return mScore;}

}
