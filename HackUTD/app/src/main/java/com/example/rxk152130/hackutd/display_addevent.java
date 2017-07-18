package com.example.rxk152130.hackutd;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class display_addevent extends AppCompatActivity {

    Button button;
    String s,s1;
    String lanstr,lonstr;
    /*private TextView mDataText;
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_addevent);

        s = getIntent().getStringExtra("NAME");
        s1  = getIntent().getStringExtra("ACTIVITY");
        lanstr = getIntent().getStringExtra("LATITUDE");
        lonstr = getIntent().getStringExtra("LONGITUDE");
        button = (Button) findViewById(R.id.button);
        //TextView nametextview = (TextView) findViewById(R.id.dispname);
        /*TextView acttextview = (TextView) findViewById(R.id.dispact);
        TextView lantextview = (TextView) findViewById(R.id.displan);
        TextView lontextview = (TextView) findViewById(R.id.displon);
        TextView mtextview = (TextView) findViewById(R.id.dispmob);*/
        //nametextview.setText(getIntent().getStringExtra("NAME"));
        /*acttextview.setText(getIntent().getStringExtra("ACTIVITY"));
        lantextview.setText(getIntent().getStringExtra("LATITUDE"));
        lontextview.setText(getIntent().getStringExtra("LONGITUDE"));
        mtextview.setText(getIntent().getStringExtra("PHONE"));*/
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-dd-MM HH:mm:ss");

        String startdate = getIntent().getStringExtra("SYEAR") + "-" + getIntent().getStringExtra("SMONTH") + "-"
                + getIntent().getStringExtra("SDAY") + "+" +getIntent().getStringExtra("SHOUR") + ":"
                + getIntent().getStringExtra("SMINUTE") + ":" + "00" + ":" + "0";
        String enddate = getIntent().getStringExtra("EYEAR") + "-" + getIntent().getStringExtra("EMONTH") + "-"
                + getIntent().getStringExtra("EDAY") + "+" + getIntent().getStringExtra("EHOUR") + ":"
                + getIntent().getStringExtra("EMINUTE") + ":" + "00" + ":" + "0";

        String newstr = "http://192.168.137.1:8080/HackUTDServer/putEvent?"+"activity_name="+getIntent().getStringExtra("ACTIVITY")+
                "&user_name="+getIntent().getStringExtra("NAME")+
                "&msg="+""+
                "&number="+getIntent().getStringExtra("PHONE")+
                "&lat="+getIntent().getStringExtra("LATITUDE")+
                "&lng="+getIntent().getStringExtra("LONGITUDE")+
                "&startTime="+startdate+"&endTime="+enddate;

        new opennewurl().execute(newstr);

        /*URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(newstr);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            while(data != -1){
                char current = (char) data;
                data = isw.read();
                System.out.println(current);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }*/


       /* mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),
                "http://192.168.137.1:8080/HackUTDServer/putEvent?"+"&activity_name="+getIntent().getStringExtra("ACTIVITY")+
                        "&user_name="+getIntent().getStringExtra("NAME")+
                        "&msg="+""+
                        "&number="+getIntent().getStringExtra("PHONE")+
                        "&lat="+getIntent().getStringExtra("LATITUDE")+
                        "&lng="+getIntent().getStringExtra("LONGITUDE")+
                        "&startTime="+startdate+"&endTime="+enddate);*/


        //mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), newstr);

        //startDownload();

        /*try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newstr));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(display_addevent.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });


    }

    class opennewurl extends AsyncTask<String, Void, String>{

        protected String doInBackground(String... urls){
            URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            while(data != -1){
                char current = (char) data;
                data = isw.read();
                System.out.println(current);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
            return null;
        }

        protected void onPostExecute(String... urls){

        }

    }

    /*private void startDownload(){
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(String result) {

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
    }*/











}

