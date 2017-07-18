package com.example.rxk152130.hackutd;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class readfromurl extends FragmentActivity implements DownloadCallback {

    private TextView mDataText;
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;
    Button button;
    Button button2;
    GPSTracker1 mGPS;
    double lanstr,lonstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readfromurl);


        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        mDataText = (TextView) findViewById(R.id.data_text);
        mGPS = new GPSTracker1(this);
        if(mGPS.canGetLocation()){
            mGPS.getLocation();
            lanstr = mGPS.getLatitude();
            lonstr = mGPS.getLongitude();
        } else {
            mGPS.showSettingsAlert();
        }
        //mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "https://www.google.com");
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "http://192.168.137.1:8080/HackUTDServer/getEventInfo?lat="+Double.toString(lanstr)+"&lng="+Double.toString(lonstr));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                startDownload();
            }
        });

        //startDownload();

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                finishDownloading();
                mDataText.setText("");
            }
        });

    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startDownload();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.fetch_action:
                startDownload();
                return true;
            case R.id.clear.action:
                finishDownloading();
                mDataText.setText("");
                return true;
        }
        return false;
    }*/

    private void startDownload(){
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }

    static String selectedActivity = "";
    static HashMap<String, ArrayList<Activity>> hashMap = new HashMap<>();

    @Override
    public void updateFromDownload(String result) {
        if(result != null){
            //mDataText.setText(Integer.toString(result.length()));
            //mDataText.setText(result);
            ArrayList<Activity> activitylist = new ArrayList<>();
            ArrayList<String> activityNamesList = new ArrayList<>();

            hashMap = new HashMap<>();
            try{
                JSONObject jsonObj = new JSONObject(result);
                // Getting JSON Array node
                JSONArray activities = jsonObj.getJSONArray("activities");

                for (int i = 0; i < activities.length(); i++) {
                    Activity act = new Activity();
                    JSONObject c = activities.getJSONObject(i);

                    act.activateName = c.getString("activity_name");
                    act.userName = c.getString("user_name");
                    act.msg = c.getString("msg");
                    act.number = c.getString("number");
                    act.lat = c.getDouble("lat");
                    act.lng = c.getDouble("lng");
                    act.distance = c.getDouble("distance");
                    String startstr = c.getString("startTime");
                    //mDataText.setText(act.userName);
                    String endstr = c.getString("endTime");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    //SimpleDateFormat sdfe = new SimpleDateFormat("");
                    Date start = sdf.parse(startstr);
                    Date end = sdf.parse(endstr);
                    act.startTime = start;
                    act.endTime = end;

                    // Phone node is JSON Object
                    /*JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");*/

                    // tmp hash map for single contact
                    //HashMap<String, String> contact = new HashMap<>();
                    activitylist.add(act);

                    ArrayList<Activity> tempList = hashMap.get(act.activateName);
                    if (tempList == null){
                        tempList = new ArrayList<>();
                        tempList.add(act);
                        hashMap.put(act.activateName, tempList);
                    }
                    else{
                        tempList.add(act);
                        hashMap.put(act.activateName, tempList);
                    }




                    // adding each child node to HashMap key => value
                    /*contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);
                    // adding contact to contact list
                    contactList.add(contact);*/
                }
            } catch (JSONException e){
                e.printStackTrace();
                mDataText.setText(e.toString());
            } catch (ParseException a){
                a.printStackTrace();
                mDataText.setText(a.toString());
            }

            for(String activityName : hashMap.keySet()){
                activityNamesList.add(activityName);
                //mDataText.setText(activityName);
            }

            final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, activityNamesList);

            final ListView listView = (ListView) findViewById(R.id.activity_list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int itemPosition = position;

                    selectedActivity = (String) listView.getItemAtPosition(position);
                    //end_result.setText("print" + selectedActivity);
                    Intent myIntent = new Intent(readfromurl.this, Activity_Info1.class);
                    startActivity(myIntent);
                }
            });


            /*ArrayAdapter<Activity> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, activitylist );
            ListView lv;
            lv = (ListView) findViewById(R.id.newlist);
            lv.setAdapter(arrayAdapter);*/
            //ListAdapter adapter = new SimpleAdapter(readfromurl.this, hashMap, R.layout.list_item, new String[]{"activatename", "userName", "msg", "number"}, new int[]{R.id.activateName,R.id.userName,R.id.msg,R.id.number});
        } else {
            //mDataText.setText(getString(R.string.connection_error));
            mDataText.setText("ERROR");
        }

    }

    @Override
    public NetworkInfo getActiveNetworkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void finishDownloading(){
        mDownloading = false;
        if(mNetworkFragment != null){
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete){
        switch(progressCode) {
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                mDataText.setText("" + percentComplete + "%");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }


}
