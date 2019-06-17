package rosette.draz.com;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    //Permission
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


    private TextView alarm;
    //private Button btnTimePicker, btnCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarm = findViewById(R.id.alarm);

     Button  btnTimePicker = findViewById(R.id.btnTimePicker);
      Button btnCancelAlarm = findViewById(R.id.btnCanelAlarm);

       btnTimePicker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment timePicker = new TimePickerFragment();
               timePicker.show(getSupportFragmentManager(), "time picker");
           }
       });

       btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cancelAlarm();
           }
       });


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            startAlarm(c);
        }
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm Set For: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        alarm.setText(timeText);
    }


    private void startAlarm (Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1 , intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //   alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private  void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1 , intent, 0);

        alarmManager.cancel(pendingIntent);
        alarm.setText("Alarm Canceled");
    }

}
