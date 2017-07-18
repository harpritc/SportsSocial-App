package com.example.rxk152130.hackutd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_Info1 extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__info1);


        ArrayList<Activity> activityList = readfromurl.hashMap.get(readfromurl.selectedActivity);
        final ListView listView = (ListView) findViewById(R.id.activity_list);
        //listView.setAdapter(null);

        users.clear();
        for (Activity act : activityList){
            String str = act.userName + "\n";
            /*str += "Contact number : " + act.number + "\n";
            act.distance = act.distance * 10000;
            act.distance = Math.round(act.distance);
            act.distance = act.distance/10000;*/
            str += "Distance :" + act.distance + " miles"+"\n";
            str += "Start Time :" + act.startTime + "\n";
            str += "End Time :" + act.endTime + "\n";
            users.add(str);
        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview1, users);

        listView.setAdapter(adapter);

        //adapter.clear();
        adapter.notifyDataSetChanged();
        listView.invalidate();


    }
}
