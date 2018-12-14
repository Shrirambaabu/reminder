package in.myreminder.srb.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myreminder.srb.AlarmReceiver;
import in.myreminder.srb.R;

import static in.myreminder.srb.utils.Utils.backButtonOnToolbar;

public class ShowAlarmActivity extends AppCompatActivity {

    private PendingIntent notifyIntent;


    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alarm);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        getValues(getIntent());
    }

    private void getValues(Intent intent) {
     //   notifyIntent = intent.getParcelableExtra("pend");

        Intent myIntent = new Intent(ShowAlarmActivity.this, AlarmReceiver.class);
        myIntent.putExtra("alarm", "alarm");
        notifyIntent = PendingIntent.getBroadcast(ShowAlarmActivity.this, 0, myIntent, 0);
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
    @OnClick(R.id.alarm_button)
    public void turnOffAlarm() {
/*

        if (notifyIntent!=null){
            Log.e("But","Cancel");
            AddReminderActivity.cancelAlarm();
            */
/*alarmManager.cancel(notifyIntent);
            Log.e("notifyIntent", "" + notifyIntent);*//*


        }
*/

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(notifyIntent);
    }
}
