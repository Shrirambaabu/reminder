package in.myreminder.srb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.myreminder.srb.activity.AddReminderActivity;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                new AddReminderActivity().alarmSetter(context);
            }
        }
    }
}