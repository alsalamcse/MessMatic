package rosette.draz.com;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;

public class AlerReceiver extends BroadcastReceiver {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("Message");
        String phone = intent.getStringExtra("Phone");

        //Send the SMS//
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null, null);

    }


}
