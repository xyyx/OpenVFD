package com.xyyx.openvfd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.xyyx.openvfd.Utils.Constants;
import com.xyyx.openvfd.Utils.Utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class BootCompleteService extends Service {
    private byte[] values = new byte[660];
    private NetworkChangeReceiver networkChangeReceiver;
    private UsbChangeReceiver usbChangeReceiver;
    private AlarmReceiver alarmReceiver;
    private  PendingIntent alarmIntentA;
    private  PendingIntent alarmIntentB;


    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        this.registerReceiver(networkChangeReceiver,netFilter);

        IntentFilter usbFilter = new IntentFilter();
        usbFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        usbFilter.addAction("android.intent.action.MEDIA_EJECT");
        usbFilter.addAction("android.intent.action.MEDIA_REMOVED");
        usbFilter.addDataScheme("file");
        usbChangeReceiver = new UsbChangeReceiver();
        this.registerReceiver(usbChangeReceiver,usbFilter);

        alarmReceiver = new AlarmReceiver();
        IntentFilter alarmFilter = new IntentFilter("com.xyyx.openvfd.ALARM_PROCESSED");
        alarmFilter.addCategory(Intent.CATEGORY_DEFAULT);
        this.registerReceiver(alarmReceiver,alarmFilter);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at 23:00
        Intent intentA = new Intent("com.xyyx.openvfd.ALARM_PROCESSED");
        intentA.putExtra("requestCode", 1);
        alarmIntentA = PendingIntent.getBroadcast(this, 1, intentA, 0);
        Calendar lowBright = Calendar.getInstance();
        lowBright.setTimeInMillis(System.currentTimeMillis());
        lowBright.set(Calendar.HOUR_OF_DAY, 22);
        lowBright.set(Calendar.MINUTE, 00);
        am.setRepeating(AlarmManager.RTC_WAKEUP, lowBright.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntentA);
        if (Constants.DEBUG)
            Log.d(Constants.TAG,"ALARM1 Started " + lowBright.getTimeInMillis());
        // Set the alarm to start at 10:00
        Intent intentB = new Intent("com.xyyx.openvfd.ALARM_PROCESSED");
        intentB.putExtra("requestCode", 2);
        alarmIntentB = PendingIntent.getBroadcast(this, 2, intentB, 0);
        Calendar hiBright = Calendar.getInstance();
        hiBright.setTimeInMillis(System.currentTimeMillis());
        hiBright.set(Calendar.HOUR_OF_DAY, 10);
        hiBright.set(Calendar.MINUTE, 00);
        am.setRepeating(AlarmManager.RTC_WAKEUP, hiBright.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntentB);
        if (Constants.DEBUG)
            Log.d(Constants.TAG,"ALARM2 Started " + hiBright.getTimeInMillis());

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            int cnt = 0;
                            Arrays.fill(values, (byte)0);  //Fills array with 0
                            values[0] = (byte)5;
                            while (cnt < 5) {
                                int temp = Integer.parseInt(Utils.readLine(Constants.TEMP_FILE));
                                temp = temp / 1000;
                                values[3] = (byte)temp;
                                Utils.writeTemp(Constants.PIPE_FILE,values);
                                Thread.sleep(5000);
                                cnt+= 1;
                            }
                            byte clock[] = new byte[] {(byte)0};
                            Utils.writeTemp(Constants.PIPE_FILE,clock);
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 300000); //execute in every 5 minutes
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
        }
}
