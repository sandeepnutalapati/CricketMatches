package com.example.android.cricketmatches;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class CricketMatchDataAdapter  extends ArrayAdapter<CricketMatchData> {

    public CricketMatchDataAdapter(Activity context, ArrayList<CricketMatchData> cricketMatchDatas) {
        super(context, 0, cricketMatchDatas);


    }

    @Override
    public void clear() {
        super.clear();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }

        CricketMatchData currentMatch = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);

        if (currentMatch != null) {
            title.setText(currentMatch.getTitle());
        }



        return listItemView;
    }


}