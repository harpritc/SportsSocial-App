package com.example.rxk152130.hackutd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

public class add_event extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button b_date;
    TextView tv_result;

    Button b_end_date;
    TextView end_result;

    int day , month, year, hour, minute , day_end,month_end, year_end,hour_end,minute_end;
    int endday, endmonth, endyear, endhour, endminute;
    int startday, startmonth, startyear, starthour, startminute;
    boolean flag = true;

    EditText inputname,inputphone;
    Button button;
    Button button2;
    String str,act,mstr;
    double lanstr = 0.0,lonstr = 0.0;
    TextView nameview, latname, longname;
    Spinner myact;
    GPSTracker1 mGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Start date and time
        b_date = (Button)findViewById(R.id.b_date);
        tv_result = (TextView) findViewById(R.id.tv_result);

        // End date and time
        b_end_date = (Button)findViewById(R.id.b_end_date);
        end_result = (TextView) findViewById(R.id.end_result);

        inputname = (EditText) findViewById(R.id.inputname);
        inputphone = (EditText) findViewById(R.id.inputphone);
        button = (Button) findViewById(R.id.button);
        myact = (Spinner) findViewById(R.id.inputact);

        nameview = (TextView) findViewById(R.id.dispname);

        b_end_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year_end = c.get(Calendar.YEAR);
                month_end = c.get(Calendar.MONTH);
                day_end = c.get(Calendar.DAY_OF_MONTH);
                //end_result.setText("clicked");
                end_result.setText("day_end" + day_end);
                //DatePickerDialog datePickerDialog = new DatePickerDialog(AddItem.this, AddItem.this,year,month,day);
                flag = false;
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_event.this, add_event.this,year,month,day);
                datePickerDialog.show();


            }
        });

        b_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                flag = true;
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_event.this, add_event.this,year,month,day);
                datePickerDialog.show();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                str = inputname.getText().toString();
                act = myact.getSelectedItem().toString();
                mstr = inputphone.getText().toString();
                // Start NewActivity.class
                Intent myIntent = new Intent(add_event.this,
                        display_addevent.class);
                myIntent.putExtra("NAME", str);
                myIntent.putExtra("ACTIVITY", act);
                myIntent.putExtra("LATITUDE", Double.toString(lanstr));
                myIntent.putExtra("LONGITUDE", Double.toString(lonstr));
                myIntent.putExtra("PHONE",mstr);
                myIntent.putExtra("SYEAR",Integer.toString(startyear));
                myIntent.putExtra("SMONTH",Integer.toString(startmonth));
                myIntent.putExtra("SDAY",Integer.toString(startday));
                myIntent.putExtra("SHOUR",Integer.toString(starthour));
                myIntent.putExtra("SMINUTE",Integer.toString(startminute));
                myIntent.putExtra("EYEAR",Integer.toString(endyear));
                myIntent.putExtra("EMONTH",Integer.toString(endmonth));
                myIntent.putExtra("EDAY",Integer.toString(endday));
                myIntent.putExtra("EHOUR",Integer.toString(endhour));
                myIntent.putExtra("EMINUTE",Integer.toString(endminute));
                startActivity(myIntent);
                //nameview.setText(inputname.getText().toString());
            }
        });
        //All about location from here
        button2 = (Button) findViewById(R.id.button2);
        latname = (TextView) findViewById(R.id.latname);
        longname = (TextView) findViewById(R.id.longname);
        mGPS = new GPSTracker1(this);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0){
                //mGPS.canGetLocation()
                if(mGPS.canGetLocation()){
                    mGPS.getLocation();
                    //text.setText("Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude());
                    //System.out.println("lat" + mGPS.getLatitude());
                    //System.out.println("lon" + mGPS.getLongitude());
                    lanstr = mGPS.getLatitude();
                    lonstr = mGPS.getLongitude();
                    latname.setText("Lat" + lanstr);
                    longname.setText("Lon" + lonstr);
                }else{
                    //latname.setText("Unabletofind");
                    mGPS.showSettingsAlert();
                    System.out.println("Unable");
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if(flag == true) {
            startyear = year;
            startmonth = month + 1;
            startday = dayOfMonth;
        } else {
            endyear = year;
            endmonth = month + 1;
            endday = dayOfMonth;
        }
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(add_event.this,add_event.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        if(flag == true){
            starthour = hourOfDay;
            startminute = minute;
        } else {
            endhour = hourOfDay;
            endminute = minute;
        }

        /*tv_result.setText("year: " + startyear + "\n" +
                "month: " + startmonth + "\n" +
                "day: " + startday + "\n" +
                "hour: " + starthour + "\n" +
                "minute: " + startminute + "\n");*/

        tv_result.setText(startmonth + "-" + startday + "-" + startyear + " " + starthour + ":" + startminute);

        end_result.setText(endmonth + "-" + endday + "-" + endyear + " " + endhour + ":" + endminute);

        /*end_result.setText("year: " + endyear + "\n" +
                "month: " + endmonth + "\n" +
                "day: " + endday + "\n" +
                "hour: " + endhour + "\n" +
                "minute: " + endminute + "\n");*/

    }

}
