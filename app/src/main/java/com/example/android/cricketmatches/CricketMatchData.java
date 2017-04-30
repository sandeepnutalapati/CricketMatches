package com.example.android.cricketmatches;


public class CricketMatchData {

    private String mTitle;
    private String mUnique_id;
    private String mDate;




    public CricketMatchData(String name,String unique_id,String date){

        mTitle = name;
        mUnique_id= unique_id;
        mDate = date;



    }

    public String getTitle() {

        return mTitle;
    }

    public String getDate()
    {
       return mDate;
    }

    public String getUnique_id() {
        return mUnique_id;
    }

}
