package in.myreminder.srb.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myreminder.srb.AlarmBroadcaster;
import in.myreminder.srb.R;
import in.myreminder.srb.adapter.AlertAdapter;
import in.myreminder.srb.database.DatabaseHelper;
import in.myreminder.srb.model.MyAlert;
import in.myreminder.srb.utils.EmptyRecyclerView;

import static in.myreminder.srb.utils.Utils.backButtonOnToolbar;

public class AddReminderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView emptyRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    DatabaseHelper db;
    private ArrayList<MyAlert> myAlertArrayList = new ArrayList<MyAlert>();
    private AlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        db = new DatabaseHelper(this);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        getAlertList();
    }

    private void getAlertList() {
        myAlertArrayList.clear();

        myAlertArrayList.addAll(db.getAllAlerts());

        alertAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        alertAdapter = new AlertAdapter(myAlertArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setAdapter(alertAdapter);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        getAlertList();
    }

    @OnClick(R.id.add_reminder)
    public void addReminder() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //  eReminderTime.setText( selectedHour + ":" + selectedMinute);

                Log.e("Time", "" + selectedHour + ":" + selectedMinute);
                String hourFinal = "", minuteFinal = "", meridian = "";

                if (selectedHour == 0) {
                    selectedHour = 12;
                    meridian = "AM";
                } else if (selectedHour < 12) {
                    selectedHour = selectedHour;
                    meridian = "AM";
                } else if (selectedHour == 12) {
                    selectedHour = selectedHour;
                    meridian = "PM";
                } else {
                    selectedHour = selectedHour - 12;
                    meridian = "PM";
                }

                if (selectedHour < 10) {
                    hourFinal = "0" + selectedHour;
                } else {
                    hourFinal = "" + selectedHour;
                }

                if (selectedMinute < 10) {
                    minuteFinal = "0" + selectedMinute;
                } else {
                    minuteFinal = "" + selectedMinute;
                }

                Log.e("Converted", "" + hourFinal + ":" + minuteFinal + " " + meridian);
                String selectedTime = "" + hourFinal + ":" + minuteFinal + " " + meridian;
                addReminderValue(selectedTime);


            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void addReminderValue(String selectedTime) {

        MyAlert myAlert = new MyAlert();
        myAlert.setAlertTime(selectedTime);
        myAlert.setAlertRead("1");

        long rowInserted = db.addAlert(myAlert);
        if (rowInserted != -1) {
            Toast.makeText(getApplicationContext(), "Reminder Added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddReminderActivity.this, AddReminderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }


    public void alarmSetter(Context context) {

        //This will set the alarm time to be at the 14:30
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 27);

        //This intent, will execute the AlarmBroadcaster when the alarm is triggered
        Intent alertIntent = new Intent(context, AlarmBroadcaster.class);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
