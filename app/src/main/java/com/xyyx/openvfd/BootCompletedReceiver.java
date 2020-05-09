package com.xyyx.openvfd;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver{
    private Intent bootIntent;

    @Override
    public void onReceive(Context context,Intent intent){
        bootIntent = new Intent(context, BootCompleteService.class);
        context.startService(bootIntent);
    }
}
